package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.entities.Actor;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorld;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;

/**
 * The base interface for {@link Action}s in {@link VacuumWorld}.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 */
public interface VacuumWorldAction extends Action {

	/**
	 * Getter for the {@link Actor} who is attempting this {@link Action}. See
	 * {@link Action#getActor()}. All {@link VacuumWorldAction}s are attempted
	 * by an {@link Actor} with a {@link VacuumWorldAgentAppearance}.
	 */
	public VacuumWorldAgentAppearance getActor();
}
