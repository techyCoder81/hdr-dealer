package client.discord;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import response.CommandResponse;

import static client.discord.PagedMessage.LEFT_ARROW;
import static client.discord.PagedMessage.RIGHT_ARROW;

public class PageListener {

  /** 5 minute timeout */
  private final static long timeout = 5 * 1000 * 60;

  ConcurrentHashMap<Long, PagedMessage>  messages = new ConcurrentHashMap<Long, PagedMessage>();

  public PageListener() {
    Timer timer = new Timer();
    int begin = 0;
    int timeInterval = 10000;

    timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
            cleanupListeners();
        }
    }, begin, timeInterval);
  }

  private void cleanupListeners() {
    long currentTime = System.currentTimeMillis();
    for (Entry<Long, PagedMessage> entry : messages.entrySet()){
      if (currentTime - entry.getValue().lastUpdatedTime > timeout) {
        messages.remove(entry.getKey());
      }
    }
  }

  public void addMessage(Message discordMessage, CommandResponse response) {
    PagedMessage pagedMessage = new PagedMessage(discordMessage, response);
    messages.put(discordMessage.getIdLong(), pagedMessage);
  }

  public boolean isListeningForReactions(Long messageId) {
    boolean isContained = messages.containsKey(messageId);
    return isContained;
  }

  public void handleReaction(Long messageId, MessageReactionAddEvent event) {
    PagedMessage message = messages.get(messageId);
    if (message != null) {
      message.handleReaction(event);
    }
  }

}
