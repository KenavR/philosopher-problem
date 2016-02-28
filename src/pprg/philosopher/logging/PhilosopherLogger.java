package pprg.philosopher.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhilosopherLogger extends Logger {

    private static Logger instance;

    private PhilosopherLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    public static Logger getInstance() {
        if(instance == null)  {
            instance =  Logger.getGlobal();
            Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.FINE);
            instance.addHandler(consoleHandler);
            instance.setLevel(Level.FINE);
        }
        return instance;
    }
}
