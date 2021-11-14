package response;

import java.io.File;
import java.util.Collection;

public interface ResponseConsumer {
    /**
     * return a simple, text response.
     * @param response
     */
    void simpleResponse(String response);

    /**
     * return a paged response where the data is broken up into
     * a collection of strings
     * @param description
     * @param data
     */
    void pagedResponse(String description, Collection<String> data);

    /**
     * return a file handler object the serves as a response
     * @param file
     */
    void fileResponse(String description, File file);
}
