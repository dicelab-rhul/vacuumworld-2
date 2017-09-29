package uk.ac.rhul.cs.dice.vacuumworld.readonly;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorld;

public class ReadOnlyWrap {

	private static final String NICETRY = "Stop trying to change things youre not supposed to! ";

	private ReadOnlyWrap() {
	}

	public static <T, A extends ReadOnlyInterface<T>> T readOnlyCopy(A obj) {
		if (obj != null) {
			try {
				return obj.getReadOnlyClass().getConstructor(obj.getClass())
						.newInstance(obj);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				VacuumWorld.LOGGER.log(Level.SEVERE,
						"Failed to create a read only copy of Object: " + obj
								+ " of Type: " + obj.getClass(), e);
			}
		}
		return null;
	}

	public static void nicetry(String message) {
		String m = ReadOnlyWrap.NICETRY + message;
		VacuumWorld.LOGGER.log(Level.WARNING, m);
	}

	public static void nicetry() {
		VacuumWorld.LOGGER.log(Level.WARNING, ReadOnlyWrap.NICETRY);
	}
}
