package command.handlers;

import response.ResponseConsumer;

public class PingHandler implements CommandHandler {

    public PingHandler() {
        
    }

    @Override
    public void handle(String[] arguments, ResponseConsumer consumer) {
        // arguments dont matter lol its a ping
        consumer.simpleResponse("PONG!");
        
    }

    @Override
    public int getTimeout() {
        return 5000;
    }

    @Override
    public String getHelp() {
        return "This command returns `PONG!`\n" +
                "Usage: `ping`\n" +
                "Output: `PONG!`";
    }
}
