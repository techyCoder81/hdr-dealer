package command.handlers;

import response.ResponseConsumer;

public abstract class CommandHandler {

    /**
     * handle the given set of arguments, where <code>arguments[0]</code> is the command itself.
     * @param arguments the given arguments, where index 0 is the argument name
     * @param consumer the response consumer to return results data to.
     */
    public abstract void handle(String[] arguments, ResponseConsumer consumer);

    /** the timeout for the command being handled. default is 5000 ms. */
    public int getTimeout() {
        return 5000;
    }

    /** returns a help string for the command */
    public abstract String getHelp();

    /** preprocess the given arguments, if necessary */
    public String[] preprocess(String[] arguments) {
        return arguments;
    }
}
