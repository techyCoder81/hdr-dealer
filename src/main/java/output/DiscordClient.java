package output;

import command.CommandProducer;
import engine.CommandEngine;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import output.consumer.DiscordResponseConsumer;

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
        //System.out.println(event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        if (event.getMessage().getContentRaw().startsWith("$")) {

            //event.getChannel().sendMessage("Pong!").queue();
            DiscordResponseConsumer responseConsumer = new DiscordResponseConsumer(event.getChannel());
            if (engine != null) {
                engine.schedule(event.getMessage().getContentDisplay(), responseConsumer);
                // TODO use a command object
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