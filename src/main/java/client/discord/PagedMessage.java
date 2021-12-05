package client.discord;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import response.CommandResponse;
import util.Range;

public class PagedMessage {

  // max character count for a discord message
  private static final int MAX_DISCORD_MESSAGE = 4000;

  // we dont really want more than 20 lines at a time
  private static final int MAX_LINES = 20;

  public static final String LEFT_ARROW = "U+2B05";
  public static final String RIGHT_ARROW = "U+27A1";

  private Message discordMessage;
  private CommandResponse response;
  public long lastUpdatedTime = System.currentTimeMillis();

  /** each Range specifies the data lines included on that page, inclusive */
  private List<String> dataPages;

  private int pageIndex = 0;

  public PagedMessage(Message message, CommandResponse response){
    this.discordMessage = message;
    this.response = response;
    dataPages = new ArrayList<String>();
    List<String> data = response.getData();
    if (data == null) {
      return;
    }

    int pageSpace = MAX_DISCORD_MESSAGE - message.getContentRaw().length();
    
    int available = pageSpace;
    StringBuilder pageBuilder = new StringBuilder();
    int lineCount = 0;
    int totalLineCount = 0;
    pageBuilder.append("```");

    for (String line : data) {
      // if not enough space in current page, start a new page
      if (available - line.length() < 75 || lineCount >= MAX_LINES) {
        available = pageSpace;
        pageBuilder.append("```------------------------------\n\n"
          + "Showing entries `" + totalLineCount + "` - `" + (totalLineCount + lineCount) + "`\n"
          + "Page: `" + dataPages.size() + "` of $page_count123. Use arrows to change pages.");
        dataPages.add(pageBuilder.toString());
        pageBuilder = new StringBuilder();
        pageBuilder.append("```");
        totalLineCount += lineCount;
        lineCount = 0;
      }

      pageBuilder.append(line + "\n");
      ++lineCount;

    }
    // finish the final page
    pageBuilder.append("```------------------------------\n\n"
          + "Showing entries `" + totalLineCount + "` - `" + (totalLineCount + lineCount) + "`\n"
          + "Page: `" + dataPages.size() + "` of $page_count123. Use arrows to change pages.");
    dataPages.add(pageBuilder.toString());

    
    // replace all page count placeholders with the actual page count
    ArrayList<String> tempPages = new ArrayList<String>();
    dataPages.forEach(entry -> tempPages.add(entry.replace("$page_count123", '`' + Integer.toString(dataPages.size() - 1) + '`')));
    dataPages = tempPages;

    if (dataPages.size() > 0) {
      updatePage();
    }

    // clear reactions, and add the arrows.
    discordMessage.clearReactions()
            .and(discordMessage.addReaction(LEFT_ARROW))
            .and(discordMessage.addReaction(RIGHT_ARROW)).queue();
  }

  public void handleReaction(MessageReactionAddEvent event) {
    ReactionEmote reactionEmote = event.getReactionEmote();
    
    if (reactionEmote == null) {
      return;
    }

    String reactionString = reactionEmote.toString().toUpperCase();

    if (reactionString.contentEquals("RE:" + LEFT_ARROW)) {
      if (pageIndex > 0) {
        pageIndex--;
        updatePage();
      }
      lastUpdatedTime = System.currentTimeMillis();
      discordMessage.removeReaction(LEFT_ARROW, event.getUser()).queue();
    } else if (reactionString.contentEquals("RE:" + RIGHT_ARROW)) {
      if (pageIndex < dataPages.size() - 1) {
        pageIndex++;
        updatePage();
      }
      lastUpdatedTime = System.currentTimeMillis();
      discordMessage.removeReaction(RIGHT_ARROW, event.getUser()).queue();
    }


  }

  public void updatePage() {
    // build the updated message
    StringBuilder updateBuilder = new StringBuilder();
    if (response.getDescription() != null) {
      updateBuilder.append(response.getDescription() + '\n');
    }
    updateBuilder.append(dataPages.get(pageIndex));

    
    discordMessage.editMessage(updateBuilder.toString()).complete();
  }

  public Message getDiscordMessage() {
    return discordMessage;
  }

 

}
