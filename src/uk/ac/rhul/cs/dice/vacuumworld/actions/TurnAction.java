package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.RandomEnum;
import uk.ac.rhul.cs.dice.vacuumworld.misc.TurnDirection;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldPerception;

public class TurnAction extends PhysicalAction implements VacuumWorldAction {

	@SensiblePerception
	public static final Class<?> POSSIBLEPERCEPTION = VacuumWorldPerception.class;

	private static final long serialVersionUID = 4721670044302093268L;
	private TurnDirection direction;

	/**
	 * Constructor.
	 * 
	 * @param direction
	 *            : to turn, a random {@link TurnDirection} will be chosen if
	 *            null
	 */
	public TurnAction(TurnDirection direction) {
		this.direction = (direction != null) ? direction : RandomEnum
				.getRandom(TurnDirection.class);
	}

	@Override
	public VacuumWorldAgentAppearance getActor() {
		return (VacuumWorldAgentAppearance) super.getActor();
	}

	public TurnDirection getDirection() {
		return direction;
	}
}
