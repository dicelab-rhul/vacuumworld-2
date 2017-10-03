package uk.ac.rhul.cs.dice.vacuumworld.agent;

import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Sensor;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldUniverse;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;

/**
 * The {@link Agent} that resides in the {@link VacuumWorldUniverse}.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class VacuumWorldAgent extends AbstractAutonomousAgent {

    public VacuumWorldAgent(List<Sensor> sensors, List<Actuator> actuators, AbstractAgentMind mind) {
	super(null, sensors, actuators, mind);
	this.setAppearance(new VacuumWorldAgentAppearance(this));
    }

    @Override
    public VacuumWorldAgentAppearance getAppearance() {
	return (VacuumWorldAgentAppearance) super.getAppearance();
    }
}
