package command.handlers;

import command.CommandEnum;
import response.CommandResponse;
import response.ResponseConsumer;

import java.util.Arrays;

public class HelpHandler extends CommandHandler {
    @Override
    public void handle(String[] args, ResponseConsumer consumer) {
        if (args.length == 0) {
            consumer.receiveResponse(new CommandResponse("ERROR: empty command!"));
            return;
        }

        if (!args[0].contentEquals("help")) {
            StringBuilder builder = new StringBuilder();
            builder.append("ERROR: HelpHandler called for non-help command! Command given: \n");
            Arrays.stream(args).forEach(argument -> builder.append(argument + ' '));
            consumer.receiveResponse(new CommandResponse(builder.toString()));
            return;
        }

        if (args.length > 1) {
            //handle subcommand help info
            String subcommandStr = args[1];
            CommandEnum subcommand = CommandEnum.fromString(subcommandStr);

            HandlerFactory factory = new HandlerFactory();
            String helpStr = factory.getHandler(subcommand).getHelp();
            consumer.receiveResponse(new CommandResponse(helpStr));
        } else {
            // display generic help data
            consumer.receiveResponse(new CommandResponse(getHelp()));
        }
        return;
    }

    @Override
    public String getHelp() {
        StringBuilder builder = new StringBuilder();
        builder.append("`help <command_name>` will return usage info for each command. Valid commands are:\n");
        Arrays.stream(CommandEnum.values()).forEach(
            value -> {
                if (value != CommandEnum.HELP && value != CommandEnum.INVALID) {
                    HandlerFactory factory = new HandlerFactory();
                    builder.append("- " + value.toString() + "\n");
                }
            }
        );
        return builder.toString();
    }
}
