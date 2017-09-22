package uk.ac.rhul.cs.dice.vacuumworld.actions;

import java.util.ArrayList;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.environment.subscription.AbstractSubscriptionHandler.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldMessageContent;

/**
 * The {@link Action} that a {@link VacuumWorldAgent} should attempt when it
 * wishes to communicate with other {@link VacuumWorldAgent}s.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class VacuumWorldCommunicationAction extends CommunicationAction<String>
		implements VacuumWorldAction {

	private static final long serialVersionUID = 641986939290090117L;
	@SensiblePerception
	public static final Class<?> COMMUNICATIONPERCEPTION = CommunicationPerception.class;
	/*
	 * This must be set so that when an communication action is attempted the
	 * agent still gets a perception of the grid.
	 */
	@SensiblePerception
	public static final Class<?> GRIDPERCEPTION = VacuumWorldGridPerception.class;

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
