package command;

import command.handlers.CommandHandler;
import command.handlers.InvalidHandler;

/**
 * this class follows the factory pattern. It's purpose is
 * to get the appropriate handler for each command, rather
 * than requiring users of the CommandEnum to make the
 * reflective calls themselves.
 */
public class HandlerFactory {
    public HandlerFactory() {

    }

    /**
     * gets an instance of the appropriate CommandHandler
     * for the given command string.
     * @param command the command string, potentially including arguments,
     * such as '$'
     * @return an instance of the appropriate CommandHandler for this command
     */
    public CommandHandler getHandler(String command) {
        // if we were given a null command, return 
        // the invalid handler
        if (command == null) {
            return new InvalidHandler();
        }
        String[] args = command.split(" ");

        // if we somehow were given a command of length 0,
        // return the invalid handler.
        if (args.length == 0) {
            return new InvalidHandler();
        }

        // get the command name, and return a handler instance for that type
        String commandName = args[0];
        CommandEnum commandType = CommandEnum.fromString(commandName);
        return getHandler(commandType);
    }


    /**
     * gets an instance of the appropriate CommandHandler
     * for the given command enum entry.
     * @param command the CommandEnum entry representing this command type
     * @return an instance of the appropriate CommandHandler for this command
     */
    public CommandHandler getHandler(CommandEnum command) {
        Class<?> handlerClass = command.getHandlerClass();
        try {
            return (CommandHandler)(handlerClass.getConstructor().newInstance());
        } catch (Exception e) {
            System.out.println("could not create handler instance " +
                    "- given class did not have a no-arg constructor.");
            return new InvalidHandler();
        }
    }
}
