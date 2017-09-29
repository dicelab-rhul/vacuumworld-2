package uk.ac.rhul.cs.dice.vacuumworld.saveload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.logging.Level;

import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorld;

public class Loader {

	private static final String FAILEDTOLOAD = "Failed to load save file: ";

	private Loader() {
		super();
	}

	public static Serializable load(String filename) {
		return load(new File(filename));
	}

	public static Serializable load(File file) {
		try (FileInputStream in = new FileInputStream(file);
				ObjectInputStream stream = new ObjectInputStream(in)) {
			Serializable result = (Serializable) stream.readObject();
			stream.close();
			return result;
		} catch (FileNotFoundException fnfe) {
			VacuumWorld.LOGGER.log(Level.SEVERE, FAILEDTOLOAD + file
					+ " as it doesnt exist", fnfe);
		} catch (ClassNotFoundException cnfe) {
			VacuumWorld.LOGGER.log(Level.SEVERE, FAILEDTOLOAD + file
					+ " deserialization failed", cnfe);
		} catch (IOException ioe) {
			VacuumWorld.LOGGER.log(Level.SEVERE, FAILEDTOLOAD + file, ioe);
		}
		return null;
	}
}
