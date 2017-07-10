package uk.ac.rhul.cs.dice.vacuumworld.actions;

import java.util.ArrayList;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldMessageContent;

public class VacuumWorldCommunicationAction extends CommunicationAction<String>
		implements VacuumWorldAction {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor. Sends the message to all other {@link Agent}s.
	 * 
	 * @param message
	 *            : the message
	 * 
	 */
	public VacuumWorldCommunicationAction(String message) {
		super(new VacuumWorldMessageContent(message), new ArrayList<>());
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            : the message
	 * @param recipientsIds
	 *            : a {@link List} of IDs of the recipients.
	 * 
	 */
	public VacuumWorldCommunicationAction(String message,
			List<String> recipientsIds) {
		super(new VacuumWorldMessageContent(message), recipientsIds);
	}

	@Override
	public VacuumWorldAgentAppearance getActor() {
		return (VacuumWorldAgentAppearance) super.getActor();
	}

}
