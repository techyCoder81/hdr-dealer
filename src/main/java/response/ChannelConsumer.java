package response;

import net.dv8tion.jda.api.entities.MessageChannel;

import java.io.File;
import java.util.Collection;

public class ChannelConsumer implements ResponseConsumer {

    public MessageChannel destinationChannel;

    public ChannelConsumer(MessageChannel destinationChannel) {
        this.destinationChannel = destinationChannel;
    }

    @Override
    public synchronized void simpleResponse(String response) {
        destinationChannel.sendMessage(response).queue();
        
    }

    @Override
    public synchronized void pagedResponse(String description, Collection<String> data) {
        // TODO build response paging mechanism
        destinationChannel.sendMessage(description).queue();
        data.forEach(str -> destinationChannel.sendMessage(str).queue());
    }

    @Override
    public synchronized void fileResponse(String description, File file) {
        destinationChannel.sendMessage(description).addFile(file).queue();
    }

}
