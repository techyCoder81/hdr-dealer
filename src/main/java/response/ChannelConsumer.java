package response;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.File;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import client.discord.PageListener;

public class ChannelConsumer implements ResponseConsumer {

    private MessageChannel destinationChannel;
    private PageListener pageListener = null;

    public ChannelConsumer(MessageChannel destinationChannel) {
        this(destinationChannel, null);
    }

    public ChannelConsumer(MessageChannel destinationChannel, PageListener pageListener) {
        this.destinationChannel = destinationChannel;
        this.pageListener = pageListener;
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
        String rawText = response.getDescription();
        MessageAction message;
        if (rawText != null && !rawText.contentEquals("")) {
            message = destinationChannel.sendMessage(rawText);
        } else {
            message = destinationChannel.sendMessage("[No description given]");
        }

        
        // attached file contents, if any
        File file = response.getFile();
        if (file != null && file.exists()) {
            message.addFile(file);
        }
        
        // blocking call that executes message sending until complete. This
        // consumer instance will run in its own thread though, so its ok.
        // If this thread is alive for more time than is allowed, itll get killed anyway.
        Message createdMessage = message.complete();
        
        // if theres pageable data, add it to the page listener
        if (pageListener != null && response.getData() != null) {
            pageListener.addMessage(createdMessage, response);
        }
    }
}
