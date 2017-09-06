package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import java.util.Collection;
import java.util.Collections;

import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.readonly.ReadOnlyWrap;

public class VacuumWorldAgentAppearanceReadOnly extends
		VacuumWorldAgentAppearance {

	private static final String APPEARANCETOSTRING = "VacuumWorldAgent{";
	private static final long serialVersionUID = 3495451081936308325L;

	public VacuumWorldAgentAppearanceReadOnly(
			VacuumWorldAgentAppearance appearance) {
		super(appearance.getId(), appearance.getBody(),
				appearance.getSensors(), appearance.getActuators());
		super.setColor(appearance.getColor());
		super.setOrientation(appearance.getOrientation());
		try {
			super.setPosition(ReadOnlyWrap.readOnlyCopy(appearance
					.getPosition()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Collection<Class<? extends Actuator>> getActuators() {
		return Collections.unmodifiableCollection(super.getActuators());
	}

	@Override
	public Collection<Class<? extends Sensor>> getSensors() {
		return Collections.unmodifiableCollection(super.getSensors());
	}

	@Override
	public void setColor(BodyColor color) {
		ReadOnlyWrap.nicetry(this.getClass().getSimpleName() + " color");
	}

	@Override
	public void setId(String id) {
		ReadOnlyWrap.nicetry(this.getClass().getSimpleName() + " id");
	}

	@Override
	public void setOrientation(Orientation orientation) {
		ReadOnlyWrap.nicetry(this.getClass().getSimpleName() + " orientation");
	}

	@Override
	public void setPosition(Position position) {
		ReadOnlyWrap.nicetry(this.getClass().getSimpleName() + " position");
	}
	
	@Override
	public String toString() {
		return APPEARANCETOSTRING + getColor() + ","  + getPosition() + ","
				 + getOrientation() + "}";
	}
}
