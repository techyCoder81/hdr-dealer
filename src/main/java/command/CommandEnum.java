package command;
import handler.*;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * this enum controls what text commands should be handled by
 * what class. any new commands (including aliases) should be
 * added to this list.
 */
public enum CommandEnum{
    PING("ping", PingHandler.class),
    LOOPBACK("loopback", LoopbackHandler.class);

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

    public AbstractHandler getNewInstance() {
        try {
            return (AbstractHandler) handler.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CommandData getCommandData() {
        try {
            AbstractHandler theHandler = getNewInstance();
            if (theHandler == null) {
                System.out.println("instances for this enum was null!\n" + this.toString());
                return null;
            }
            return theHandler.getCommandData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * gets the correct CommandEnum entry from the given string.
     * @param str string name, such as 'loopback', 'makeit', or 'help'
     * @return the corresponding enum entry
     * @throws IllegalArgumentException if the given string does not correspond with any values
     */
    public static CommandEnum fromString(String str) {
        for (CommandEnum entry : CommandEnum.values()) {
            if (entry.toString().contentEquals(str)) {
                return entry;
            }
        }
        throw new IllegalArgumentException("Given string " + str + " is not a valid command.");
    }

}
