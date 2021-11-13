package engine;

import command.CommandEnum;
import command.handlers.CommandHandler;
import output.consumer.ResponseConsumer;

public class CommandEngine {
    public void schedule(String command, ResponseConsumer consumer) {
        String[] args = command.split(" ");
        if (args.length > 0) {
            CommandEnum commandType = CommandEnum.fromString(args[0]);
            if (commandType == CommandEnum.NOT_FOUND) {
                consumer.receiveResponse("No such command!");
                return;
            }


            Class<?> handlerClass = commandType.getHandlerClass();

            try {
                Object handler = handlerClass.getConstructor().newInstance();
                ((CommandHandler)handler).handle(args, consumer);
            } catch (Exception e) {
                consumer.receiveResponse("ENGINE ERROR: " + handlerClass + "\nMessage: " + e.getMessage());
                e.printStackTrace();
                return;
            }
            
        } else {
            consumer.receiveResponse("ENGINE ERROR: empty command!");
            return;
        }
    }
}
