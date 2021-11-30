package response;


/** singleton class for terminal output */
public class TerminalConsumer implements ResponseConsumer {

    private static TerminalConsumer terminal = null;

    /** private constructor, to disallow instantiation */
    private TerminalConsumer(){}

    public static TerminalConsumer singleton() {
        if (terminal == null) {
            terminal = new TerminalConsumer();
        }
        return terminal;
    }

    /**
     * outputs the response to the terminal as a raw string.
     */
    @Override
    public void receiveResponse(CommandResponse response) {
        System.out.println(response.toString());
    }
}
