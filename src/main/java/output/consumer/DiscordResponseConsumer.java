package output.consumer;

import net.dv8tion.jda.api.entities.MessageChannel;

public class DiscordResponseConsumer implements ResponseConsumer {

    public MessageChannel destinationChannel;

    public DiscordResponseConsumer(MessageChannel destinationChannel) {
        this.destinationChannel = destinationChannel;
    }

    @Override
    public void receiveResponse(String response) {
        destinationChannel.sendMessage(response).queue();;
        
    }
    
}
