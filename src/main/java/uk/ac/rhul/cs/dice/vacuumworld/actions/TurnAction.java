package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.environment.subscription.AbstractSubscriptionHandler.SensiblePerception;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.RandomUtility;
import uk.ac.rhul.cs.dice.vacuumworld.misc.TurnDirection;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;

/**
 * The {@link Action} that a {@link VacuumWorldAgent} should attempt when it
 * wishes to turn. A {@link VacuumWorldAgent} may turn either
 * {@link TurnDirection#LEFT Left} or {@link TurnDirection#RIGHT Right}.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class TurnAction extends PhysicalAction implements VacuumWorldAction {
    private static final ActionEnum leftCode = ActionEnum.TURN_LEFT_ACTION;
    private static final ActionEnum rightCode = ActionEnum.TURN_RIGHT_ACTION;
    
    @SensiblePerception
    public static final Class<?> POSSIBLEPERCEPTION = VacuumWorldGridPerception.class;

    private static final long serialVersionUID = 4721670044302093268L;
    private TurnDirection direction;

    /**
     * Constructor. </br>
     * Turn in a random {@link TurnDirection direction}.
     */
    public TurnAction() {
	this.direction = RandomUtility.getRandomEnum(TurnDirection.class);
    }

    /**
     * Constructor.
     * 
     * @param direction
     *            : to turn, a random {@link TurnDirection} will be chosen if null
     */
    public TurnAction(TurnDirection direction) {
	this.direction = (direction != null) ? direction : RandomUtility.getRandomEnum(TurnDirection.class);
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

    public static ActionEnum getCode(Object... params) {
	if(params.length == 0) {
	    throw new IllegalArgumentException("Expecting a TurnDirection parameter!");
	}
	
	if(!(params[0] instanceof TurnDirection)) {
	    throw new IllegalArgumentException("Expecting a TurnDirection parameter!");
	}
	
	return getCodeHelper((TurnDirection) params[0]);
    }

    private static ActionEnum getCodeHelper(TurnDirection turnDirection) {
	switch(turnDirection) {
	case LEFT:
	    return TurnAction.leftCode;
	case RIGHT:
	    return TurnAction.rightCode;
	default:
	    throw new IllegalArgumentException();
	}
    }
}