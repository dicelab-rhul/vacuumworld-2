package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldPerception;

public class MoveAction extends PhysicalAction {

	private static final long serialVersionUID = -541182028589848248L;
	
	@SensiblePerception
	public static final Class<?> POSSIBLEPERCEPTION = VacuumWorldPerception.class;

}
