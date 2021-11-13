package command.handlers;

import output.consumer.ResponseConsumer;

public interface CommandHandler {
    public void handle(String[] arguments, ResponseConsumer consumer);
}
