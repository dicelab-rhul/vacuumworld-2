package uk.ac.rhul.cs.dice.vacuumworld.agent;

import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SeeingSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldPerception;

public class VacuumWorldSeeingSensor extends SeeingSensor {

	@SensiblePerception
	public static final Class<?> VACUUMWORLDPERCEPTION = VacuumWorldPerception.class;
	@SensiblePerception
	public static final Class<?> DEFAULTPERCEPTION = DefaultPerception.class;

}
