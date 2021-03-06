package command.handlers;

import response.CommandResponse;
import response.ResponseConsumer;

public class LoopbackHandler extends CommandHandler {

    public LoopbackHandler() {

    }

    @Override
    public void handle(String[] arguments, ResponseConsumer consumer) {
        // TODO Auto-generated method stub
        StringBuilder builder = new StringBuilder();
        for (String arg : arguments) {
            builder.append(arg + " ");
        }
        consumer.receiveResponse(
                new CommandResponse(builder.toString())
        );
    }

    @Override
    public String getHelp() {
        return "This command simply returns whatever the original call was.\n" +
                "Usage: `loopback <loopback_string>`\n" +
                "Output: `loopback <loopback_string>`";
    }

}
