package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class VacuumWorldAgentAppearance extends ActiveBodyAppearance implements
		VacuumWorldBodyAppearance {

	private static final long serialVersionUID = 1L;
	private Position position;
	private Orientation orientation;
	private BodyColor color;

	public VacuumWorldAgentAppearance(ActiveBody body) {
		super(body);
	}

	@Override
	public Position getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(Position position) {
		this.position = position;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public BodyColor getColor() {
		return color;
	}

	public void setColor(BodyColor color) {
		this.color = color;
	}
}
