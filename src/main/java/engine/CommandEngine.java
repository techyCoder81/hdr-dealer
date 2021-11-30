package engine;

import command.HandlerFactory;
import command.handlers.CommandHandler;
import response.ResponseConsumer;

/** an engine through which commands can be scheduled */
public class CommandEngine {

    /** the handler factory for creating CommandHandler instances */
    private HandlerFactory factory;

    public CommandEngine() {
        factory = new HandlerFactory();
    }

    /**
     * parse and schedule the given command with the given ResponseConsumer
     * @param command the command string, without leading symbols like '$'
     * @param consumer the ResponseConsumer that should consume the generated response
     */
    public synchronized void schedule(final String command, final ResponseConsumer consumer) {
        CommandHandler handler = factory.getHandler(command);
        CommandExecutor executor = new CommandExecutor(handler, command.split(" "), consumer);
        new Thread(executor).start();
    }
}
