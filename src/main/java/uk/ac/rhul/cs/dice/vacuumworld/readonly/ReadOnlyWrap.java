package uk.ac.rhul.cs.dice.vacuumworld.readonly;

import java.lang.reflect.InvocationTargetException;

public class ReadOnlyWrap {

	private static final String NICETRY = "Stop trying to change things youre not supposed to!";

	private ReadOnlyWrap() {
	}

	public static <T, A extends ReadOnlyInterface<T>> T readOnlyCopy(A obj)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		if (obj != null) {
			return obj.getReadOnlyClass().getConstructor(obj.getClass())
					.newInstance(obj);
		}
		return null;
	}

	public static void nicetry(String message) {
		System.out.println(ReadOnlyWrap.NICETRY + " " + message);
	}

	public static void nicetry() {
		System.out.println(ReadOnlyWrap.NICETRY);
	}
}
