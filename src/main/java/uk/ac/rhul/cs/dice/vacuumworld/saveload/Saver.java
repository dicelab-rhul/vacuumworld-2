package uk.ac.rhul.cs.dice.vacuumworld.saveload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import uk.ac.rhul.cs.dice.vacuumworld.utilities.VacuumWorldSaveLoadException;

public class Saver {

    private Saver() {
	super();
    }

    public static void save(String filename, Serializable data) throws VacuumWorldSaveLoadException {
	save(new File(filename), data);
    }

    public static void save(File file, Serializable data) throws VacuumWorldSaveLoadException {
	try (FileOutputStream out = new FileOutputStream(file);
		ObjectOutputStream stream = new ObjectOutputStream(out)) {
	    stream.writeObject(data);
	    out.close();
	    stream.close();
	} catch (IOException e) {
	    throw new VacuumWorldSaveLoadException("Failed to save to file: " + file, e);
	}
    }
}
