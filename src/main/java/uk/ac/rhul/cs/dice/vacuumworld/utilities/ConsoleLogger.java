package uk.ac.rhul.cs.dice.vacuumworld.utilities;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ConsoleLogger extends Logger {

    private static String name = "ConsolLogger";
    private static final Logger INSTANCE = Logger.getLogger(name);

    private ConsoleLogger() {
	super(name, null);
	this.setLevel(Level.ALL);
	ConsoleHandler handler = new ConsoleHandler();
	handler.setFormatter(new SimpleFormatter());
	handler.setLevel(Level.ALL);
	this.addHandler(handler);
    }

    public static Logger getInstance() {
	return INSTANCE;
    }
}
