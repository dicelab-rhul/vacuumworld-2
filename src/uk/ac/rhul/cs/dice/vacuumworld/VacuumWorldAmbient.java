package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.AppearanceFilter;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.Filter;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.grid.Grid;
import uk.ac.rhul.cs.dice.vacuumworld.grid.tiles.Tile;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridContent;

public class VacuumWorldAmbient extends AbstractAmbient {

	public static final String GRIDKEY = "GRID";
	public static final String PERCEPTIONKEY = "PERCEPTION";
	// used when physical actions are performed
	private PerceptionFilter perceptionfilter;
	private Grid grid;

	public VacuumWorldAmbient(Set<AbstractAutonomousAgent> agents,
			Set<ActiveBody> activeBodies, Set<PassiveBody> passiveBodies,
			Set<AbstractAvatarAgent<?>> avatars) {
		super(agents, activeBodies, passiveBodies, avatars);
		super.addFilter(PERCEPTIONKEY,
				(perceptionfilter = new PerceptionFilter()));
	}

	/**
	 * An initialisation method that called by the
	 * {@link PhysicalEnvironment#postInitialisation()} method (and only by that
	 * method) to set up the {@link Grid} of this {@link VacuumWorldAmbient}.
	 * 
	 * @param dimension
	 *            to set
	 * @throws IllegalStateException
	 *             if multiple attempts are made to initialise the grid (i.e. if
	 *             this method is called more than once)
	 */
	public void initialiseGrid(int dimension) {
		if (this.grid == null) {
			grid = new Grid();
			super.addEnvironmentVariable(GRIDKEY, this.grid);
		}
		if (grid.isClear()) {
			grid.setDimension(dimension);
			grid.fillGrid(this.agents.values(), this.passiveBodies.values(),
					this.avatars.values());
		} else {
			multiInitError();
		}
	}

	/**
	 * Clears the {@link Ambient} of everything.
	 */
	public void clear() {
		this.avatars.clear();
		this.agents.clear();
		this.activeBodies.clear();
		this.passiveBodies.clear();
		this.grid.clear();
	}

	private void multiInitError() {
		throw new IllegalStateException(
				"a grid initialisation method should never be called more than once");
	}

	public Integer getDimension() {
		return grid.getDimension();
	}

	public VacuumWorldAgentAppearance getAgent(Position position) {
		return this.grid.getAgent(position);
	}

	public DirtAppearance getDirt(Position position) {
		return this.grid.getDirt(position);
	}

	public void cleanDirt(Position position) {
		this.passiveBodies.remove(this.grid.getDirt(position).getId());
		this.grid.cleanDirt(position);
	}

	public void placeDirt(Position position, Dirt dirt) {
		this.addPassiveBody(dirt);
		this.grid.placeDirt(dirt, position);
	}

	public Position moveAgent(VacuumWorldAgentAppearance agent,
			Position position) {
		return grid.moveAgent(agent, position);
	}

	public boolean outOfBounds(Position position) {
		return grid.outOfBounds(position);
	}

	public boolean containsAgent(Position position) {
		return grid.containsAgent(position);
	}

	public boolean containsDirt(Dirt dirt, Position position) {
		return grid.containsDirt(dirt, position);
	}

	public boolean containsDirt(Position position) {
		return grid.containsDirt(position);
	}

	public Grid getGrid() {
		return grid;
	}

	public void print() {
		grid.printGrid();
	}

	public class LocalAgentFilter extends AppearanceFilter {
		@Override
		public Set<Appearance> get(AbstractEnvironmentalAction action,
				Object... args) {
			Set<Appearance> result = new HashSet<>();
			result.remove(null);
			return result;
		}
	}

	public class PerceptionFilter implements Filter {

		@Override
		public VacuumWorldGridContent get(AbstractEnvironmentalAction action,
				Object... args) {
			Grid grid = (Grid) args[0];
			Position position = ((VacuumWorldAgentAppearance) action.getActor())
					.getPosition();
			Orientation orientation = ((VacuumWorldAgentAppearance) action
					.getActor()).getOrientation();
			Integer x = position.getX();
			Integer y = position.getY();
			Map<Position, Tile> result = new HashMap<>();
			int[] ii;
			int[] jj;
			if (orientation.getI() != 0) {
				ii = new int[] { 0, orientation.getI() };
				jj = new int[] { -1, 0, 1 };
			} else {
				ii = new int[] { -1, 0, 1 };
				jj = new int[] { 0, orientation.getJ() };
			}
			for (int i : ii) {
				for (int j : jj) {
					Position p = new Position(x + i, y + j);
					result.put(p, grid.getReadOnlyTile(p));
				}
			}
			return new VacuumWorldGridContent(result, position);
		}
	}

	public VacuumWorldGridContent getAgentPerception(
			AbstractEnvironmentalAction action) {
		return perceptionfilter.get(action, this.grid);
	}
}
