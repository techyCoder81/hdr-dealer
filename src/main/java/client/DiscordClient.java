package client;

import command.CommandProducer;
import engine.CommandEngine;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import response.ChannelConsumer;

public class DiscordClient extends ListenerAdapter implements CommandProducer {
    CommandEngine engine;

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
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) {
            return;
        }

        String text = event.getMessage().getContentDisplay();

        if (text.startsWith("$")) {

            //event.getChannel().sendMessage("Pong!").queue();
            ChannelConsumer responseConsumer = new ChannelConsumer(event.getChannel());
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

}