package uk.ac.rhul.cs.dice.vacuumworld.mvc;

import java.io.Serializable;
import java.util.Map;

import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class StartParameters implements Serializable {

    private static final long serialVersionUID = -6330032688028892878L;

    private Integer dimension;
    private Integer simulationRate;
    private Map<Position, VacuumWorldAgentAppearance> agentapps;
    private Map<Position, DirtAppearance> dirtapps;
    private Map<BodyColor, Class<?>> mindmap;

    public StartParameters() {
	// empty for reflection?
    }

    public StartParameters(Integer dimension, Integer simulationRate,
	    Map<Position, VacuumWorldAgentAppearance> agentapps, Map<Position, DirtAppearance> dirtapps,
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

    public Integer getDimension() {
	return dimension;
    }

    public void setDimension(Integer dimension) {
	this.dimension = dimension;
    }

    public Integer getSimulationRate() {
	return simulationRate;
    }

    public void setSimulationRate(Integer simulationRate) {
	this.simulationRate = simulationRate;
    }

    public Map<Position, VacuumWorldAgentAppearance> getAgentapps() {
	return agentapps;
    }

    public void setAgentapps(Map<Position, VacuumWorldAgentAppearance> agentapps) {
	this.agentapps = agentapps;
    }

    public Map<Position, DirtAppearance> getDirtapps() {
	return dirtapps;
    }

    public void setDirtapps(Map<Position, DirtAppearance> dirtapps) {
	this.dirtapps = dirtapps;
    }

    public Map<BodyColor, Class<?>> getMindmap() {
	return mindmap;
    }

    public void setMindmap(Map<BodyColor, Class<?>> mindmap) {
	this.mindmap = mindmap;
    }

    @Override
    public String toString() {
	StringBuilder b = new StringBuilder();
	b.append("   Agents: " + System.lineSeparator());
	agentapps.values().forEach(a -> b.append("   " + a + System.lineSeparator()));
	b.append("   Dirts: " + System.lineSeparator());
	dirtapps.values().forEach(a -> b.append("   " + a + System.lineSeparator()));
	b.append("   Minds: " + System.lineSeparator());
	mindmap.forEach((col, cla) -> b.append("   " + col + "->" + cla + System.lineSeparator()));
	return this.getClass().getSimpleName() + System.lineSeparator() + "   Dimension: " + this.dimension
		+ System.lineSeparator() + "   Rate: " + this.simulationRate + System.lineSeparator() + b.toString();
    }
}
