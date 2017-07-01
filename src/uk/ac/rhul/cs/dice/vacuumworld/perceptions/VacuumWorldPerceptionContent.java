package uk.ac.rhul.cs.dice.vacuumworld.perceptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.grid.tiles.Tile;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class VacuumWorldPerceptionContent {

	private Map<Position, Tile> view;
	private Position selfposition;

	public VacuumWorldPerceptionContent(Map<Position, Tile> view,
			Position selfposition) {
		this.selfposition = selfposition;
		this.view = view;
	}

	/**
	 * Gets the {@link Appearance} of the {@link Agent} who has received the
	 * {@link VacuumWorldPerception}. The receiving {@link Agent} is always in
	 * the centre of the 3x2 or 2x3 view.
	 * 
	 * @return self {@link Appearance}
	 */
	public VacuumWorldAgentAppearance getSelf() {
		return view.get(selfposition).getAgent();
	}

	/**
	 * Gets all of the free {@link Position}s in this {@link Agent}s current
	 * {@link Perception}. That is, all {@link Position}s that have {@link Dirt}
	 * or nothing in them.
	 * 
	 * @return the free {@link Position}s
	 */
	public Collection<Position> getFreePositions() {
		Collection<Position> free = new ArrayList<>();
		view.forEach((p, t) -> {
			if (!(t.containsAgent() || t.isWall())) {
				free.add(p);
			}
		});
		return free;
	}

	/**
	 * Gets all of the filled {@link Position}s in this {@link Agent}s current
	 * {@link Perception}. That is, all {@link Position}s that are filled with
	 * an {@link Agent} or are a Wall. (Note that this {@link Agent} fills a
	 * {@link Position}).
	 * 
	 * @return the filled {@link Position}s
	 */
	public Collection<Position> getFilledPositions() {
		Collection<Position> filled = new ArrayList<>();
		view.forEach((p, t) -> {
			if (t.containsAgent() || t.isWall()) {
				filled.add(p);
			}
		});
		return filled;
	}

	/**
	 * Gets all {@link Appearance}s of the {@link Agent}s that this
	 * {@link Agent} can currently see.
	 * 
	 * @return other {@link Agent} {@link Appearance}s
	 */
	public Collection<VacuumWorldAgentAppearance> getAgents() {
		Collection<VacuumWorldAgentAppearance> agents = new ArrayList<>();
		view.values().forEach((t) -> {
			if (t.containsAgent()) {
				agents.add(t.getAgent());
			}
		});
		return agents;
	}

	/**
	 * Gets the {@link Position}s of all the Walls that this {@link Agent} can
	 * currently see.
	 * 
	 * @return wall {@link Position}s
	 */
	public Collection<Position> getWalls() {
		Collection<Position> walls = new ArrayList<>();
		view.forEach((p, t) -> {
			if (t.isWall()) {
				walls.add(p);
			}
		});
		return walls;
	}

	/**
	 * Gets the {@link Position}s of all the {@link Dirt}s that this
	 * {@link Dirt} can currently see.
	 * 
	 * @return {@link Dirt} {@link Position}s
	 */
	public Collection<Position> getDirtPositions() {
		Collection<Position> dirts = new HashSet<>();
		view.forEach((p, t) -> {
			if (t.containsDirt()) {
				dirts.add(p);
			}
		});
		return dirts;
	}

	/**
	 * Gets the {@link Position}s of all the {@link Agent}s that this
	 * {@link Agent} can currently see.
	 * 
	 * @return {@link Agent} {@link Position}s
	 */
	public Collection<Position> getAgentPositions() {
		Collection<Position> agents = new HashSet<>();
		view.forEach((p, t) -> {
			if (t.containsAgent()) {
				agents.add(p);
			}
		});
		return agents;
	}

	/**
	 * Gets all {@link Appearance}s of the {@link Dirt}s that this {@link Agent}
	 * can currently see.
	 * 
	 * @return {@link Dirt} {@link Appearance}s
	 */
	public Collection<DirtAppearance> getDirts() {
		Collection<DirtAppearance> dirts = new ArrayList<>();
		view.values().forEach((t) -> {
			if (t.containsDirt()) {
				dirts.add(t.getDirt());
			}
		});
		return dirts;
	}

	@Override
	public String toString() {
		return view.toString();
	}
}
