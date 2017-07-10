package uk.ac.rhul.cs.dice.vacuumworld.MVC;

import java.util.Map;

import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class StartParameters {

	Integer dimension;
	Integer simulationRate;
	Map<Position, VacuumWorldAgentAppearance> agentapps;
	Map<Position, DirtAppearance> dirtapps;
	Map<BodyColor, Class<?>> mindmap;

	public StartParameters(Integer dimension, Integer simulationRate,
			Map<Position, VacuumWorldAgentAppearance> agentapps,
			Map<Position, DirtAppearance> dirtapps,
			Map<BodyColor, Class<?>> mindmap) {
		this.dimension = dimension;
		this.simulationRate = simulationRate;
		this.agentapps = agentapps;
		this.dirtapps = dirtapps;
		this.mindmap = mindmap;
	}

}
