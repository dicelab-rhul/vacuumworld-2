package uk.ac.rhul.cs.dice.vacuumworld.misc;

import uk.ac.rhul.cs.dice.vacuumworld.readonly.ReadOnlyWrap;

public class PositionReadOnly extends Position {

	private static final long serialVersionUID = -2729496233091137597L;

	public PositionReadOnly(Position position) {
		super(position.getX(), position.getY());
	}

	@Override
	public void setX(Integer x) {
		ReadOnlyWrap.nicetry(this.getClass().getSimpleName() + " x");
	}

	@Override
	public void setY(Integer y) {
		ReadOnlyWrap.nicetry(this.getClass().getSimpleName() + " y");
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "READONLY " + super.toString();
	}

}
