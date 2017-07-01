package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldAmbient;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;

/**
 * A {@link SensingAction} that will return the limited view of the a
 * {@link VacuumWorldAgent}. The views will be one of the following depending on
 * the {@link Orientation} of the {@link Agent}.</br><img
 * src="./doc-files/westview.png"> <img src="./doc-files/eastview.png"> <img
 * src="./doc-files/northview.png"> <img src="./doc-files/southview.png">
 * 
 * @author Ben
 *
 */
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
