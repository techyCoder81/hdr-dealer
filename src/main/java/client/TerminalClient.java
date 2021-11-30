package client;

import command.CommandProducer;
import engine.CommandEngine;
import response.TerminalConsumer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TerminalClient implements Runnable, CommandProducer {
    CommandEngine engine;

    /** private default constructor */
    @SuppressWarnings("unused")
    private TerminalClient(){}

    public TerminalClient(CommandEngine engine) {
        this.engine = engine;
        new Thread(this).start();
    }

    @Override
    public void setCommandEngine(CommandEngine engine) {
        this.engine = engine;
    }

    @Override
    public void run() {

        if (engine == null) {
            System.out.println("No engine was supplied to TerminalClient!");
            return;
        }
        // Enter data from terminal
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        while (true) {
            try {
                // read data from terminal
                String command = reader.readLine().trim();

                // schedule the command using the terminal singleton
                engine.schedule(command, TerminalConsumer.singleton());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
