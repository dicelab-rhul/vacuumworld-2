package uk.ac.rhul.cs.dice.vacuumworld.bodies;

import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;

public class Dirt extends PassiveBody {

	public Dirt(BodyColor color) {
		super(new DirtAppearance(color));
	}

	public void getColor() {
		this.getAppearance().getColor();
	}

	@Override
	public DirtAppearance getAppearance() {
		return (DirtAppearance) super.getAppearance();
	}
}
