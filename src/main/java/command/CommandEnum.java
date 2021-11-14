package command;

import command.handlers.*;

public enum CommandEnum{
    PING("ping", PingHandler.class),
    LOOPBACK("loopback", LoopbackHandler.class),
    HELP("help", HelpHandler.class),
    INVALID("invalid", InvalidHandler.class);


    String command;
    Class<?> handler;
    CommandEnum(String command, Class<?> handler) {
        this.command = command;
        this.handler = handler;
    }

    @Override
    public String toString() {
        return command;
    }

    public Class<?> getHandlerClass() {
        return handler;
    }

    public static CommandEnum fromString(String str) {
        for (CommandEnum entry : CommandEnum.values()) {
            if (entry.toString().contentEquals(str)) {
                return entry;
            }
        }
        return INVALID;
    }

}
