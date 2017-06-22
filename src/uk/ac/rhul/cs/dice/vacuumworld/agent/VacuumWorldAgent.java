package uk.ac.rhul.cs.dice.vacuumworld.agent;

import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;

public class VacuumWorldAgent extends AbstractAgent {

	public VacuumWorldAgent(List<Sensor> sensors, List<Actuator> actuators,
			AbstractAgentMind mind) {
		super(null, sensors, actuators, mind);
		this.setAppearance(new VacuumWorldAgentAppearance(this));
	}

	@Override
	public VacuumWorldAgentAppearance getAppearance() {
		return (VacuumWorldAgentAppearance) super.getAppearance();
	}
}
