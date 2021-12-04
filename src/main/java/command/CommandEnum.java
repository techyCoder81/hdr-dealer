package command;

import command.handlers.*;

/**
 * this enum controls what text commands should be handled by
 * what class. any new commands (including aliases) should be
 * added to this list.
 */
public enum CommandEnum{
    PING("ping", PingHandler.class),
    LOOPBACK("loopback", LoopbackHandler.class),
    HELP("help", HelpHandler.class),
    CRY("cry", CryHandler.class),
    INVALID("invalid", InvalidHandler.class),
    MAKEIT("makeit", MakeItHandler.class),
    PAGEDTEST("pagedtest", PagedTestHandler.class);


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

    /**
     * gets the correct CommandEnum entry from the given string.
     * @param str string name, such as 'loopback', 'makeit', or 'help'
     * @return the corresponding enum entry
     */
    public static CommandEnum fromString(String str) {
        for (CommandEnum entry : CommandEnum.values()) {
            if (entry.toString().contentEquals(str)) {
                return entry;
            }
        }
        return INVALID;
    }

}
