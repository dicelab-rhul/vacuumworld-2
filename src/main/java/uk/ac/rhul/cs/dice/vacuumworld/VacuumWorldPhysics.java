package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.ArrayList;
import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldAmbient.PerceptionQuery;
import uk.ac.rhul.cs.dice.vacuumworld.actions.CleanAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.MoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.PlaceDirtAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.TurnAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldCommunicationAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldSeeingSensor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridContent;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.LogUtils;

/**
 * The {@link Physics} of {@link VacuumWorld}. Contain all the necessary
 * {@link Action} methods etc. The possible {@link Action}s in
 * {@link VacuumWorld} defined by this {@link Physics} are: </br>
 * <ul>
 * <li>{@link CleanAction}
 * <li>{@link MoveAction}
 * <li>{@link TurnAction}
 * <li>{@link PlaceDirtAction}
 * <li>{@link VacuumWorldCommunicationAction}
 * <li>{@link VacuumWorldSensingAction}
 * </ul>
 * All of which are subclasses of {@link VacuumWorldAction}. </br>
 * The {@link Perception} that all {@link Agent}s receive, provided by
 * {@link VacuumWorldPhysics#activeBodyPerceive(ActiveBodyAppearance, Action, Ambient)}
 * is a {@link VacuumWorldGridPerception} whose content is
 * {@link VacuumWorldGridContent}, see {@link PerceptionQuery}.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class VacuumWorldPhysics extends AbstractPhysics {
    private Position opt; // this is a bit hacky! beware doing this!

    public boolean perceivable(VacuumWorldSeeingSensor sensor, AbstractPerception<?> perception, Ambient context) {
	if (sensor != null) {
	    return super.perceivable(sensor, perception, context);
	} else {
	    return false;
	}
    }

    @Override
    public void simulate() {
	while (!getEnvironment().shouldStop()) {
	    if (getEnvironment().isPaused()) {
		this.getEnvironment().setPausedSafe(true);
		simulateHelper();
	    }

	    LogUtils.log("*************CYCLE**************");
	    cycle();
	    this.getEnvironment().updateView();
	    sleep();
	}
    }

    private void simulateHelper() {
	while (getEnvironment().isPaused()) {
	    if (getEnvironment().isPaused()) {
		continue;
	    }
	}

	this.getEnvironment().setPausedSafe(false);
    }

    @Override
    public Collection<AbstractPerception<?>> activeBodyPerceive(ActiveBodyAppearance body, Action action,
	    Ambient context) {
	return getPerceptions((AbstractEnvironmentalAction) action, (VacuumWorldAmbient) context);
    }

    public Collection<AbstractPerception<?>> getPerceptions(AbstractEnvironmentalAction action,
	    VacuumWorldAmbient context) {
	Collection<AbstractPerception<?>> percepts = new ArrayList<>();
	percepts.add(new VacuumWorldGridPerception(context.getAgentPerception(action)));
	return percepts;
    }

    // THE PARAMETERS OF METHODS BELOW MUST BE UNUSED.
    // THE METHODS MUST BE DEFINED IN THIS WAY FOR THE SYSTEM TO WORK! THIS IS
    // BECAUSE THEY ARE ACCESSED VIA A REFLECTIVE CALL.

    // *************************************************************** //
    // ********************* PLACE ACTION METHODS ********************* //
    // *************************************************************** //

    public Collection<AbstractPerception<?>> getAgentPerceptions(PlaceDirtAction action, Ambient context) {
	return getPerceptions(action, (VacuumWorldAmbient) context);
    }

    public Collection<AbstractPerception<?>> getOtherPerceptions(PlaceDirtAction action, Ambient context) {
	if (action == null || context == null) {
	    throw new IllegalArgumentException();
	}

	return new ArrayList<>();
    }

    public boolean isPossible(PlaceDirtAction action, Ambient context) {
	if (action.getActor().getColor() == BodyColor.getUserColor()
		|| action.getActor().getColor() == BodyColor.getAvatarColor()) {
	    VacuumWorldAmbient ambient = (VacuumWorldAmbient) context;
	    return !ambient.getGrid().containsDirt(action.getActor().getPosition());
	}
	return false;
    }

    public boolean perform(PlaceDirtAction action, Ambient context) {
	VacuumWorldAmbient ambient = (VacuumWorldAmbient) context;
	ambient.placeDirt(action.getActor().getPosition(), new Dirt(action.getDirtColor()));

	return true;
    }

    public boolean verify(PlaceDirtAction action, Ambient context) {
	if (action == null || context == null) {
	    throw new IllegalArgumentException();
	}

	return true;
    }

    // *************************************************************** //
    // ********************* CLEAN ACTION METHODS ********************* //
    // *************************************************************** //

    public Collection<AbstractPerception<?>> getAgentPerceptions(CleanAction action, Ambient context) {
	return getPerceptions(action, (VacuumWorldAmbient) context);
    }

    public Collection<AbstractPerception<?>> getOtherPerceptions(CleanAction action, Ambient context) {
	if (action == null || context == null) {
	    throw new IllegalArgumentException();
	}

	return new ArrayList<>();
    }

    public boolean isPossible(CleanAction action, Ambient context) {
	VacuumWorldAmbient ambient = (VacuumWorldAmbient) context;
	VacuumWorldAgentAppearance actor = action.getActor();
	if (ambient.getGrid().containsDirt(actor.getPosition())) {
	    return actor.getColor().canClean(ambient.getDirt(action.getActor().getPosition()).getColor());
	}
	return false;
    }

    public boolean perform(CleanAction action, Ambient context) {
	((VacuumWorldAmbient) context).cleanDirt(action.getActor().getPosition());
	return true;
    }

    public boolean verify(CleanAction action, Ambient context) {
	if (action == null || context == null) {
	    throw new IllegalArgumentException();
	}

	return true;
    }

    // *************************************************************** //
    // ********************* TURN ACTION METHODS ********************* //
    // *************************************************************** //

    public Collection<AbstractPerception<?>> getAgentPerceptions(TurnAction action, Ambient context) {
	return getPerceptions(action, (VacuumWorldAmbient) context);
    }

    public Collection<AbstractPerception<?>> getOtherPerceptions(TurnAction action, Ambient context) {
	if (action == null || context == null) {
	    throw new IllegalArgumentException();
	}

	return new ArrayList<>();
    }

    public boolean isPossible(TurnAction action, Ambient context) {
	if (action == null || context == null) {
	    throw new IllegalArgumentException();
	}

	return true;
    }

    public boolean perform(TurnAction action, Ambient context) {
	if (action == null || context == null) {
	    throw new IllegalArgumentException();
	}

	VacuumWorldAgentAppearance app = action.getActor();
	app.setOrientation(action.getDirection().turn(app.getOrientation()));

	return true;
    }

    public boolean verify(TurnAction action, Ambient context) {
	if (action == null || context == null) {
	    throw new IllegalArgumentException();
	}

	return true;
    }

    // *************************************************************** //
    // ********************* MOVE ACTION METHODS ********************* //
    // *************************************************************** //

    public Collection<AbstractPerception<?>> getAgentPerceptions(MoveAction action, Ambient context) {
	return getPerceptions(action, (VacuumWorldAmbient) context);
    }

    public Collection<AbstractPerception<?>> getOtherPerceptions(MoveAction action, Ambient context) {
	if (action == null || context == null) {
	    throw new IllegalArgumentException();
	}

	return new ArrayList<>();
    }

    public boolean isPossible(MoveAction action, Ambient context) {
	VacuumWorldAmbient s = (VacuumWorldAmbient) context;
	Orientation o = action.getActor().getOrientation();
	Position p = action.getActor().getPosition();
	this.opt = new Position(p.getX() + o.getI(), p.getY() + o.getJ());
	if (!s.getGrid().outOfBounds(this.opt)) {
	    return !s.getGrid().containsAgent(this.opt);
	}
	return false;
    }

    public boolean perform(MoveAction action, Ambient context) {
	VacuumWorldAmbient s = (VacuumWorldAmbient) context;
	s.getGrid().moveAgent(action.getActor(), this.opt);
	return true;
    }

    public boolean verify(MoveAction action, Ambient context) {
	if (action == null || context == null) {
	    throw new IllegalArgumentException();
	}

	return true;
    }

    public VacuumWorldAmbient getState() {
	return (VacuumWorldAmbient) super.state;
    }

    @Override
    public VacuumWorldUniverse getEnvironment() {
	return (VacuumWorldUniverse) this.environment;
    }

    @Override
    public void cycleAddition() {
	// not needed?
    }
}