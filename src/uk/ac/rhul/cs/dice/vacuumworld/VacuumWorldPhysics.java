package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.ArrayList;
import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.vacuumworld.actions.CleanAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.MoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.TurnAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldSeeingSensor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldPerception;

public class VacuumWorldPhysics extends AbstractPhysics {

	public VacuumWorldPhysics() {
		super();
	}

	@Override
	protected void cycle() {
		System.out.println("******* CYCLE *******");
		super.cycle();
		this.getEnvironment().updateView();
	}

	public boolean perceivable(VacuumWorldSeeingSensor sensor,
			AbstractPerception<?> perception, Ambient context) {
		return super.perceivable(sensor, perception, context);
	}

	public Collection<AbstractPerception<?>> getPerceptions(
			AbstractEnvironmentalAction action, VacuumWorldAmbient ambient) {
		Collection<AbstractPerception<?>> percepts = new ArrayList<>();
		percepts.add(new VacuumWorldPerception(ambient
				.getAgentPerception(action)));
		return percepts;
	}

	// *************************************************************** //
	// ********************* CLEAN ACTION METHODS ********************* //
	// *************************************************************** //

	public Collection<AbstractPerception<?>> getAgentPerceptions(
			CleanAction action, Ambient context) {
		return getPerceptions(action, (VacuumWorldAmbient) context);
	}

	public Collection<AbstractPerception<?>> getOtherPerceptions(
			CleanAction action, Ambient context) {
		return null;
	}

	public boolean isPossible(CleanAction action, Ambient context) {
		VacuumWorldAmbient ambient = (VacuumWorldAmbient) context;
		VacuumWorldAgentAppearance actor = action.getActor();
		if (ambient.containsDirt(actor.getPosition())) {
			return actor.getColor()
					.canClean(
							ambient.getDirt(action.getActor().getPosition())
									.getColor());
		}
		return false;
	}

	public boolean perform(CleanAction action, Ambient context)
			throws Exception {
		((VacuumWorldAmbient) context).cleanDirt(action.getActor()
				.getPosition());
		return true;
	}

	public boolean verify(CleanAction action, Ambient context) {
		return true;
	}

	// *************************************************************** //
	// ********************* TURN ACTION METHODS ********************* //
	// *************************************************************** //

	public Collection<AbstractPerception<?>> getAgentPerceptions(
			TurnAction action, Ambient context) {
		return getPerceptions(action, (VacuumWorldAmbient) context);
	}

	public Collection<AbstractPerception<?>> getOtherPerceptions(
			TurnAction action, Ambient context) {
		return null;
	}

	public boolean isPossible(TurnAction action, Ambient context) {
		return true;
	}

	public boolean perform(TurnAction action, Ambient context) throws Exception {
		VacuumWorldAgentAppearance app = (VacuumWorldAgentAppearance) action
				.getActor();
		app.setOrientation(action.getDirection().turn(app.getOrientation()));
		return true;
	}

	public boolean verify(TurnAction action, Ambient context) {
		return true;
	}

	// *************************************************************** //
	// ********************* MOVE ACTION METHODS ********************* //
	// *************************************************************** //

	public Collection<AbstractPerception<?>> getAgentPerceptions(
			MoveAction action, Ambient context) {
		return getPerceptions(action, (VacuumWorldAmbient) context);
	}

	public Collection<AbstractPerception<?>> getOtherPerceptions(
			MoveAction action, Ambient context) {
		return null;
	}

	private Position opt; // this is a bit hacky! beware doing this!

	public boolean isPossible(MoveAction action, Ambient context) {
		VacuumWorldAmbient s = (VacuumWorldAmbient) context;
		Orientation o = action.getActor().getOrientation();
		Position p = action.getActor().getPosition();
		opt = new Position(p.getX() + o.getI(), p.getY() + o.getJ());
		if (!s.outOfBounds(opt)) {
			return !s.containsAgent(opt);
		}
		return false;
	}

	public boolean perform(MoveAction action, Ambient context) {
		VacuumWorldAmbient s = (VacuumWorldAmbient) context;
		s.moveAgent(action.getActor(), opt);
		return true;
	}

	public boolean verify(MoveAction action, Ambient context) {
		return true;
	}

	public VacuumWorldAmbient getState() {
		return (VacuumWorldAmbient) super.state;
	}

	public VacuumWorldUniverse getEnvironment() {
		return (VacuumWorldUniverse) this.environment;
	}

	@Override
	public void cycleAddition() {
	}
}
