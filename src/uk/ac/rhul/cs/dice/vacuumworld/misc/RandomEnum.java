package uk.ac.rhul.cs.dice.vacuumworld.misc;

public class RandomEnum {

	public static <T extends Enum<?>> T getRandom(Class<T> type) {
		T[] values = type.getEnumConstants();
		return values[(int) Math.ceil(Math.random() * values.length) - 1];
	}
}
