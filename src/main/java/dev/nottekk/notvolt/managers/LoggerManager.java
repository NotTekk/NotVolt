package dev.nottekk.notvolt.managers;

import dev.nottekk.notvolt.formatters.LoggerFormatter;

import java.io.IOException;
import java.util.logging.*;

public class LoggerManager {
    private static FileHandler fileHandler;
    private static ConsoleHandler consoleHandler;

    static {
        try {
            fileHandler = new FileHandler("void.log", true);
            fileHandler.setFormatter(new LoggerFormatter());

            consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new LoggerFormatter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getLogger(Class<?> className) {
        Logger logger = Logger.getLogger(className.getName());
        logger.addHandler(fileHandler);
        logger.addHandler(consoleHandler);
        return logger;
    }
}
