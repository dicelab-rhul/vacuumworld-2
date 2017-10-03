package uk.ac.rhul.cs.dice.vacuumworld.utilities;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class SuperSimpleFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
	StringBuilder builder = new StringBuilder();
	builder.append(record.getLevel() + ": ");
	builder.append(record.getMessage());
	builder.append(System.lineSeparator());
	return builder.toString();
    }

}
