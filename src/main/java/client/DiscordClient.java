package client;

import client.discord.PageListener;
import command.CommandProducer;
import engine.CommandEngine;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import response.ChannelConsumer;

/**
 * this is a discord client which also produces commands
 */
public class DiscordClient extends ListenerAdapter implements CommandProducer {
    CommandEngine engine;
    PageListener pageListener = new PageListener();

    public DiscordClient(String token, CommandEngine engine) {   
        this(token);
        this.engine = engine;
    }

    public DiscordClient(String token){
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.addEventListeners(this);
        try {
            builder.build();
        } catch (Exception e) {
            System.out.println("exception while building jda!");
            System.out.println("token was: '" + token + "'");
            System.out.println(e.getMessage());
            return;
        }

    }
    
    /** called by the internal discord client event handling anytime 
     * ANY message gets sent. logic here should be efficient */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) {
            return;
        }

        String text = event.getMessage().getContentDisplay();

        if (text.startsWith("$")) {

            //event.getChannel().sendMessage("Pong!").queue();
            ChannelConsumer responseConsumer = new ChannelConsumer(event.getChannel(), pageListener);
            if (engine != null) {
                engine.schedule(text.substring(1), responseConsumer);
            } else {
                event.getChannel().sendMessage("ERROR: Engine was null for discordclient.").queue();
            }
        }
    }

    @Override
    public void setCommandEngine(CommandEngine engine) {
        this.engine = engine;
        
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        Long messageId = event.getMessageIdLong();

        if (!event.getUser().isBot() && pageListener.isListeningForReactions(messageId)) {
            pageListener.handleReaction(messageId, event);
        }
        
    }

}