package uk.ac.rhul.cs.dice.vacuumworld.misc;

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

    LEFT(new TurnLeft()), RIGHT(new TurnRight());

    private Turn turn;

    private static interface Turn {
	public Orientation turn(Orientation orientation);
    }

    static class TurnLeft implements Turn {

	@Override
	public Orientation turn(Orientation orientation) {
	    return orientation.getLeft();
	}
    }

    static class TurnRight implements Turn {

	@Override
	public Orientation turn(Orientation orientation) {
	    return orientation.getRight();
	}

    }

    private TurnDirection(Turn turn) {
	this.turn = turn;
    }

    /**
     * Given an {@link Orientation}, this method will 'turn' it in the direction
     * specified by this {@link TurnDirection}.
     * 
     * @param orientation
     *            : to turn
     * @return the turned {@link Orientation}.
     */
    public Orientation turn(Orientation orientation) {
	return turn.turn(orientation);
    }
}
