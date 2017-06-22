package uk.ac.rhul.cs.dice.vacuumworld.grid;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldPhysics;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt.DirtColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.EntityContainer;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.misc.RandomEnum;

public class Grid {

	private Integer dimension;
	private Map<Position, EntityContainer> grid = new HashMap<>();

	public Grid(Integer dimension) {
		this.setDimension(dimension);
	}

	/**
	 * Moves an agent its last {@link Position} to its not {@link Position}. No
	 * validation of the move is done here. This should be done in
	 * {@link VacuumWorldPhysics}.
	 * 
	 * @param agent
	 *            to move
	 * @param position
	 *            to move to
	 * @return the {@link Position} of the {@link Agent} before it was moved
	 */
	public Position moveAgent(VacuumWorldAgent agent, Position position) {
		Position old = agent.getAppearance().getPosition();
		grid.get(old).setAgent(null);
		grid.get(position).setAgent(agent.getAppearance());
		agent.getAppearance().setPosition(position);
		return old;
	}

	public Position getAgentPosition(VacuumWorldAgentAppearance appearance) {
		return appearance.getPosition();
	}

	public Position getDirtPosition(DirtAppearance dirt) {
		return dirt.getPosition();
	}

	/**
	 * Checks if the given {@link Dirt} is at the given {@link Position}.
	 * 
	 * @param dirt
	 *            to check
	 * @param position
	 *            to check
	 * @return true if the {@link Dirt} is at the {@link Position}, false
	 *         otherwise
	 */
	public boolean containsDirt(Dirt dirt, Position position) {
		return dirt.getAppearance().getPosition().equals(position);
	}

	/**
	 * Checks if the given {@link Agent} is at the given {@link Position}.
	 * 
	 * @param agent
	 *            to check
	 * @param position
	 *            to check
	 * @return true if the {@link Agent} is at the {@link Position}, false
	 *         otherwise
	 */
	public boolean containsAgent(VacuumWorldAgent agent, Position position) {
		return agent.getAppearance().getPosition().equals(position);
	}

	/**
	 * Places the given {@link Dirt} at the given {@link Position} if it does
	 * not already contain {@link Dirt}.
	 * 
	 * @param dirt
	 *            to place
	 * @param position
	 *            to place at
	 * @return true if the {@link Dirt} was successfully placed, false otherwise
	 */
	public boolean placeDirt(Dirt dirt, Position position) {
		EntityContainer container = grid.get(position);
		if (container != null) {
			if (!container.containsDirt()) {
				dirt.getAppearance().setPosition(position);
				container.setDirt(dirt.getAppearance());
				return true;
			}
			return false;
		} else {
			dirt.getAppearance().setPosition(position);
			grid.put(position, new EntityContainer(dirt.getAppearance()));
			return true;
		}
	}

	/**
	 * Places the given {@link Agent} at the given {@link Position} if it does
	 * not already contain an {@link Agent}. This method should only be called
	 * when adding a new {@link Agent}, to move existing {@link Agent}s the
	 * {@link Grid#moveAgent(AbstractAgent, Position)} method should be used.
	 * 
	 * @param agent
	 *            to place
	 * @param position
	 *            to place at
	 * @return true if the {@link Agent} was successfully placed, false
	 *         otherwise
	 */
	public boolean placeAgent(VacuumWorldAgent agent, Position position,
			Orientation orientation) {
		EntityContainer container = grid.get(position);
		if (container != null) {
			if (!container.containsAgent()) {
				agent.getAppearance().setPosition(position);
				agent.getAppearance().setOrientation(orientation);
				container.setAgent(agent.getAppearance());
				return true;
			}
			return false;
		} else {
			agent.getAppearance().setPosition(position);
			agent.getAppearance().setOrientation(orientation);
			grid.put(position, new EntityContainer(agent.getAppearance()));
			return true;
		}
	}

	/**
	 * Fills this {@link Grid} with the given {@link Agent}s and {@link Dirt} at
	 * random {@link Position}s. A fill method may only be called once.
	 * 
	 * @param agents
	 *            to place
	 * @param dirts
	 *            to place
	 */
	public void fillRandom(Collection<AbstractAgent> agents,
			Collection<Dirt> dirts) {
		Random random = new Random();
		agents.forEach((a) -> {
			placeAgent(
					(VacuumWorldAgent) a,
					new Position(random.nextInt(dimension), random
							.nextInt(dimension)), RandomEnum
							.getRandom(Orientation.class));
		});
		dirts.forEach((d) -> {
			placeDirt(
					d,
					new Position(random.nextInt(dimension), random
							.nextInt(dimension)));
		});

	}

	/**
	 * Fills this {@link Grid} with the given {@link Agent}s and {@link Dirt}s
	 * with their mapped {@link Position}s.
	 * 
	 * @param agents
	 *            to place
	 * @param dirts
	 *            to place
	 */
	public void fillGrid(
			Map<VacuumWorldAgent, Pair<Position, Orientation>> agents,
			Map<Dirt, Position> dirts) {
		agents.forEach((a, p) -> {
			placeAgent(a, p.getFirst(), p.getSecond());
		});
		dirts.forEach((d, p) -> {
			placeDirt(d, p);
		});
	}

	public Collection<Dirt> randomDirts(int dimension) {
		int numdirts = (int) (dimension / 5);
		Collection<Dirt> dirts = new HashSet<>();
		for (int i = 0; i < numdirts; i++) {
			dirts.add(new Dirt(RandomEnum.getRandom(DirtColor.class)));
		}
		return dirts;
	}

	public Integer getDimension() {
		return dimension;
	}

	public void setDimension(Integer dimension) {
		this.dimension = dimension;
	}

	public void printGrid() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				builder.append("[ ]");
			}
			builder.append(System.lineSeparator());
		}
		grid.keySet()
				.forEach(
						(pos) -> {
							int start = ((pos.getY() * this.dimension + pos
									.getX()) * 3) + pos.getY() * 2;
							int end = start + 3;
							builder.replace(start, end, "[" + grid.get(pos)
									+ "]");
						});
		System.out.println(builder.toString());
	}
}
