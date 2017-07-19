package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.RandomUtility;
import uk.ac.rhul.cs.dice.vacuumworld.misc.TurnDirection;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;

/**
 * The {@link Action} that a {@link VacuumWorldAgent} should attempt when it wishes to
 * turn. A {@link VacuumWorldAgent} may turn either {@link TurnDirection#LEFT
 * Left} or {@link TurnDirection#RIGHT Right}.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class TurnAction extends PhysicalAction implements VacuumWorldAction {

	@SensiblePerception
	public static final Class<?> POSSIBLEPERCEPTION = VacuumWorldGridPerception.class;

	private static final long serialVersionUID = 4721670044302093268L;
	private TurnDirection direction;

	/**
	 * Constructor. </br> Turn in a random {@link TurnDirection direction}.
	 */
	public TurnAction() {
		this.direction = RandomUtility.getRandomEnum(TurnDirection.class);
	}

	/**
	 * Constructor.
	 * 
	 * @param direction
	 *            : to turn, a random {@link TurnDirection} will be chosen if
	 *            null
	 */
	public TurnAction(TurnDirection direction) {
		this.direction = (direction != null) ? direction : RandomUtility
				.getRandomEnum(TurnDirection.class);
	}

	@Override
	public VacuumWorldAgentAppearance getActor() {
		return (VacuumWorldAgentAppearance) super.getActor();
	}

	public TurnDirection getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ":" + this.getDirection();
	}
}
