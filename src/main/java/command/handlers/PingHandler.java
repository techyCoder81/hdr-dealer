package command.handlers;

import response.ResponseConsumer;

public class PingHandler extends CommandHandler {

    public PingHandler() {
        
    }

    @Override
    public void handle(String[] arguments, ResponseConsumer consumer) {
        // arguments dont matter lol its a ping
        consumer.simpleResponse("PONG!");
        
    }

    @Override
    public String getHelp() {
        return "This command returns `PONG!`\n" +
                "Usage: `ping`\n" +
                "Output: `PONG!`";
    }
}
