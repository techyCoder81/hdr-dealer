package client;

import java.util.List;

import command.CommandEnum;
import handler.AbstractHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * this is a discord client which also produces commands
 */
public class DiscordClient extends ListenerAdapter {
    //PageListener pageListener = new PageListener();

    public DiscordClient(String token){
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.addEventListeners(this);
        try {
            JDA jda = builder.build();
            jda.awaitReady();

            // clear old commands
            List<Command> commands = jda.retrieveCommands().complete();
            for (Command slashCommand : commands) {
                slashCommand.delete().queue();
                Guild guild = jda.getGuildById("697248604905144363");
                guild.deleteCommandById(slashCommand.getId());
            }

            // create new commands
            for (CommandEnum command : CommandEnum.values()) {
                CommandData data = command.getCommandData();
                jda.upsertCommand(data).queue();
                Guild guild = jda.getGuildById("697248604905144363");
                guild.upsertCommand(data).queue();
            }
        } catch (Exception e) {
            System.out.println("exception while building jda!");
            System.out.println("token was: '" + token + "'");
            System.out.println(e.getMessage());
            return;
        }

    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        try {
            if (event.getUser().isBot()) {
                return;
            }
            
            CommandEnum command = CommandEnum.fromString(event.getName());
            AbstractHandler handler = command.getNewInstance();
            handler.handle(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
/*
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        Long messageId = event.getMessageIdLong();

        if (!event.getUser().isBot() && pageListener.isListeningForReactions(messageId)) {
            pageListener.handleReaction(messageId, event);
        }
        
    }
*/
}