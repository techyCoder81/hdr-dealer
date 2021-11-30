package response;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.File;

public class ChannelConsumer implements ResponseConsumer {

    public MessageChannel destinationChannel;

    public ChannelConsumer(MessageChannel destinationChannel) {
        this.destinationChannel = destinationChannel;
    }

    /**
     * handle the response object
     * @param response a <code>CommandResponse</code> object containing data for the message
     */
    @Override
    public void receiveResponse(CommandResponse response) {
        if (response == null) {
            destinationChannel.sendMessage("Response was null!");
            System.out.println("ERROR: response was null!");
        }
        String rawText = response.toSimpleText();
        MessageAction message;
        if (rawText != null && !rawText.contentEquals("")) {
            message = destinationChannel.sendMessage(rawText);
        } else {
            message = destinationChannel.sendMessage("[No description given]");
        }

        File file = response.getFile();
        if (file != null && file.exists()) {
            message.addFile(file);
        }
        message.queue();
    }
}
