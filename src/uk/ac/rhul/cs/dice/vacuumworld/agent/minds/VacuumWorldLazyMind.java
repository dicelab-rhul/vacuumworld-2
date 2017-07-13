package uk.ac.rhul.cs.dice.vacuumworld.agent.minds;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.vacuumworld.actions.TurnAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldCommunicationAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldMind;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.misc.TurnDirection;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridContent;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldMessageContent;

public class VacuumWorldLazyMind extends VacuumWorldMind {

	private VacuumWorldGridContent percept = null;
	private Set<Position> dirtIveSeen = new HashSet<>();

	@Override
	public void perceive(
			VacuumWorldGridPerception perception,
			Collection<CommunicationPerception<VacuumWorldMessageContent>> messages) {
		if (perception != null) {
			this.percept = perception.getPerception();
		}
	}

	@Override
	public VacuumWorldAction decide() {
		// on the first cycle do a sensing action
		if (percept == null) {
			return new VacuumWorldSensingAction();
		}

		/*
		 * if I see some dirt, tell another agent to come and clean it, I am
		 * lazy!
		 */
		if (this.isDirtFoward(percept)) {
			Position dirtposition = this.getDirtFoward(percept).getPosition();
			if(!dirtIveSeen.contains(dirtposition)) {
				dirtIveSeen.add(dirtposition);
				// I seen some new dirt! let everyone know
				String message = "I see some dirt at: " + dirtposition.toString();
				System.out.println(message);
				return new VacuumWorldCommunicationAction(message);
			}
		}
		// if I dont see dirt, turn around until I do
		return new TurnAction(TurnDirection.LEFT);
	}

	@Override
	public VacuumWorldAction execute(VacuumWorldAction action) {
		percept = null;
		return action;
	}

}
