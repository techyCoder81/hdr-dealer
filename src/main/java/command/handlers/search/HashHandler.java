package command.handlers.search;

import java.util.List;

import response.CommandResponse;
import response.ResponseConsumer;

public class HashHandler extends SearchHandler{
    // all we have to do is override the files we want to search,
    // and the base class will handle the rest.
    @Override
    protected String[] getSearchableFiles() {
        return new String[] {
            "ParamLabels.csv"
        };
    }

    @Override
    protected void addValue(String line) {
      searchables.add(line.replace(",", " : "));
    }

    @Override
    protected void createResponse(String[] arguments, List<String> results, ResponseConsumer consumer) {

      if (results.isEmpty()) {
        consumer.receiveResponse(new CommandResponse("No matching hash found."));
        return;
      }

      CommandResponse response = new CommandResponse("Found hash: \n```" + results.get(0) + "```");
      consumer.receiveResponse(response);
  }

}
