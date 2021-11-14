package command.handlers;

import response.ResponseConsumer;

public class CryHandler extends CommandHandler {
    @Override
    public void handle(String[] arguments, ResponseConsumer consumer) {
        consumer.simpleResponse("https://media.discordapp.net/attachments/659975025926864897/854804942913011722/cry_about_it_ryu.gif");
    }

    @Override
    public String getHelp() {
        return "Outputs the 'CRY ABOUT IT' gif";
    }
}
