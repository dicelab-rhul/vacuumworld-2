package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SeeingSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.State;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.vacuumworld.actions.MoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.TurnAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldSeeingSensor;

public class VacuumWorldPhysics extends AbstractPhysics {

	public VacuumWorldPhysics() {
		super();
	}

	@Override
	protected void cycle() {
		System.out.println("******* CYCLE *******");
		super.cycle();
	}

	public boolean perceivable(VacuumWorldSeeingSensor sensor,
			AbstractPerception<?> perception, State context) {
		return super.perceivable(sensor, perception, context);
	}

	// *************************************************************** //
	// ********************* TURN ACTION METHODS ********************* //
	// *************************************************************** //

	public Collection<AbstractPerception<?>> getAgentPerceptions(
			TurnAction action, State context) {
		return null;
	}

	public Collection<AbstractPerception<?>> getOtherPerceptions(
			TurnAction action, State context) {
		return null;
	}

	public boolean perform(TurnAction action, State context) {
		return true;
	}

	public boolean isPossible(TurnAction action, State context) {
		return true;
	}

	public boolean verify(TurnAction action, State context) {
		return true;
	}

	// *************************************************************** //
	// ********************* MOVE ACTION METHODS ********************* //
	// *************************************************************** //

	public Collection<AbstractPerception<?>> getAgentPerceptions(
			MoveAction action, State context) {
		return null;
	}

	public Collection<AbstractPerception<?>> getOtherPerceptions(
			MoveAction action, State context) {
		return null;
	}

	public boolean perform(MoveAction action, State context) {
		System.out.println("PERFORMING MOVE ACTION");
		return true;
	}

	public boolean isPossible(MoveAction action, State context) {
		return true;
	}

	public boolean verify(MoveAction action, State context) {
		return true;
	}
}
