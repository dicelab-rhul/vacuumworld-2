package uk.ac.rhul.cs.dice.vacuumworld.agent.minds;

import java.util.Collection;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.AbstractVacuumWorldAgentMind;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldPerception;

public class VacuumWorldRandomMind extends AbstractVacuumWorldAgentMind {

	@Override
	public void perceive(Collection<VacuumWorldPerception> perceptions) {
		System.out.println(perceptions);
	}

	@Override
	public VacuumWorldAction decide() {
		return new VacuumWorldSensingAction();
		// Random random = new Random();
		// if (random.nextBoolean()) {
		// return new MoveAction();
		// } else {
		// return new TurnAction(null);
		// }
	}

	@Override
	public VacuumWorldAction execute(VacuumWorldAction action) {
		return action;
	}

}
