package command.handlers;

import java.util.Arrays;

import response.CommandResponse;
import response.ResponseConsumer;

public class PagedTestHandler extends CommandHandler {

  @Override
  public void handle(String[] arguments, ResponseConsumer consumer) {
    CommandResponse response = new CommandResponse("Paged data:");
    response.setData(Arrays.asList(arguments));
    consumer.receiveResponse(response);
  }

  @Override
  public String getHelp() {
    return "This command tests the paged data handling by returning the given argument list as a pageable response list.";
  }
  
}
