package response;

import java.io.File;
import java.util.Collection;

public class TerminalConsumer implements ResponseConsumer {
    @Override
    public void simpleResponse(String response) {
        System.out.println(response);
    }

    @Override
    public void pagedResponse(String description, Collection<String> data) {
        System.out.println(description);
        data.forEach(string -> System.out.println(string));
    }

    @Override
    public void fileResponse(String description, File file) {
        System.out.println(description);
        System.out.println(file.getAbsolutePath().toString());
    }
}
