package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;

/**
 * The {@link Action} that a {@link VacuumWorldAgent} should attempt when it
 * wishes to clean some {@link Dirt} that it is on top of.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class CleanAction extends PhysicalAction implements VacuumWorldAction {
	@SensiblePerception
	public static final Class<?> POSSIBLEPERCEPTION = VacuumWorldGridPerception.class;

	private static final long serialVersionUID = 2069946942493951106L;

	@Override
	public VacuumWorldAgentAppearance getActor() {
		return (VacuumWorldAgentAppearance) super.getActor();
	}
}
