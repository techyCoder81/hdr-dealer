package command.handlers;

import response.CommandResponse;
import response.ResponseConsumer;

public class MakeItHandler extends CommandHandler {
    @Override
    public void handle(String[] arguments, ResponseConsumer consumer) {
        consumer.receiveResponse(
            CommandResponse.fromString(
                "https://cdn.discordapp.com/attachments/837096691936133130/915054913829081168/xnoWC6GpPxG7lnEZ-OGzEjapAC4bk01uaEheJyghH7A.png"
            )
        );
    }

    @Override
    public String getHelp() {
        return "Outputs the 'YOU MAKE IT THEN' image";
    }
}
