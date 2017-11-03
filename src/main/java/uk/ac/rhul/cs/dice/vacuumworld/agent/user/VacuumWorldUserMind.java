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
    private boolean filledForward;
    private boolean filledLeft;
    private boolean filledRight;

    @Override
    public void perceive(VacuumWorldGridPerception gridperception, Collection<CommunicationPerception<VacuumWorldMessageContent>> messages) {
	if (gridperception != null) {
	    this.actionfailed = false;
	    this.currentpercept = gridperception.getPerception();
	}
	else {
	    this.actionfailed = true;
	}
    }

    @Override
    public VacuumWorldAction decide() {
	if (!this.actionfailed) {
	    if (MoveAction.class.isAssignableFrom(this.lastaction)) {
		this.actioncounter++;
	    }
	    else if (TurnAction.class.isAssignableFrom(this.lastaction)) {
		this.justTurned = true;
	    }
	}
	
	if (this.currentpercept == null) {
	    return new VacuumWorldSensingAction();
	}
	
	this.filledForward = !this.currentpercept.isForwardAccessible();
	this.filledLeft = !this.currentpercept.isLeftAccessible();
	this.filledRight = !this.currentpercept.isRightAccessible();
	
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
	if (Math.random() <= VacuumWorldUserMind.mobility || this.justTurned) {
	    this.justTurned = false;
	    
	    return new MoveAction();
	}
	else {
	    return performRandomAction();
	}
    }

    private VacuumWorldAction performRandomAction() {
	if (this.filledLeft && this.filledRight || !this.filledLeft && !this.filledRight) {
	    return new TurnAction(null);
	}
	else if (this.filledLeft) {
	    return new TurnAction(TurnDirection.RIGHT);
	}
	else {
	    return new TurnAction(TurnDirection.LEFT);
	}
    }

    private VacuumWorldAction doMessAction() {
	if (this.actioncounter % VacuumWorldUserMind.messiness == 0) {
	    this.actioncounter++;
	    
	    if (!this.currentpercept.isDirtOnAgentPosition()) {
		return new PlaceDirtAction(null);
	    }
	}
	
	return null; //TODO change this
    }

    private VacuumWorldAction doWallAction() {
	if (this.filledForward) {
	    if (this.filledLeft) {
		return new TurnAction(TurnDirection.RIGHT);
	    }
	    else if (this.filledRight) {
		return new TurnAction(TurnDirection.LEFT);
	    }
	    else {
		return new TurnAction(null);
	    }
	}
	
	return null; //TODO change this
    }

    @Override
    public VacuumWorldAction execute(VacuumWorldAction action) {
	this.lastaction = action.getClass();
	this.currentpercept = null;
	
	return action;
    }
}