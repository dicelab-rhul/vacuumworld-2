package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.Mind;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.PhysicalActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SpeechActuator;
import uk.ac.rhul.cs.dice.starworlds.initialisation.AgentFactory;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.VacuumWorldController;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.VacuumWorldView;
import uk.ac.rhul.cs.dice.vacuumworld.actions.CleanAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.MoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.TurnAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldSeeingSensor;
import uk.ac.rhul.cs.dice.vacuumworld.agent.minds.VacuumWorldRandomMind;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;

public class VacuumWorld {

	private final static Collection<Class<? extends AbstractEnvironmentalAction>> POSSIBLEACTIONS = new ArrayList<>();
	private final static Collection<Class<?>> SENSORS = new ArrayList<>();
	private final static Collection<Class<?>> ACTUATORS = new ArrayList<>();
	private final static Class<? extends Mind> MIND = VacuumWorldRandomMind.class;

	static {
		SENSORS.add(VacuumWorldSeeingSensor.class);
		SENSORS.add(ListeningSensor.class);
		ACTUATORS.add(SpeechActuator.class);
		ACTUATORS.add(PhysicalActuator.class);
		POSSIBLEACTIONS.add(CleanAction.class);
		POSSIBLEACTIONS.add(TurnAction.class);
		POSSIBLEACTIONS.add(MoveAction.class);
		POSSIBLEACTIONS.add(CommunicationAction.class);
		POSSIBLEACTIONS.add(VacuumWorldSensingAction.class);
	}

	public static void main(String[] args) throws Exception {
		VacuumWorldUniverse universe = new VacuumWorldUniverse(
				new VacuumWorldAmbient(null, null, null),
				new VacuumWorldPhysics(), POSSIBLEACTIONS);
		VacuumWorldView view = new VacuumWorldView();
		new VacuumWorldController(view, universe);
	}

	public static Set<PassiveBody> createRandomDirts(int numdirts) {
		Set<PassiveBody> dirts = new HashSet<>();
		BodyColor[] colors = new BodyColor[] { BodyColor.GREEN,
				BodyColor.ORANGE };
		Random random = new Random();
		for (int i = 0; i < numdirts; i++) {
			dirts.add(new Dirt(colors[random.nextInt(2)]));
		}
		return dirts;
	}

	public static Set<AbstractAgent> createVacuumWorldAgents(int numagents) {
		Set<AbstractAgent> agents = new HashSet<>();
		for (int i = 0; i < numagents; i++) {
			agents.add(createVacuumWorldAgent());
		}
		return agents;
	}

	public static VacuumWorldAgent createVacuumWorldAgent() {
		return new VacuumWorldAgent(AgentFactory.getInstance().constructEmpty(
				SENSORS, Sensor.class), AgentFactory.getInstance()
				.constructEmpty(ACTUATORS, Actuator.class),
				(AbstractAgentMind) AgentFactory.getInstance().constructEmpty(
						MIND));
	}
}
