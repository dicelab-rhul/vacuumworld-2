package uk.ac.rhul.cs.dice.vacuumworld.saveload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import uk.ac.rhul.cs.dice.vacuumworld.utilities.VacuumWorldSaveLoadException;

public class Loader {

    private static final String FAILEDTOLOAD = "Failed to load save file: ";

    private Loader() {
	super();
    }

    public static Serializable load(String filename) throws VacuumWorldSaveLoadException {
	return load(new File(filename));
    }

    public static Serializable load(File file) throws VacuumWorldSaveLoadException {
	try (FileInputStream in = new FileInputStream(file); ObjectInputStream stream = new ObjectInputStream(in)) {
	    Serializable result = (Serializable) stream.readObject();
	    stream.close();
	    return result;
	} catch (FileNotFoundException fnfe) {
	    throw new VacuumWorldSaveLoadException(FAILEDTOLOAD + file + " as it does not exist", fnfe);
	} catch (ClassNotFoundException cnfe) {
	    throw new VacuumWorldSaveLoadException(FAILEDTOLOAD + file + " deserialization failed", cnfe);
	} catch (IOException ioe) {
	    throw new VacuumWorldSaveLoadException(FAILEDTOLOAD + file, ioe);
	}
    }
}
