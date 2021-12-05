package command.handlers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import response.CommandResponse;
import response.ResponseConsumer;
import util.StringMatch;

public class SearchHandler extends CommandHandler {

    protected String[] getSearchableFiles() {
        return new String[] {
            "consts.txt",
            "custom_hashes.txt",
            "Hashes_FullPath.txt",
            "Hashes.txt",
            "Labels.txt",
            "ParamLabels.csv",
            "sqb-Labels.txt"
        };
    }
    
    private static HashSet<String> searchables = null;

    private synchronized void loadFiles() {
        if (searchables != null) {
            return;
        }

        searchables = new HashSet<String>();
        
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        for (String fileName : getSearchableFiles()) {
            try {
                InputStream inputStream = classloader.getResourceAsStream("searchable/" + fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while (reader.ready()) {
                    String line = reader.readLine();
                    searchables.add(line);
                }
            } catch (Exception e) {
                System.out.println("Error while reading file: " + fileName + "\nReason: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
    }

    @Override
    public void handle(String[] arguments, ResponseConsumer consumer) {
        
        if (searchables == null) {
            loadFiles();
        }

        if (arguments.length < 2) {
            consumer.receiveResponse(
                CommandResponse.fromString(
                    "No arguments given."
                )
            );
        }
        
        Stream<String> results = searchables.stream().filter(
            entry -> StringMatch.wildcard(entry, arguments[1]) == true
        );
        List<String> resultsList = results.collect(Collectors.toList());
        
        int index = 2;
        while (index < arguments.length) {
            int currentIndex = index;
            resultsList = resultsList.stream().filter(
                entry -> StringMatch.wildcard(entry, arguments[currentIndex]) == true
            ).collect(Collectors.toList());
            index++;
        }
        
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < arguments.length; ++i) {
            builder.append(' ' + arguments[i]);
        }

        CommandResponse response = new CommandResponse();

        response.setData(resultsList);
        response.setDescription("Found `" + response.getData().size() + "` search results for: `" + builder.toString().trim() + '`');

        consumer.receiveResponse(response);
        
    }

    @Override
    public String getHelp() {
        return "Searches for the given argument string to find any matching data.";
    }

    @Override
    public int getTimeout() {
        return 10000;
    }
}
