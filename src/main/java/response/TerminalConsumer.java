package response;

import java.io.File;
import java.util.Collection;

public class TerminalConsumer implements ResponseConsumer {

    @Override
    public void receiveResponse(CommandResponse response) {
        System.out.println(response.toString());
    }
}
