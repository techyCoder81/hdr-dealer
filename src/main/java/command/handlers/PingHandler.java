package command.handlers;

import output.consumer.ResponseConsumer;

public class PingHandler implements CommandHandler {

    public PingHandler() {
        
    }

    @Override
    public void handle(String[] arguments, ResponseConsumer consumer) {
        // arguments dont matter lol its a ping
        consumer.receiveResponse("PONG!");
        
    }
    
}
