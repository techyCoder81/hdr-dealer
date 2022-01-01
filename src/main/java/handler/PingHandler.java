package handler;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class PingHandler extends AbstractHandler {

 

  @Override
  public CommandData getCommandData() {
    CommandData command = new CommandData("ping", "returns pong, no args.");
    return command;
  }

  @Override
  public void handle(SlashCommandEvent event) {
    event.reply("Pong!").queue();
  }
  
}
