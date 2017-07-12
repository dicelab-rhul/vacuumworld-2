package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PhysicalBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class VacuumWorldAgentAppearance extends ActiveBodyAppearance implements
		VacuumWorldBodyAppearance<VacuumWorldAgentAppearanceReadOnly> {

	private static final long serialVersionUID = 7665091635149107205L;
	private Position position;
	private Orientation orientation;
	private BodyColor color;

	public VacuumWorldAgentAppearance(String id,
			Class<? extends PhysicalBody> body,
			Collection<Class<? extends Sensor>> sensors,
			Collection<Class<? extends Actuator>> actuators) {
		super(id, body, null, null);
		this.setActuators(actuators);
		this.setSensors(sensors);
	}

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

	@Override
	public Class<VacuumWorldAgentAppearanceReadOnly> getReadOnlyClass() {
		return VacuumWorldAgentAppearanceReadOnly.class;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " : " + this.position + ", "
				+ this.color + ", " + this.orientation;
	}
}
