package uk.ac.rhul.cs.dice.vacuumworld.agent.minds;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.Mind;
import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorld;
import uk.ac.rhul.cs.dice.vacuumworld.actions.CleanAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.MoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.TurnAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldMind;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.TurnDirection;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridContent;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldMessageContent;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.LogUtils;

/**
 * This class is an example of the {@link Mind} of an {@link Agent} in
 * {@link VacuumWorld}. It shows a simple way of programming the mind as a
 * sequence of conditional statements. This may not be the best way of doing it,
 * this {@link Mind} has no long term memory, does not plan, have goals or do
 * anything complicated. It is here so that you can get a general feel for the
 * system and begin to implement your better and more complex {@link Mind}.
 * 
 * @author Ben Wilkins
 *
 */
public class VacuumWorldExampleMind extends VacuumWorldMind {

    /*
     * When developing your agent mind, you should extend VacuumWorldMind, just like
     * this class does and implement the perceive, decide and execute methods. To
     * stick to software engineering practices you should use the Logger that is
     * provided statically by VacuumWorldMind - VacuumWorldMind.LOGGER (or LOGGER if
     * you are within a subclass of VacuumWorldMind as here)
     */

    // the perception of the agent at the current cycle
    private VacuumWorldGridContent currentpercept;

    @Override
    public void perceive(VacuumWorldGridPerception perception, Collection<CommunicationPerception<VacuumWorldMessageContent>> messages) {
	if (perception != null) {
	    this.currentpercept = perception.getPerception();
	}
    }

    // this is just a compact example.
    // TODO separate this into smaller methods to reduce the cyclomatic complexity
    // and the number of nested conditional statements.
    @Override
    public VacuumWorldAction decide() {
	if (this.currentpercept == null) {
	    /*
	     * the last action failed or the agent is on the first cycle. The agent should
	     * get its first perception.\gd
	     */
	    return new VacuumWorldSensingAction();
	}

	/*
	 * The methods, onDirt(Perception) and canCleanDirt(DirtAppearance) are very
	 * useful! They are used below.
	 */

	// check if on some dirt
	if (this.currentpercept.isDirtOnAgentPosition()) {
	    // the agent is on some dirt
	    DirtAppearance dirt = this.currentpercept.getDirtOnCurrentPositionIfAny(); // get the
	    // dirt
	    LogUtils.log(this.getId() + " is on some dirt!");
	    // check if the agent can clean the dirt
	    if (canCleanDirt(dirt)) {
		// the agent is on some dirt and can clean it!
		LogUtils.log(this.getId() + " can clean the dirt!");
		// decide on a clean action!
		return new CleanAction();
	    }
	}
	/*
	 * The agent is not on any dirt, check the agents surroundings for more. The
	 * methods, dirtForward(Perception), dirtLeft(Perception), dirtRight(Perception)
	 * will be useful here. You should try to implement methods to check if the dirt
	 * is at the other two positions in the perception. i.e.
	 * dirtForwardRight(Perception) dirtFowardRight(Perception)
	 */
	if (this.currentpercept.isDirtForward()) {
	    // there is dirt in front of the agent!
	    DirtAppearance dirt = this.currentpercept.getDirtForwardIfAny();
	    if (super.canCleanDirt(dirt)) {
		LogUtils.log(this.getId() + " see's cleanable dirt in front!");
		return new MoveAction();
	    }
	}
	// there is no dirt forward, what about to the left or right?
	DirtAppearance leftdirt = null;
	DirtAppearance rightdirt = null;
	if (this.currentpercept.isDirtOnTheLeft()) {
	    leftdirt = this.currentpercept.getDirtOnTheLeftIfAny();
	    if (!canCleanDirt(leftdirt)) {
		leftdirt = null;
	    }
	}
	if (this.currentpercept.isDirtOnTheRight()) {
	    rightdirt = this.currentpercept.getDirtOnTheRightIfAny();
	    if (!canCleanDirt(rightdirt)) {
		rightdirt = null;
	    }
	}

	// there is dirt to the right and left
	if (leftdirt != null && rightdirt != null) {
	    // the agent could go either way, decide randomly
	    return new TurnAction(null); // null means random!
	}
	// there is dirt to the left
	if (leftdirt != null) {
	    LogUtils.log(this.getId() + " see's cleanable dirt on its left!");
	    return new TurnAction(TurnDirection.LEFT); // turn left
	}
	// there is dirt to the right
	if (rightdirt != null) {
	    LogUtils.log(this.getId() + " see's cleanable dirt on its right!");
	    return new TurnAction(TurnDirection.RIGHT); // turn right
	}

	boolean filledLeft;
	boolean filledRight;
	// there is no cleanable dirt to the left or right
	// check if the forward location is filled with an agent or a wall
	if (!this.currentpercept.isForwardAccessible()) {
	    // the agent should turn to avoid moving into the wall
	    filledLeft = !this.currentpercept.isLeftAccessible();
	    filledRight = !this.currentpercept.isRightAccessible();

	    if (filledLeft && filledRight) {
		// the agent is blocked in! turn to try and escape
		LogUtils.log(this.getId() + " is blocked in!");
		return new TurnAction(null);
	    }
	    if (filledLeft) {
		// left is blocked so turn right
		LogUtils.log(this.getId() + " is blocked forward and left!");
		return new TurnAction(TurnDirection.RIGHT);
	    }
	    if (filledRight) {
		// right is blocked so turn left
		LogUtils.log(this.getId() + " is blocked forward and right!");
		return new TurnAction(TurnDirection.LEFT);
	    }
	    // the agent should turn left or right as it cannot move forward
	    LogUtils.log(this.getId() + " is blocked in front!");
	    return new TurnAction(null);
	}

	// this agent is not very smart, so it just decides randomly!
	return decideRandomAction();
    }

    @Override
    public VacuumWorldAction execute(VacuumWorldAction action) {
	this.currentpercept = null;
	// returning the action will execute it in the next cycle
	return action;
    }
}
