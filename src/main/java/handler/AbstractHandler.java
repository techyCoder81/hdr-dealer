package handler;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public abstract class AbstractHandler {
  public abstract void handle(SlashCommandEvent event);
  public abstract CommandData getCommandData();
  public AbstractHandler() {
    
  }
}
