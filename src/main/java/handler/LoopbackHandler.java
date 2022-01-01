package handler;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class LoopbackHandler extends AbstractHandler {

  @Override
  public void handle(SlashCommandEvent event) {
    OptionMapping message = event.getOption("message");
    if (message != null) {
      event.reply(message.getAsString()).queue();;
    } else {
      event.reply("invalid arguments given!").queue();;
    }
  }

  @Override
  public CommandData getCommandData() {
    CommandData command = new CommandData("loopback", "repeats the given message");
    command.addOption(OptionType.STRING, "message", "the loopback message");
    return command;
  }
  
}
