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
		// System.out.println("PERCEPTION: " + currentpercept);
		// for (CommunicationPerception<VacuumWorldMessageContent> m : messages)
		// {
		// System.out.println(this.getId() + " received message: "
		// + m.getPerception().getPayload());
		// }
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
		if (super.isDirtOn(currentpercept)) {
			// the agent is on some dirt
			DirtAppearance dirt = super.getDirtOn(currentpercept); // get the
			// dirt
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
		if (super.isDirtFoward(currentpercept)) {
			// there is dirt in front of the agent!
			DirtAppearance dirt = super.getDirtFoward(currentpercept);
			if (super.canCleanDirt(dirt)) {
				System.out.println(this.getId()
						+ " see's cleanable dirt in front!");
				return new MoveAction();
			}
		}
		// there is no dirt forward, what about to the left or right?
		DirtAppearance leftdirt = null;
		DirtAppearance rightdirt = null;
		if (super.isDirtLeft(currentpercept)) {
			leftdirt = super.getDirtLeft(currentpercept);
		}
		if (super.isDirtRight(currentpercept)) {
			rightdirt = super.getDirtRight(currentpercept);
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
		if (super.isFilledForward(currentpercept)) {
			// the agent should turn to avoid moving into the wall
			filledLeft = super.isFilledLeft(currentpercept);
			filledRight = super.isFilledRight(currentpercept);
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

		// there is no smart action, do something random
		if (Math.random() > 0.3) {
			return new MoveAction();
		} else if (Math.random() > 0.4) {
			if (filledLeft && filledRight) {
				return new MoveAction();
			}
			if (filledLeft) {
				return new TurnAction(TurnDirection.RIGHT);
			}
			if (filledRight) {
				return new TurnAction(TurnDirection.RIGHT);
			}
			return new TurnAction(null);
		} else {
			return new VacuumWorldCommunicationAction("Hello, my id is: "
					+ this.getId());
		}
	}

	@Override
	public VacuumWorldAction execute(VacuumWorldAction action) {
		currentpercept = null;
		// returning the action will execute it in the next cycle
		// System.out.println("EXECUTING: " + action);
		return action;
	}
}
