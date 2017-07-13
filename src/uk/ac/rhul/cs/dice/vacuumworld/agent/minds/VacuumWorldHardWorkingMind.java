package uk.ac.rhul.cs.dice.vacuumworld.agent.minds;

import java.util.Collection;
import java.util.HashSet;
import java.util.Stack;

import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldMind;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridContent;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldMessageContent;

public class VacuumWorldHardWorkingMind extends VacuumWorldMind {

	private VacuumWorldGridContent percept = null;
	private Collection<Position> dirtPositionFromMessages = new HashSet<>();
	private Position movingTo = null;
	private Stack<VacuumWorldAction> plan;

	@Override
	public void perceive(
			VacuumWorldGridPerception perception,
			Collection<CommunicationPerception<VacuumWorldMessageContent>> messages) {
		if (perception != null) {
			this.percept = perception.getPerception();
		}
		for (CommunicationPerception<VacuumWorldMessageContent> m : messages) {
			// check the messages to see if ive been told about some dirt
			Position p = getPositionFromMessage(m.getPerception());
			if (p != null) {
				if (!messages.contains(p)) {
					dirtPositionFromMessages.add(p);
				} else {
					System.out.println("I already know theres dirt at " + p
							+ "!");
				}
			}
		}
	}

	@Override
	public VacuumWorldAction decide() {
		if (movingTo == null && !dirtPositionFromMessages.isEmpty()) {
			System.out.println("Where should I go..."
					+ this.dirtPositionFromMessages);
			// choose a random dirt to go to
			movingTo = dirtPositionFromMessages.stream().findAny().get();
			createMoveToDirtPlan(movingTo);
		}
		return null;
	}

	@Override
	public VacuumWorldAction execute(VacuumWorldAction action) {
		return null;
	}

	private void createMoveToDirtPlan(Position position) {
		// check my position
		Position myPosition = this.getAppearance().getPosition();
		Orientation myOrientation = this.getAppearance().getOrientation();
		System.out.println("Planing to move from: " + myPosition + " facing: "
				+ myOrientation + " to: " + position);
	}

	// unpacks a position it received in a message
	private Position getPositionFromMessage(VacuumWorldMessageContent content) {
		// your messages will probably be more concise, make it easy for
		// yourself!
		String message = content.getPayload();
		String[] split = message.split("\\(");
		if (split.length == 2) {
			String[] pos = split[1].replace(")", "").split(",");
			return new Position(Integer.valueOf(pos[0]),
					Integer.valueOf(pos[1]));
		}
		return null;
	}

}
