package uk.ac.rhul.cs.dice.vacuumworld.misc;

import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;

public class EntityContainer {

	private VacuumWorldAgentAppearance agent;
	private DirtAppearance dirt;

	public EntityContainer(VacuumWorldAgentAppearance agent, DirtAppearance dirt) {
		this.setAgent(agent);
		this.setDirt(dirt);
	}

	public EntityContainer(VacuumWorldAgentAppearance agent) {
		this.setAgent(agent);
	}

	public EntityContainer(DirtAppearance dirt) {
		this.setDirt(dirt);
	}

	public boolean containsAgent() {
		return agent != null;
	}

	public boolean containsDirt() {
		return dirt != null;
	}

	public VacuumWorldAgentAppearance getAgent() {
		return agent;
	}

	public void setAgent(VacuumWorldAgentAppearance agent) {
		this.agent = agent;
	}

	public DirtAppearance getDirt() {
		return dirt;
	}

	public void setDirt(DirtAppearance dirt) {
		this.dirt = dirt;
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
		EntityContainer other = (EntityContainer) obj;
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
		return (dirt == null) ? agent.getId() : (agent == null) ? dirt
				.getColor().toString() : "B";
	}
}
