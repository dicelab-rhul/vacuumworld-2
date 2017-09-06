package uk.ac.rhul.cs.dice.vacuumworld.MVC;

import java.io.Serializable;
import java.util.Map;

import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class StartParameters implements Serializable {

	private static final long serialVersionUID = -6330032688028892878L;

	public Integer dimension;
	public Integer simulationRate;
	public Map<Position, VacuumWorldAgentAppearance> agentapps;
	public Map<Position, DirtAppearance> dirtapps;
	public Map<BodyColor, Class<?>> mindmap;

	public StartParameters() {
	}

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
	
	public void clearAgentsAndDirts() {
		dirtapps.clear();
		agentapps.clear();
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("   Agents: " + System.lineSeparator());
		agentapps.values().forEach(
				(a) -> b.append("   " + a + System.lineSeparator()));
		b.append("   Dirts: " + System.lineSeparator());
		dirtapps.values().forEach(
				(a) -> b.append("   " + a + System.lineSeparator()));
		b.append("   Minds: " + System.lineSeparator());
		mindmap.forEach((col, cla) -> b.append("   " + col + "->" + cla
				+ System.lineSeparator()));
		return this.getClass().getSimpleName() + System.lineSeparator()
				+ "   Dimension: " + this.dimension + System.lineSeparator()
				+ "   Rate: " + this.simulationRate + System.lineSeparator()
				+ b.toString();
	}
}
