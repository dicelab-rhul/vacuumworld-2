package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.grid.tiles.Tile;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;

/**
 * The {@link Action} that a {@link VacuumWorldAgent} should attempt when it
 * wishes to move one {@link Tile} in the {@link Orientation} is it facing.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class MoveAction extends PhysicalAction implements VacuumWorldAction {
	@SensiblePerception
	public static final Class<?> POSSIBLEPERCEPTION = VacuumWorldGridPerception.class;

	private static final long serialVersionUID = -541182028589848248L;

	@Override
	public VacuumWorldAgentAppearance getActor() {
		return (VacuumWorldAgentAppearance) super.getActor();
	}

}
