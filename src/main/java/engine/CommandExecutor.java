package engine;

import command.handlers.CommandHandler;
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

        try {
            Object result = future.get(handler.getTimeout(), TimeUnit.MILLISECONDS);
            // successful completion
        } catch (ExecutionException e) {
            e.printStackTrace();
            consumer.simpleResponse("ERROR: execution failed!");
        } catch (InterruptedException e) {
            e.printStackTrace();
            consumer.simpleResponse("ERROR: operation interrupted!");
        } catch (TimeoutException e) {
            future.cancel(true);
            consumer.simpleResponse("ERROR: Command operation timed out!");
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
