package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;


public class MoveAction extends PhysicalAction implements VacuumWorldAction {
	@SensiblePerception
	public static final Class<?> POSSIBLEPERCEPTION = VacuumWorldGridPerception.class;

	private static final long serialVersionUID = -541182028589848248L;
	@Override
	public VacuumWorldAgentAppearance getActor() {
		return (VacuumWorldAgentAppearance) super.getActor();
	}

}
