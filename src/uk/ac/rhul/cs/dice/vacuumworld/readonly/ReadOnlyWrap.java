package uk.ac.rhul.cs.dice.vacuumworld.readonly;

public class ReadOnlyWrap {

	private static String NICETRY = "Stop trying to change things youre not supposed to!";

	public static <T, A extends ReadOnlyInterface<T>> T readOnlyCopy(A obj)
			throws Exception {
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
