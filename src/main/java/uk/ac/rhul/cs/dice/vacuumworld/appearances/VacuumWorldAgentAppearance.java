package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PhysicalBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.utils.Utils;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class VacuumWorldAgentAppearance extends ActiveBodyAppearance
	implements VacuumWorldBodyAppearance<VacuumWorldAgentAppearanceReadOnly> {

    private static final long serialVersionUID = 7665091635149107205L;
    private Position position;
    private Orientation orientation;
    private BodyColor color;

    public VacuumWorldAgentAppearance(String id, Class<? extends PhysicalBody> body,
	    Collection<Class<? extends Sensor>> sensors, Collection<Class<? extends Actuator>> actuators) {
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
	return this.getClass().getSimpleName() + " : " + this.position + ", " + this.color + ", " + this.orientation;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((color == null) ? 0 : color.hashCode());
	result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
	result = prime * result + ((position == null) ? 0 : position.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (Utils.equalsHelper(this, obj)) {
	    return true;
	}

	VacuumWorldAgentAppearance other = (VacuumWorldAgentAppearance) obj;

	if (other == null) {
	    return false;
	}

	if (color != other.color)
	    return false;
	if (orientation != other.orientation)
	    return false;
	if (position == null) {
	    if (other.position != null)
		return false;
	} else if (!position.equals(other.position))
	    return false;
	return true;
    }
}