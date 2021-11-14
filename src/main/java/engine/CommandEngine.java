package engine;

import command.handlers.CommandHandler;
import command.handlers.HandlerFactory;
import response.ResponseConsumer;

public class CommandEngine {
    private HandlerFactory factory;

    public CommandEngine() {
        factory = new HandlerFactory();
    }

    public synchronized void schedule(final String command, final ResponseConsumer consumer) {
        CommandHandler handler = factory.getHandler(command);
        CommandExecutor executor = new CommandExecutor(handler, command.split(" "), consumer);
        new Thread(executor).start();
    }
}
