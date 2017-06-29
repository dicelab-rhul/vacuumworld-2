package uk.ac.rhul.cs.dice.vacuumworld.misc;

import java.lang.reflect.Method;

public enum TurnDirection  {

	LEFT(getTurnMethod("getLeft")), RIGHT(getTurnMethod("getRight"));

	private Method turn;

	private TurnDirection(Method turn) {
		this.turn = turn;
	}

	public Orientation turn(Orientation orientation) throws Exception {
		return (Orientation) turn.invoke(this, orientation);
	}

	private static Method getTurnMethod(String name) {
		try {
			return Orientation.class.getMethod(name, Orientation.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
}
