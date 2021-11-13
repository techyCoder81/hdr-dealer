package command.handlers;

import output.consumer.ResponseConsumer;

public class LoopbackHandler implements CommandHandler {

    public LoopbackHandler() {

    }

    @Override
    public void handle(String[] arguments, ResponseConsumer consumer) {
        // TODO Auto-generated method stub
        StringBuilder builder = new StringBuilder();
        for (String arg : arguments) {
            builder.append(arg + " ");
        }
        consumer.receiveResponse(builder.toString());
    }
    
}
