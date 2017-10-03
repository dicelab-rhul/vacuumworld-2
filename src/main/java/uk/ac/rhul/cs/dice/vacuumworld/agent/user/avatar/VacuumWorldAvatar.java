package uk.ac.rhul.cs.dice.vacuumworld.agent.user.avatar;

import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarMind;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;

public class VacuumWorldAvatar extends AbstractAvatarAgent<VacuumWorldAction> {

    public VacuumWorldAvatar(List<Sensor> sensors, List<Actuator> actuators,
	    AbstractAvatarMind<VacuumWorldAction> mind) {
	super(sensors, actuators, mind);
	this.setAppearance(new VacuumWorldAgentAppearance(this));
    }

    @Override
    public VacuumWorldAgentAppearance getAppearance() {
	return (VacuumWorldAgentAppearance) super.getAppearance();
    }

}
