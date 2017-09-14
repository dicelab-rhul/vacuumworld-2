package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.environment.subscription.AbstractSubscriptionHandler.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.ActivePerception;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldAmbient;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridContent;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;

/**
 * A {@link SensingAction} that will return the limited view of the a
 * {@link VacuumWorldAgent}. The views will be one of the following depending on
 * the {@link Orientation} of the {@link Agent}.</br><img
 * src="./doc-files/westview.png"> <img src="./doc-files/eastview.png"> <img
 * src="./doc-files/northview.png"> <img src="./doc-files/southview.png"> </br>
 * All {@link VacuumWorldAction}s will provide a
 * {@link VacuumWorldGridPerception} that contains
 * {@link VacuumWorldGridContent}. The {@link VacuumWorldGridContent} is the 2x3
 * or 3x2 view.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class VacuumWorldSensingAction extends SensingAction implements
		VacuumWorldAction {
	
	private static final long serialVersionUID = 3191925631824605396L;

	public static final String KEY = VacuumWorldAmbient.GRIDKEY + "."
			+ VacuumWorldAmbient.PERCEPTIONQUERYKEY;

	@SensiblePerception
	public static final Class<?> DEFAULTPERCEPTION = ActivePerception.class;

	/**
	 * Constructor.
	 */
	public VacuumWorldSensingAction() {
		super(KEY);
	}

	@Override
	public VacuumWorldAgentAppearance getActor() {
		return (VacuumWorldAgentAppearance) super.getActor();
	}
}
