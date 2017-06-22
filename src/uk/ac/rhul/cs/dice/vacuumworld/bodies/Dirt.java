package uk.ac.rhul.cs.dice.vacuumworld.bodies;

import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;

public class Dirt extends PassiveBody {

	public Dirt(DirtColor color) {
		super(new DirtAppearance(IDFactory.getInstance().getNewID(), color));
	}

	public void getColor() {
		this.getAppearance().getColor();
	}

	@Override
	public DirtAppearance getAppearance() {
		return (DirtAppearance) super.getAppearance();
	}

	public enum DirtColor {
		WHITE, GREEN, ORANGE;

		@Override
		public String toString() {
			return super.toString().substring(0, 1);
		}
	}
}
