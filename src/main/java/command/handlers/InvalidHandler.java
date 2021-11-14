package command.handlers;

import response.ResponseConsumer;

public class InvalidHandler implements CommandHandler {

    public InvalidHandler() {

    }

    @Override
    public void handle(String[] arguments, ResponseConsumer consumer) {
        consumer.simpleResponse("Invalid Command!");
    }

    @Override
    public int getTimeout() {
        return 5000;
    }

    @Override
    public String getHelp() {
        return "This is not a valid command.";
    }
}
