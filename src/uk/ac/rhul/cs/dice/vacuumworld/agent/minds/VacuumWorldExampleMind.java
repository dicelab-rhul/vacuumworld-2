package uk.ac.rhul.cs.dice.vacuumworld.agent.minds;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.vacuumworld.actions.CleanAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.MoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.TurnAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldCommunicationAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldMind;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.TurnDirection;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridContent;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldMessageContent;

public class VacuumWorldExampleMind extends VacuumWorldMind {

	private VacuumWorldGridContent currentpercept;

	@Override
	public void perceive(
			VacuumWorldGridPerception perception,
			Collection<CommunicationPerception<VacuumWorldMessageContent>> messages) {
		if (perception != null) {
			currentpercept = perception.getPerception();
		}
		for (CommunicationPerception<VacuumWorldMessageContent> m : messages) {
			System.out.println(this.getId() + "received: "
					+ m.getPerception().getPayload());
		}
	}

	@Override
	public VacuumWorldAction decide() {
		if (currentpercept == null) {
			/*
			 * the last action failed or the agent is on the first cycle. The
			 * agent should get its first perception.\gd
			 */
			return new VacuumWorldSensingAction();
		}

		/*
		 * The methods, onDirt(Perception) and canCleanDirt(DirtAppearance) are
		 * very useful! They are used below.
		 */

		// check if on some dirt
		if (super.onDirt(currentpercept)) {
			// the agent is on some dirt
			DirtAppearance dirt = super.getDirt(currentpercept); // get the dirt
			System.out.println(this.getId() + " is on some dirt!");
			// check if the agent can clean the dirt
			if (super.canCleanDirt(dirt)) {
				// the agent is on some dirt and can clean it!
				System.out.println(this.getId() + " can clean the dirt!");
				// decide on a clean action!
				return new CleanAction();
			}
		}
		/*
		 * The agent is not on any dirt, check the agents surroundings for more.
		 * The methods, dirtForward(Perception), dirtLeft(Perception),
		 * dirtRight(Perception) will be useful here. You should try to
		 * implement methods to check if the dirt is at the other two positions
		 * in the perception. i.e. dirtForwardRight(Perception)
		 * dirtFowardRight(Perception)
		 */
		if (super.dirtForward(currentpercept)) {
			// there is dirt in front of the agent!
			DirtAppearance dirt = super.getFowardDirt(currentpercept);
			if (super.canCleanDirt(dirt)) {
				System.out.println(this.getId()
						+ " see's cleanable dirt in front!");
				return new MoveAction();
			}
		}
		// there is no dirt forward, what about to the left or right?
		DirtAppearance leftdirt = null;
		DirtAppearance rightdirt = null;
		if (super.dirtLeft(currentpercept)) {
			leftdirt = super.getLeftDirt(currentpercept);
		}
		if (super.dirtRight(currentpercept)) {
			rightdirt = super.getRightDirt(currentpercept);
		}
		// if there is dirt to the left and right
		if (leftdirt != null || rightdirt != null) {
			if (leftdirt != null && rightdirt != null) {
				if (super.canCleanDirt(leftdirt)
						&& super.canCleanDirt(rightdirt)) {
					// the agent could go either way, decide randomly
					return new TurnAction(null); // null means random!
				}
			}
			if (leftdirt != null) {
				if (super.canCleanDirt(leftdirt)) {
					System.out.println(this.getId()
							+ " see's cleanable dirt on its left!");
					return new TurnAction(TurnDirection.LEFT); // turn left
				}
			}
			if (rightdirt != null) {
				if (super.canCleanDirt(rightdirt)) {
					System.out.println(this.getId()
							+ " see's cleanable dirt on its right!");
					return new TurnAction(TurnDirection.RIGHT); // turn right
				}
			}
		}

		boolean filledLeft = false;
		boolean filledRight = false;
		// the is no cleanable dirt to the left or right
		// check if the forward location is filled with an agent or a wall
		if (super.filledForward(currentpercept)) {
			// the agent should turn to avoid moving into the wall
			filledLeft = super.filledLeft(currentpercept);
			filledRight = super.filledRight(currentpercept);
			if (filledLeft || filledRight) {
				if (filledLeft && filledRight) {
					// the agent is blocked in! turn to try and escape
					System.out.println(this.getId() + " is blocked in!");
					return new TurnAction(null);
				}
				if (filledLeft) {
					// left is blocked so turn right
					System.out.println(this.getId()
							+ " is blocked forward and left!");
					return new TurnAction(TurnDirection.RIGHT);
				}
				if (filledRight) {
					// right is blocked so turn left
					System.out.println(this.getId()
							+ " is blocked forward and right!");
					return new TurnAction(TurnDirection.LEFT);
				}
			} else {
				// the agent should turn left or right as it cannot move forward
				System.out.println(this.getId() + " is blocked in front!");
				return new TurnAction(null);
			}
		}

		return new VacuumWorldCommunicationAction("Hello from: " + this.getId());

		// there is no smart action, do something random
//		if (Math.random() > 0.2) {
//			return new MoveAction();
//		} else {
//			if (filledLeft && filledRight) {
//				return new MoveAction();
//			}
//			if (filledLeft) {
//				return new TurnAction(TurnDirection.RIGHT);
//			}
//			if (filledRight) {
//				return new TurnAction(TurnDirection.RIGHT);
//			}
//			return new TurnAction(null);
//		}
	}

	@Override
	public VacuumWorldAction execute(VacuumWorldAction action) {
		currentpercept = null;
		// returning the action will execute it in the next cycle
		return action;
	}
}
