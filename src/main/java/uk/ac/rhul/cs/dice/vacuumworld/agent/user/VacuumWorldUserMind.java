package uk.ac.rhul.cs.dice.vacuumworld.agent.user;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.vacuumworld.actions.MoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.PlaceDirtAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.TurnAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldMind;
import uk.ac.rhul.cs.dice.vacuumworld.misc.TurnDirection;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridContent;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldMessageContent;

@UserMindAnnotation
public class VacuumWorldUserMind extends VacuumWorldMind {
    private static int messiness = 4;
    private static double mobility = 0.8;
    private boolean actionfailed = false;
    private int actioncounter = 0;
    private Class<?> lastaction = VacuumWorldSensingAction.class;
    private VacuumWorldGridContent currentpercept;
    private boolean justTurned = false;

    @Override
    public void perceive(VacuumWorldGridPerception gridperception,
	    Collection<CommunicationPerception<VacuumWorldMessageContent>> messages) {
	if (gridperception != null) {
	    actionfailed = false;
	    currentpercept = gridperception.getPerception();
	} else {
	    actionfailed = true;
	}
    }

    private boolean filledForward;
    private boolean filledLeft;
    private boolean filledRight;

    @Override
    public VacuumWorldAction decide() {
	if (!actionfailed) {
	    if (MoveAction.class.isAssignableFrom(lastaction)) {
		actioncounter++;
	    } else if (TurnAction.class.isAssignableFrom(lastaction)) {
		justTurned = true;
	    }
	}
	if (currentpercept == null) {
	    return new VacuumWorldSensingAction();
	}
	filledForward = !this.currentpercept.isForwardAccessible();
	filledLeft = !this.currentpercept.isLeftAccessible();
	filledRight = !this.currentpercept.isRightAccessible();
	VacuumWorldAction action;
	if ((action = doMessAction()) != null) {
	    return action;
	}
	if ((action = doWallAction()) != null) {
	    return action;
	}
	return doRandomAction();
    }

    private VacuumWorldAction doRandomAction() {
	if (Math.random() <= mobility || justTurned) {
	    justTurned = false;
	    return new MoveAction();
	} else {
	    return performRandomAction();
	}
    }

    private VacuumWorldAction performRandomAction() {
	if (filledLeft && filledRight || !filledLeft && !filledRight) {
	    return new TurnAction(null);
	} else if (filledLeft) {
	    return new TurnAction(TurnDirection.RIGHT);
	} else {
	    return new TurnAction(TurnDirection.LEFT);
	}
    }

    private VacuumWorldAction doMessAction() {
	if (actioncounter % messiness == 0) {
	    actioncounter++;
	    if (!this.currentpercept.isDirtOnAgentPosition()) {
		return new PlaceDirtAction(null);
	    }
	}
	return null;
    }

    private VacuumWorldAction doWallAction() {
	if (filledForward) {
	    if (filledLeft) {
		return new TurnAction(TurnDirection.RIGHT);
	    } else if (filledRight) {
		return new TurnAction(TurnDirection.LEFT);
	    } else {
		return new TurnAction(null);
	    }
	}
	return null;
    }

    @Override
    public VacuumWorldAction execute(VacuumWorldAction action) {
	lastaction = action.getClass();
	currentpercept = null;
	return action;
    }
}