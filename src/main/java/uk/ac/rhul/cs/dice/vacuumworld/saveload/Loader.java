package uk.ac.rhul.cs.dice.vacuumworld.saveload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Loader {

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
		} catch (ClassNotFoundException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		return null;
	}
}
