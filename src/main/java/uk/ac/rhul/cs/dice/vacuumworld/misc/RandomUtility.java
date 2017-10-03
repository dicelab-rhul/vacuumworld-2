package uk.ac.rhul.cs.dice.vacuumworld.misc;

import java.util.Random;

public class RandomUtility {

    private RandomUtility() {
    }

    public static <T extends Enum<?>> T getRandomEnum(Class<T> type) {
	T[] values = type.getEnumConstants();
	return values[new Random().nextInt(values.length)];
    }

    public static <T> T getRandom(T[] array) {
	if (array == null) {
	    return null;
	}
	if (array.length == 0) {
	    return null;
	}
	Random random = new Random();
	return array[random.nextInt(array.length)];
    }
}
