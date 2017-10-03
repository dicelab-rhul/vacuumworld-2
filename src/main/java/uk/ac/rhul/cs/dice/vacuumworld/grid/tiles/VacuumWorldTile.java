package uk.ac.rhul.cs.dice.vacuumworld.grid.tiles;

import java.io.Serializable;

import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.readonly.ReadOnlyInterface;

public class VacuumWorldTile implements Tile, ReadOnlyInterface<VacuumWorldTileReadOnly>, Serializable {

    private static final long serialVersionUID = -5224816605802757198L;

    private VacuumWorldAgentAppearance agent;
    private DirtAppearance dirt;

    public VacuumWorldTile() {
	//empty because of reflection?
    }

    public VacuumWorldTile(VacuumWorldAgentAppearance agent, DirtAppearance dirt) {
	this.dirt = dirt;
	this.agent = agent;
    }

    public VacuumWorldTile(VacuumWorldAgentAppearance agent) {
	this.agent = agent;
    }

    public VacuumWorldTile(DirtAppearance dirt) {
	this.dirt = dirt;
    }

    public boolean isEmpty() {
	return !(containsAgent() || containsDirt());
    }

    @Override
    public boolean containsAgent() {
	return agent != null;
    }

    @Override
    public boolean containsDirt() {
	return dirt != null;
    }

    @Override
    public VacuumWorldAgentAppearance getAgent() {
	return agent;
    }

    @Override
    public void setAgent(VacuumWorldAgentAppearance agent) {
	this.agent = agent;
    }

    @Override
    public DirtAppearance getDirt() {
	return dirt;
    }

    @Override
    public void setDirt(DirtAppearance dirt) {
	this.dirt = dirt;
    }

    @Override
    public boolean isWall() {
	return false;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((agent == null) ? 0 : agent.hashCode());
	result = prime * result + ((dirt == null) ? 0 : dirt.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	VacuumWorldTile other = (VacuumWorldTile) obj;
	if (agent == null) {
	    if (other.agent != null)
		return false;
	} else if (!agent.equals(other.agent))
	    return false;
	if (dirt == null) {
	    if (other.dirt != null)
		return false;
	} else if (!dirt.equals(other.dirt))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return buildDirt() + buildAgent();
    }

    private String buildDirt() {
	return ((this.dirt != null) ? this.dirt : "") + ((this.agent != null && this.dirt != null) ? " : " : "");
    }

    private String buildAgent() {
	return this.agent != null ? this.agent.toString() : "";
    }

    @Override
    public Class<VacuumWorldTileReadOnly> getReadOnlyClass() {
	return VacuumWorldTileReadOnly.class;
    }
}