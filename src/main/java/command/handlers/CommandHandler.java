package command.handlers;

import response.ResponseConsumer;

public abstract class CommandHandler {
    public abstract void handle(String[] arguments, ResponseConsumer consumer);
    public int getTimeout() {
        return 5000;
    }
    public abstract String getHelp();
}
