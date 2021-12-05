package response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * this class exists to wrap up a command response
 */
public class CommandResponse {
    private String description;
    private List<String> data;
    private File file;

    public CommandResponse() {}

    public CommandResponse(String description) {
        this.description = description;
    }

    /**
     * get the description of the response, as plain text.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * set the description of the response, in plain text.
     * @param description the description text
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get the list form data. This may be a pageable list of
     * information, or any other type of string data
     * @return
     */
    public List<String> getData() {
        return data;
    }

    /**
     * set the list form data of the response. This may be a
     * pageable list of information or values, or any other
     * form of List<String>.
     */
    public void setData(List<String> data) {
        this.data = data;
    }

    /**
     * set the list form data of the response. This may be a
     * pageable list of information or values, or any other
     * form of String[].
     */
    public void setData(String[] data) {
        ArrayList<String> list = new ArrayList<String>(data.length);

        for (String str : data) {
            list.add(str);
        }
        this.data = list;
    }

    /**
     * get the file handler for this response, if any
     * @return the file handle
     */
    public File getFile() {
        return file;
    }

    /**
     * set the file handle object for this response.
     * @param file the file handle
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * creates an instance of a CommandResponse from just
     * simple text, by populating the description.
     * @param response the response text
     * @return
     */
    public static CommandResponse fromString(String response) {
        return new CommandResponse(response);
    }

    /**
     * gets a string representation of all of the data which can be
     * easily formatted into string only.
     * @return
     */
    public String toSimpleText() {
        StringBuilder builder = new StringBuilder();
        if (description != null) {
            builder.append(description).append('\n');
        }
        if (data != null) {
            data.forEach(str -> builder.append(str).append('\n'));
        }
        return builder.toString();
    }

    /**
     * this method returns a string form of the response,
     * including all populated fields.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.toSimpleText()).append('\n');
        if (file != null && file.exists()) {
            builder.append(file.getAbsolutePath());
        }
        return builder.toString();
    }

}
