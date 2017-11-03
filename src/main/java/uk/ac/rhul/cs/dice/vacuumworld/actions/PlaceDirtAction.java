package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.Avatar;
import uk.ac.rhul.cs.dice.starworlds.environment.subscription.AbstractSubscriptionHandler.SensiblePerception;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.VacuumWorldUserMind;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.RandomUtility;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;

/**
 * The {@link Action} that a {@link VacuumWorldAgent} should attempt when it
 * wishes to leave some {@link Dirt}. The {@link Dirt} will be placed under the
 * {@link VacuumWorldAgent}. Note that only {@link VacuumWorldAgent}s that have
 * <code>User</code> status can perform this {@link Action}. That is, a
 * {@link VacuumWorldAgent} with a {@link VacuumWorldUserMind} or an
 * {@link Avatar}.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class PlaceDirtAction extends PhysicalAction implements VacuumWorldAction {
    private static final ActionEnum code = ActionEnum.DROP_DIRT_ACTION;
    
    @SensiblePerception
    public static final Class<?> POSSIBLEPERCEPTION = VacuumWorldGridPerception.class;

    private static final long serialVersionUID = 2069946942493951106L;

    private BodyColor dirtColor;

    /**
     * Constructor. Places a random coloured {@link Dirt}.
     */
    public PlaceDirtAction() {
	this.dirtColor = RandomUtility.getRandom(BodyColor.getDirtColors());
    }

    /**
     * Constructor. Place a {@link Dirt} of the given {@link BodyColor}.
     * 
     * @param dirtColor
     *            : Colour of the dirt
     */
    public PlaceDirtAction(BodyColor dirtColor) {
	this.dirtColor = (dirtColor == BodyColor.GREEN || dirtColor == BodyColor.ORANGE) ? dirtColor
		: RandomUtility.getRandom(BodyColor.getDirtColors());
    }

    @Override
    public VacuumWorldAgentAppearance getActor() {
	return (VacuumWorldAgentAppearance) super.getActor();
    }

    public BodyColor getDirtColor() {
	return this.dirtColor;
    }
    
    public ActionEnum getCode() {
	return PlaceDirtAction.code;
    }
}