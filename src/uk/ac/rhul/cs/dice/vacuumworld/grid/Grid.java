package uk.ac.rhul.cs.dice.vacuumworld.grid;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarAgent;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldPhysics;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.grid.tiles.Tile;
import uk.ac.rhul.cs.dice.vacuumworld.grid.tiles.VacuumWorldTile;
import uk.ac.rhul.cs.dice.vacuumworld.grid.tiles.WallTile;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.misc.RandomUtility;
import uk.ac.rhul.cs.dice.vacuumworld.readonly.ReadOnlyWrap;

public class Grid implements Serializable {

	private Integer dimension;
	private Map<Position, VacuumWorldTile> grid = new HashMap<>();

	public Grid(Integer dimension) {
		this.setDimension(dimension);
	}

	public void clear() {
		grid.clear();
		dimension = null;
	}

	public boolean isClear() {
		return grid.isEmpty();
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
	public Position moveAgent(VacuumWorldAgentAppearance agent,
			Position position) {
		Position old = agent.getPosition();
		grid.get(old).setAgent(null);
		grid.get(position).setAgent(agent);
		agent.setPosition(position);
		return old;
	}

	public Collection<VacuumWorldTile> getTiles() {
		return this.grid.values();
	}

	public Collection<VacuumWorldTile> getNonEmptyTiles() {
		ArrayList<VacuumWorldTile> tiles = new ArrayList<>();
		this.grid.values().forEach((t) -> {
			if (!t.isEmpty()) {
				tiles.add(t);
			}
		});
		return tiles;
	}

	public Tile getTile(Position position) {
		Tile t = this.grid.get(position);
		return (t != null) ? t : new WallTile();
	}

	public Tile getReadOnlyTile(Position position) {
		Tile t = this.grid.get(position);
		if (t != null) {
			try {
				return ReadOnlyWrap.readOnlyCopy((VacuumWorldTile) t);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return new WallTile();
		}
	}

	public void cleanDirt(Position position) {
		this.grid.get(position).setDirt(null);
	}

	public VacuumWorldAgentAppearance getAgent(Position position) {
		return grid.get(position).getAgent();
	}

	public DirtAppearance getDirt(Position position) {
		return grid.get(position).getDirt();
	}

	public Position getAgentPosition(VacuumWorldAgentAppearance appearance) {
		return appearance.getPosition();
	}

	public Position getDirtPosition(DirtAppearance dirt) {
		return dirt.getPosition();
	}

	public boolean outOfBounds(Position position) {
		return position.getX() < 0 || position.getY() < 0
				|| position.getX() >= dimension || position.getY() >= dimension;
	}

	/**
	 * Checks if there is an {@link Agent} at the given {@link Position}.
	 * 
	 * @param position
	 *            to check
	 * @return true if there is {@link Agent} at the {@link Position}, false
	 *         otherwise
	 */
	public boolean containsAgent(Position position) {
		return grid.get(position).containsAgent();
	}

	/**
	 * Checks if there is {@link Dirt} at the given {@link Position}.
	 * 
	 * @param position
	 *            to check
	 * @return true if there is {@link Dirt} at the {@link Position}, false
	 *         otherwise
	 */
	public boolean containsDirt(Position position) {
		return grid.get(position).containsDirt();
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
	 * Checks if the given {@link Dirt} is at the given {@link Position}.
	 * 
	 * @param dirt
	 *            to check
	 * @param position
	 *            to check
	 * @return true if the {@link Dirt} is at the {@link Position}, false
	 *         otherwise
	 */
	public boolean containsDirt(DirtAppearance dirt, Position position) {
		return dirt.getPosition().equals(position);
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
	public boolean containsAgent(VacuumWorldAgentAppearance agent,
			Position position) {
		return agent.getPosition().equals(position);
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
		VacuumWorldTile container = grid.get(position);
		if (!container.containsDirt()) {
			dirt.getAppearance().setPosition(position);
			container.setDirt(dirt.getAppearance());
			return true;
		}
		return false;
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
		VacuumWorldTile container = grid.get(position);
		if (!container.containsAgent()) {
			agent.getAppearance().setPosition(position);
			agent.getAppearance().setOrientation(orientation);
			container.setAgent(agent.getAppearance());
			return true;
		}
		return false;
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
	public void fillRandom(Collection<AbstractAutonomousAgent> agents,
			Collection<PassiveBody> dirts) {
		Random random = new Random();
		agents.forEach((a) -> {
			placeAgent(
					(VacuumWorldAgent) a,
					new Position(random.nextInt(dimension), random
							.nextInt(dimension)), RandomUtility
							.getRandomEnum(Orientation.class));
			((VacuumWorldAgent) a).getAppearance().setColor(
					RandomUtility.getRandomEnum(BodyColor.class));
		});
		dirts.forEach((d) -> {
			placeDirt(
					(Dirt) d,
					new Position(random.nextInt(dimension), random
							.nextInt(dimension)));
		});
	}

	/**
	 * Fills this {@link Grid} with the given {@link Agent}s and {@link Dirt}s.
	 * The {@link Position}, {@link Orientation} and {@link Color} should have
	 * be defined in their {@link VacuumWorldAgentAppearance}.
	 * 
	 * @param agents
	 *            to place
	 * @param dirts
	 *            to place
	 */
	public void fillGrid(Collection<AbstractAutonomousAgent> agents,
			Collection<PassiveBody> dirts,
			Collection<AbstractAvatarAgent<?>> avatars) {
		agents.forEach((a) -> {
			VacuumWorldAgentAppearance ap = (VacuumWorldAgentAppearance) a
					.getAppearance();
			grid.get(ap.getPosition()).setAgent(ap);
		});
		avatars.forEach((a) -> {
			VacuumWorldAgentAppearance ap = (VacuumWorldAgentAppearance) a
					.getAppearance();
			grid.get(ap.getPosition()).setAgent(ap);
		});
		dirts.forEach((d) -> {
			DirtAppearance ap = (DirtAppearance) d.getAppearance();
			grid.get(ap.getPosition()).setDirt(ap);
		});
	}

	public Integer getDimension() {
		return dimension;
	}

	public void setDimension(Integer dimension) {
		this.dimension = dimension;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				grid.put(new Position(i, j), new VacuumWorldTile());
			}
		}
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
							builder.replace(start, end, "["
									+ grid.get(pos).toString().substring(0, 1)
									+ "]");
						});
		System.out.println(builder.toString());
	}
}
