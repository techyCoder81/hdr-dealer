package engine;

import command.handlers.CommandHandler;
import response.CommandResponse;
import response.ResponseConsumer;

import java.util.concurrent.*;

/**
 * this class creates a new thread for a command to be handled in,
 * and if that thread takes longer to finish execution than the 
 * assigned time for that command, this executor will kill the thread.
 */
public class CommandExecutor implements Runnable {
    CommandHandler handler;
    String[] args;
    ResponseConsumer consumer;

    public CommandExecutor(CommandHandler handler, String[] args, ResponseConsumer consumer) {
        this.handler = handler;
        this.args = args;
        this.consumer = consumer;
    }

    public void run() {
        // create and start a new executor thread for the command
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CommandRunnable runnable = new CommandRunnable(handler, args, consumer);
        Future<?> future = executor.submit(runnable);

        // get the intended timeout for the handler
        int timeout = handler.getTimeout() != 0 ? handler.getTimeout() : 5000;

        try {

            // run it against the timeout
            future.get(timeout, TimeUnit.MILLISECONDS);
            
            // if we make it here, this was a successful completion. Otherwise, it will throw.
        } catch (ExecutionException e) {
            e.printStackTrace();
            consumer.receiveResponse(new CommandResponse("ERROR: execution failed!"));
        } catch (InterruptedException e) {
            e.printStackTrace();
            consumer.receiveResponse(new CommandResponse("ERROR: operation interrupted!"));
        } catch (TimeoutException e) {
            future.cancel(true);
            consumer.receiveResponse(new CommandResponse("ERROR: Command operation timed out!"));
        }
        executor.shutdown();
    }

    /**
     * this runnable simply calls handle() for the command
     */
    private class CommandRunnable implements Runnable {
        CommandHandler handler;
        String[] args;
        ResponseConsumer consumer;

        public CommandRunnable(CommandHandler handler, String[] args, ResponseConsumer consumer) {
            this.handler = handler;
            this.args = args;
            this.consumer = consumer;
        }

        public void run() {
            handler.handle(args, consumer);
        }
    }
}
