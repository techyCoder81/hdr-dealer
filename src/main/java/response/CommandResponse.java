package response;

import net.dv8tion.jda.api.interactions.commands.Command;

import java.io.File;
import java.util.List;

public class CommandResponse {
    private String description;
    private List<String> data;
    private File file;

    public CommandResponse() {}

    public CommandResponse(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public static CommandResponse fromString(String response) {
        return new CommandResponse(response);
    }

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
