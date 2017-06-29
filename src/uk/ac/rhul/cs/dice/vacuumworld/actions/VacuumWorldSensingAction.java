package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldAmbient;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;

public class VacuumWorldSensingAction extends SensingAction implements
		VacuumWorldAction {

	@SensiblePerception
	public static final Class<?> DEFAULTPERCEPTION = DefaultPerception.class;
	
	private static final long serialVersionUID = 3191925631824605396L;

	public VacuumWorldSensingAction() {
		super(VacuumWorldAmbient.GRIDKEY + "."
				+ VacuumWorldAmbient.PERCEPTIONKEY);
	}

	@Override
	public VacuumWorldAgentAppearance getActor() {
		return (VacuumWorldAgentAppearance) super.getActor();
	}
}
