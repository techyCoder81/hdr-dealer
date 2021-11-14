package command.handlers;

import response.ResponseConsumer;

public interface CommandHandler {
    public void handle(String[] arguments, ResponseConsumer consumer);
    public int getTimeout();
    public String getHelp();
}
