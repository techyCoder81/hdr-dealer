package command.handlers;

import response.ResponseConsumer;

public class InvalidHandler extends CommandHandler {

    public InvalidHandler() {

    }

    @Override
    public void handle(String[] arguments, ResponseConsumer consumer) {
        consumer.simpleResponse("Invalid Command!");
    }

    @Override
    public String getHelp() {
        return "This is not a valid command.";
    }
}
