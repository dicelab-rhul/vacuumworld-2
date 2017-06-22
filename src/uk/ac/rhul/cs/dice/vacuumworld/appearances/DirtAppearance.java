package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import uk.ac.rhul.cs.dice.starworlds.appearances.PhysicalBodyAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt.DirtColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class DirtAppearance extends PhysicalBodyAppearance implements
		VacuumWorldBodyAppearance {

	private static final long serialVersionUID = 3721595240698765337L;

	private DirtColor color;
	private Position position;

	public DirtAppearance(String id, DirtColor color) {
		super(id, Dirt.class);
		this.setColor(color);
	}

	public DirtColor getColor() {
		return color;
	}

	public void setColor(DirtColor color) {
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
}
