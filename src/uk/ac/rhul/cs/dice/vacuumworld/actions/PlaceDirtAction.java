package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.RandomUtility;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldPerception;

public class PlaceDirtAction extends PhysicalAction implements
		VacuumWorldAction {
	@SensiblePerception
	public static final Class<?> POSSIBLEPERCEPTION = VacuumWorldPerception.class;

	private static final long serialVersionUID = 2069946942493951106L;

	private BodyColor dirtColor;

	public PlaceDirtAction(BodyColor dirtColor) {
		this.dirtColor = (dirtColor != null) ? dirtColor : RandomUtility
				.getRandom(BodyColor.getDirtColors());
	}

	@Override
	public VacuumWorldAgentAppearance getActor() {
		return (VacuumWorldAgentAppearance) super.getActor();
	}

	public BodyColor getDirtColor() {
		return dirtColor;
	}
}
