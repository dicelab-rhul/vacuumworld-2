package uk.ac.rhul.cs.dice.vacuumworld.misc;

import java.lang.reflect.Method;

import uk.ac.rhul.cs.dice.vacuumworld.actions.TurnAction;

/**
 * The enum used to indicate the direction to turn when attempting a
 * {@link TurnAction}.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public enum TurnDirection {

	LEFT(getTurnMethod("getLeft")), RIGHT(getTurnMethod("getRight"));

	private Method turn;

	private TurnDirection(Method turn) {
		this.turn = turn;
	}

	/**
	 * Given an {@link Orientation}, this method will 'turn' it in the direction
	 * specified by this {@link TurnDirection}.
	 * 
	 * @param orientation
	 *            : to turn
	 * @return the turned {@link Orientation}.
	 * @throws Exception
	 */
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
