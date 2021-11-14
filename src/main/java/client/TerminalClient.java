package client;

import command.CommandProducer;
import engine.CommandEngine;
import response.TerminalConsumer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TerminalClient implements Runnable, CommandProducer {
    CommandEngine engine;

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
        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        while (true) {
            try {
                // Reading data using readLine
                String command = reader.readLine().trim();

                TerminalConsumer consumer = new TerminalConsumer();
                engine.schedule(command, consumer);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
