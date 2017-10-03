package uk.ac.rhul.cs.dice.vacuumworld.readonly;

import java.lang.reflect.InvocationTargetException;

import uk.ac.rhul.cs.dice.vacuumworld.utilities.LogUtils;

public class ReadOnlyWrap {

    private static final String NICETRY = "Stop trying to change things youre not supposed to! ";

    private ReadOnlyWrap() {
    }

    public static <T, A extends ReadOnlyInterface<T>> T readOnlyCopy(A obj) {
	if (obj != null) {
	    try {
		return obj.getReadOnlyClass().getConstructor(obj.getClass()).newInstance(obj);
	    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
		    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
		LogUtils.log(e);
	    }
	}
	return null;
    }

    public static void nicetry(String message) {
	String m = ReadOnlyWrap.NICETRY + message;
	LogUtils.log(m);
    }

    public static void nicetry() {
	LogUtils.log(ReadOnlyWrap.NICETRY);
    }
}
