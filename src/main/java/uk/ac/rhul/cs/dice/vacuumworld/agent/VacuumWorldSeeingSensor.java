package uk.ac.rhul.cs.dice.vacuumworld.agent;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SeeingSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.subscription.AbstractSubscriptionHandler.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.ActivePerception;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;

public class VacuumWorldSeeingSensor extends SeeingSensor {

    @SensiblePerception
    public static final Class<?> VACUUMWORLDACTIVEPERCEPTION = ActivePerception.class;
    @SensiblePerception
    public static final Class<?> VACUUMWORLDPERCEPTION = VacuumWorldGridPerception.class;

}
