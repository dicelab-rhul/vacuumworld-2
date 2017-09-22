package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import uk.ac.rhul.cs.dice.starworlds.appearances.PhysicalBodyAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class DirtAppearance extends PhysicalBodyAppearance implements
		VacuumWorldBodyAppearance<DirtAppearanceReadOnly> {

	private static final long serialVersionUID = 3721595240698765337L;

	private BodyColor color;
	private Position position;

	public DirtAppearance(BodyColor color) {
		super(Dirt.class);
		this.color = color;
	}

	public BodyColor getColor() {
		return color;
	}

	public void setColor(BodyColor color) {
		this.color = color;
	}

	@Override
	public Position getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public Class<DirtAppearanceReadOnly> getReadOnlyClass() {
		return DirtAppearanceReadOnly.class;
	}

	@Override
	public String toString() {
		return this.getBody().getSimpleName() + " : " + this.position + ", "
				+ this.color;
	}
}