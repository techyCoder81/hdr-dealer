package response;

import java.io.File;
import java.util.Collection;

public interface ResponseConsumer {
    /**
     * return a response object
     * @param response
     */
    void receiveResponse(CommandResponse response);
}
