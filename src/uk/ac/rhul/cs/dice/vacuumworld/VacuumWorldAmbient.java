package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.AppearanceFilter;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.Filter;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.grid.Grid;
import uk.ac.rhul.cs.dice.vacuumworld.grid.tiles.Tile;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldPerceptionContent;

public class VacuumWorldAmbient extends AbstractAmbient {

	public static final String GRIDKEY = "GRID";
	public static final String PERCEPTIONKEY = "PERCEPTION";
	// used when physical actions are performed
	private PerceptionFilter perceptionfilter;
	private Grid grid;

	public VacuumWorldAmbient(Set<AbstractAgent> agents,
			Set<ActiveBody> activeBodies, Set<PassiveBody> passiveBodies) {
		super(agents, activeBodies, passiveBodies);
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
	public void initialiseRandomGrid(int dimension) {
		if (this.grid == null) {
			if (dimension > this.agents.size()) {
				grid = new Grid(dimension);
				grid.fillRandom(this.agents.values(),
						this.passiveBodies.values());
				super.addEnvironmentVariable(GRIDKEY, this.grid);
				grid.printGrid();
			} else {
				throw new IllegalArgumentException(
						"The dimension of a Grid must be > than the number of agents: "
								+ agents.size());
			}
		} else {
			multiInitError();
		}
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
	public void initialiseGrid(int dimension,
			Collection<VacuumWorldAgent> agents, Collection<Dirt> dirts) {
		if (this.grid == null) {
			grid = new Grid(dimension);
			grid.fillGrid(this.agents.values(), this.passiveBodies.values());
			super.addEnvironmentVariable(GRIDKEY, this.grid);
		} else {
			multiInitError();
		}
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
			System.out.println(Arrays.toString(args));
			result.remove(null);
			return result;
		}
	}

	public class PerceptionFilter implements Filter {

		@Override
		public VacuumWorldPerceptionContent get(
				AbstractEnvironmentalAction action, Object... args) {

			// Map<ActiveBody, Pair<Integer, Integer>>
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
					result.put(p, grid.getTile(p));
				}
			}
			return new VacuumWorldPerceptionContent(result);
		}
	}

	public VacuumWorldPerceptionContent getAgentPerception(
			AbstractEnvironmentalAction action) {
		return perceptionfilter.get(action, this.grid);
	}
}
