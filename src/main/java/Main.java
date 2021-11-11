import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main extends ListenerAdapter {
    public static void main(String[] args) {
        BufferedReader br = null;
        String token = null;
        try {
            br = new BufferedReader(new FileReader("token.txt"));
            token = br.readLine().trim();
        } catch (Exception e) {
            System.out.println("Error: Could not read token file!");
            System.out.println(e.getMessage());
            return;
        }

        if (token == null || token == "") {
            System.out.println("Error: token file was null or empty.");
            return;
        }
        
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.addEventListeners(new Main());
        try {
            builder.build();
        } catch (Exception e) {
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
        if (event.getMessage().getContentRaw().contentEquals("$ping")) {

            event.getChannel().sendMessage("Pong!").queue();
        }
    }
}
