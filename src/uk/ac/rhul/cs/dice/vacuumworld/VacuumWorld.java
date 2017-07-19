package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.PhysicalActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SpeechActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarMind;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.Avatar;
import uk.ac.rhul.cs.dice.starworlds.initialisation.AgentFactory;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.VacuumWorldController;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.VacuumWorldView;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldMind;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldSeeingSensor;
import uk.ac.rhul.cs.dice.vacuumworld.agent.minds.VacuumWorldExampleMind;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.VacuumWorldUserMind;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.avatar.VacuumWorldAvatar;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.avatar.VacuumWorldSelfishAvatarMind;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.avatar.VacuumWorldSelflessAvatarMind;

/**
 * The entry point of Vacuum World.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 */
public class VacuumWorld {

	public final static Double VERSION = 2.0;

	private final static Collection<Class<?>> SENSORS = new ArrayList<>();
	private final static Collection<Class<?>> ACTUATORS = new ArrayList<>();

	public final static Class<? extends VacuumWorldMind> DEFAULTMIND = VacuumWorldExampleMind.class;
	public final static Class<? extends VacuumWorldMind> USERMIND = VacuumWorldUserMind.class;

	public final static Class<? extends AbstractAvatarMind<VacuumWorldAction>> SELFISHAVATARMIND = VacuumWorldSelfishAvatarMind.class;
	public final static Class<? extends AbstractAvatarMind<VacuumWorldAction>> SELFLESSAVATARMIND = VacuumWorldSelflessAvatarMind.class;
	public final static Class<? extends AbstractAvatarMind<VacuumWorldAction>> AVATARMIND = SELFISHAVATARMIND;

	static {
		SENSORS.add(VacuumWorldSeeingSensor.class);
		SENSORS.add(ListeningSensor.class);
		ACTUATORS.add(SpeechActuator.class);
		ACTUATORS.add(PhysicalActuator.class);

	}

	/**
	 * Entry point of {@link VacuumWorld}.
	 * 
	 * @param args
	 *            : none
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		start();
	}

	/**
	 * Starts {@link VacuumWorld}.
	 */
	public static void start() {
		VacuumWorldUniverse universe = new VacuumWorldUniverse(
				new VacuumWorldAmbient(null, null, null, null),
				new VacuumWorldPhysics());
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		VacuumWorldView view = new VacuumWorldView();
		new VacuumWorldController(view, universe);
	}

	/**
	 * Creates a {@link VacuumWorldAvatar}.
	 * 
	 * @param mind
	 *            : of the {@link Avatar}
	 * @return a new {@link VacuumWorldAvatar}
	 */
	public static VacuumWorldAvatar createAvatar(
			Class<? extends AbstractAvatarMind<VacuumWorldAction>> mind) {
		return new VacuumWorldAvatar(AgentFactory.getInstance().constructEmpty(
				SENSORS, Sensor.class), AgentFactory.getInstance()
				.constructEmpty(ACTUATORS, Actuator.class), AgentFactory
				.getInstance().constructEmpty(mind));
	}

	/**
	 * Creates a {@link VacuumWorldAgent}
	 * 
	 * @param mind
	 *            : of the {@link Agent}
	 * @return a new {@link VacuumWorldAgent}
	 */
	public static VacuumWorldAgent createVacuumWorldAgent(Class<?> mind) {
		return new VacuumWorldAgent(AgentFactory.getInstance().constructEmpty(
				SENSORS, Sensor.class), AgentFactory.getInstance()
				.constructEmpty(ACTUATORS, Actuator.class),
				(AbstractAgentMind) AgentFactory.getInstance().constructEmpty(
						mind));
	}
}
