package command.handlers.search;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import command.handlers.CommandHandler;
import response.CommandResponse;
import response.ResponseConsumer;
import util.StringMatch;

public class SearchHandler extends CommandHandler {

    protected String[] getSearchableFiles() {
        return new String[] {
            "consts.txt",
            "custom_hashes.txt",
            "Labels.txt",
            "ParamLabels.csv",
            "sqb-Labels.txt"
        };
    }
    
    protected HashSet<String> searchables = null;

    protected HashSet<String> getSearchables() {
        return searchables;
    }

    protected void setSearchables(HashSet<String> searchables) {
        this.searchables = searchables;
    }

    protected void addValue(String line) {
        searchables.add(line);
    }

    private synchronized void loadFiles() {
        if (getSearchables() != null) {
            return;
        }

        setSearchables(new HashSet<String>());
        
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        for (String fileName : getSearchableFiles()) {
            try {
                InputStream inputStream = classloader.getResourceAsStream("searchable/" + fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while (reader.ready()) {
                    String line = reader.readLine();
                    addValue(line);
                }
            } catch (Exception e) {
                System.out.println("Error while reading file: " + fileName + "\nReason: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
    }

    @Override
    public void handle(String[] arguments1, ResponseConsumer consumer) {
        String[] arguments = preprocess(arguments1);


        if (getSearchables() == null) {
            loadFiles();
        }

        if (arguments.length < 2) {
            consumer.receiveResponse(
                CommandResponse.fromString(
                    "No arguments given."
                )
            );
        }
        
        List<String> resultsList = getResults(arguments);        

        createResponse(arguments, resultsList, consumer);
        
    }

    protected void createResponse(String[] arguments, List<String> results, ResponseConsumer consumer) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < arguments.length; ++i) {
            builder.append(' ' + arguments[i]);
        }

        CommandResponse response = new CommandResponse();

        response.setData(results);
        response.setDescription("Found `" + response.getData().size() + "` search results for: `" + builder.toString().trim() + '`');

        consumer.receiveResponse(response);
    }

    protected List<String> getResults(String[] arguments) {
        // get all of the matching strings for the first arg
        Stream<String> results = getSearchables().stream().filter(
            entry -> StringMatch.wildcard(entry, arguments[1]) == true
        );
        List<String> resultsList = results.collect(Collectors.toList());
        
        // filter further with each further argument
        int index = 2;
        while (index < arguments.length) {
            int currentIndex = index;
            resultsList = resultsList.stream().filter(
                entry -> StringMatch.wildcard(entry, arguments[currentIndex]) == true
            ).collect(Collectors.toList());
            index++;
        }
        
        return resultsList;
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
