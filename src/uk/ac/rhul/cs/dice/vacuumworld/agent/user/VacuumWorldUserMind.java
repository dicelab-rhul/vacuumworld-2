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

	private static int MESSINESS = 4;
	private static double MOBILITY = 0.8;
	private boolean actionfailed = false;
	private int actioncounter = 0;
	private Class<?> lastaction = VacuumWorldSensingAction.class;
	private VacuumWorldGridContent currentpercept;
	private boolean justTurned = false;

	@Override
	public void perceive(
			VacuumWorldGridPerception gridperception,
			Collection<CommunicationPerception<VacuumWorldMessageContent>> messages) {
		if (gridperception != null) {
			
			actionfailed = false;
			currentpercept = gridperception.getPerception();
		} else {
			actionfailed = true;
		}
	}

	private boolean filledForward, filledLeft, filledRight;

	@Override
	public VacuumWorldAction decide() {
		System.out.println(lastaction);
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
		filledForward = super.isFilledForward(currentpercept);
		filledLeft = super.isFilledLeft(currentpercept);
		filledRight = super.isFilledRight(currentpercept);
		// System.out
		// .println(filledForward + " " + filledLeft + " " + filledRight);
		VacuumWorldAction action = null;
		if ((action = doMessAction()) != null) {
			return action;
		}
		if ((action = doWallAction()) != null) {
			return action;
		}
		return doRandomAction();
	}

	private VacuumWorldAction doRandomAction() {
		if (Math.random() <= MOBILITY || justTurned) {
			justTurned = false;
			return new MoveAction();
		} else {
			if (filledLeft && filledRight) {
				return new TurnAction(null);
			} else if (filledLeft) {
				return new TurnAction(TurnDirection.RIGHT);
			} else if (filledRight) {
				return new TurnAction(TurnDirection.LEFT);
			} else {
				return new TurnAction(null);
			}
		}
	}

	private VacuumWorldAction doMessAction() {
		if (actioncounter % MESSINESS == 0) {
			actioncounter++;
			if (!super.isDirtOn(currentpercept)) {
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
		System.out.println(action);
		lastaction = action.getClass();
		currentpercept = null;
		return action;
	}
}
