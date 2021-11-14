package engine;

import command.handlers.CommandHandler;
import response.CommandResponse;
import response.ResponseConsumer;

import java.util.concurrent.*;

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
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CommandRunnable runnable = new CommandRunnable(handler, args, consumer);
        Future<?> future = executor.submit(runnable);
        int timeout = handler.getTimeout() != 0 ? handler.getTimeout() : 5000;

        try {
            Object result = future.get(timeout, TimeUnit.MILLISECONDS);
            // successful completion
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
