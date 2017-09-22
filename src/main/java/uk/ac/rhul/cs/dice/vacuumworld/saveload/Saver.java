package uk.ac.rhul.cs.dice.vacuumworld.saveload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Saver {

	public static void save(String filename, Serializable data)
			throws IOException {
		save(new File(filename), data);
	}

	public static void save(File file, Serializable data) throws IOException {
		FileOutputStream out = new FileOutputStream(file);
		ObjectOutputStream stream = new ObjectOutputStream(out);
		stream.writeObject(data);
		stream.close();
	}
}
