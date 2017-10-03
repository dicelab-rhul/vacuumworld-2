package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.UIManager;

import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.PhysicalActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete.SpeechActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarMind;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.Avatar;
import uk.ac.rhul.cs.dice.starworlds.initialisation.AgentFactory;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldMind;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldSeeingSensor;
import uk.ac.rhul.cs.dice.vacuumworld.agent.minds.VacuumWorldExampleMind;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.VacuumWorldUserMind;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.avatar.VacuumWorldAvatar;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.avatar.VacuumWorldSelfishAvatarMind;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.avatar.VacuumWorldSelflessAvatarMind;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.VacuumWorldController;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.view.VacuumWorldView;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.LogUtils;

/**
 * The entry point of Vacuum World.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 */
public class VacuumWorld {
    public static final Double VERSION = 2.0;
    private static final Collection<Class<?>> SENSORS = new ArrayList<>();
    private static final Collection<Class<?>> ACTUATORS = new ArrayList<>();
    public static final Class<? extends VacuumWorldMind> DEFAULTMIND = VacuumWorldExampleMind.class;
    public static final Class<? extends VacuumWorldMind> USERMIND = VacuumWorldUserMind.class;
    public static final Class<? extends AbstractAvatarMind<VacuumWorldAction>> SELFISHAVATARMIND = VacuumWorldSelfishAvatarMind.class;
    public static final Class<? extends AbstractAvatarMind<VacuumWorldAction>> SELFLESSAVATARMIND = VacuumWorldSelflessAvatarMind.class;
    public static final Class<? extends AbstractAvatarMind<VacuumWorldAction>> AVATARMIND = SELFISHAVATARMIND;

    static {
	SENSORS.add(VacuumWorldSeeingSensor.class);
	SENSORS.add(ListeningSensor.class);
	ACTUATORS.add(SpeechActuator.class);
	ACTUATORS.add(PhysicalActuator.class);

    }

    private VacuumWorld() {
    }

    /**
     * Entry point of {@link VacuumWorld}.
     * 
     * @param args
     *            : none
     * @throws Exception
     */
    public static void main(String[] args) {
	start();
    }

    /**
     * Starts {@link VacuumWorld}.
     */
    public static void start() {
	VacuumWorldUniverse universe = new VacuumWorldUniverse();
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception e) {
	    LogUtils.log(e);
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
    public static VacuumWorldAvatar createAvatar(Class<? extends AbstractAvatarMind<VacuumWorldAction>> mind) {
	return new VacuumWorldAvatar(AgentFactory.getInstance().constructEmpty(SENSORS, Sensor.class),
		AgentFactory.getInstance().constructEmpty(ACTUATORS, Actuator.class),
		AgentFactory.getInstance().constructEmpty(mind));
    }

    /**
     * Creates a {@link VacuumWorldAgent}
     * 
     * @param mind
     *            : of the {@link Agent}
     * @return a new {@link VacuumWorldAgent}
     */
    public static VacuumWorldAgent createVacuumWorldAgent(Class<?> mind) {
	return new VacuumWorldAgent(AgentFactory.getInstance().constructEmpty(SENSORS, Sensor.class),
		AgentFactory.getInstance().constructEmpty(ACTUATORS, Actuator.class),
		(AbstractAgentMind) AgentFactory.getInstance().constructEmpty(mind));
    }
}