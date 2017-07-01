package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.readonly.ReadOnlyInterface;

public interface VacuumWorldBodyAppearance<T> extends ReadOnlyInterface<T> {

	public Position getPosition();

	public void setPosition(Position position);

}
