import client.DiscordClient;
import java.io.BufferedReader;
import java.io.FileReader;

import client.TerminalClient;
import engine.CommandEngine;

public class Main {
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
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    // all hope is lost for this file lmao
                    System.out.println("exception while closing file!");
                    e.printStackTrace();
                }
            }
        }

        if (token == null || token == "") {
            System.out.println("Error: token file was null or empty.");
            return;
        }

        CommandEngine engine = new CommandEngine();

        new DiscordClient(token, engine);
        new TerminalClient(engine);
    }
}
