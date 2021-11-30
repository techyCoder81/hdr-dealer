package command;

import engine.CommandEngine;

public interface CommandProducer {
    /**
     * set the command engine for this producer instance.
     * any commands produced by the CommandProducer should
     * be handled by the given engine.
     * @param engine
     */
    void setCommandEngine(CommandEngine engine);
}
