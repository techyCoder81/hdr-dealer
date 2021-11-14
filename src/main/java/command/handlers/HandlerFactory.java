package command.handlers;

import command.CommandEnum;

public class HandlerFactory {
    public HandlerFactory() {

    }

    public CommandHandler getHandler(String command) {
        String[] args = command.split(" ");

        if (args.length == 0) {
            return new InvalidHandler();
        }

        String commandName = args[0];
        CommandEnum commandType = CommandEnum.fromString(commandName);
        return getHandler(commandType);
    }

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
