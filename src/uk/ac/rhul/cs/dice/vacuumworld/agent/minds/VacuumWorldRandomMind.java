package uk.ac.rhul.cs.dice.vacuumworld.agent.minds;

import java.util.Collection;
import java.util.Random;

import uk.ac.rhul.cs.dice.vacuumworld.actions.CleanAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.MoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.TurnAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgentMind;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldPerceptionContent;

public class VacuumWorldRandomMind extends VacuumWorldAgentMind {

	private VacuumWorldPerceptionContent percept;
	private Random random = new Random();

	@Override
	public void perceive(Collection<VacuumWorldPerception> perceptions) {
		if (!perceptions.isEmpty()) {
			percept = perceptions.toArray(new VacuumWorldPerception[] {})[0]
					.getPerception();
			System.out.println(this.getId() + "->" + percept.getSelf());
			System.out.println(this.getId() + "->" + percept.getAgents());
			System.out.println(this.getId() + "->" + percept.getDirts());
			System.out.println(this.getId() + "->" + percept.getWalls());
		}
	}

	@Override
	public VacuumWorldAction decide() {
		if (percept == null) {
			return new VacuumWorldSensingAction();
		}
		DirtAppearance ondirt;
		if ((ondirt = super.onDirt(percept)) != null) {
			if (canCleanDirt(ondirt)) {
				return new CleanAction();
			}
		}
		if (random.nextBoolean()) {
			return new MoveAction();
		} else {
			return new TurnAction(null);
		}
	}

	@Override
	public VacuumWorldAction execute(VacuumWorldAction action) {
		percept = null;
		return action;
	}

}
