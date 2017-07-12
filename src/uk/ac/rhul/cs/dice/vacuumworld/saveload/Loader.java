package uk.ac.rhul.cs.dice.vacuumworld.saveload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Loader {

	public static Serializable load(String filename)
			throws ClassNotFoundException, IOException {
		return load(new File(filename));
	}

	public static Serializable load(File file) throws ClassNotFoundException,
			IOException {
		FileInputStream in = new FileInputStream(file);
		ObjectInputStream stream = new ObjectInputStream(in);
		Serializable result = (Serializable) stream.readObject();
		stream.close();
		return result;
	}
}
