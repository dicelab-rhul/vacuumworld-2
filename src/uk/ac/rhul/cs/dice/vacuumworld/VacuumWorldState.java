package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractState;
import uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents.PhysicalEnvironment;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.grid.Grid;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class VacuumWorldState extends AbstractState {

	private static final String GRIDKEY = "GRID";
	private static final String LOCALKEY = "LOCAL";
	private static final String LOCALAGENT = "LOCALAGENT";

	private Grid grid;

	public VacuumWorldState(Set<AbstractAgent> agents,
			Set<ActiveBody> activeBodies, Set<PassiveBody> passiveBodies) {
		super(agents, activeBodies, passiveBodies);
		super.addFilter(LOCALKEY, new LocalFilter());
		super.addFilter(LOCALAGENT, new LocalAgentFilter());
	}

	/**
	 * An initialisation method that called by the
	 * {@link PhysicalEnvironment#postInitialisation()} method (and only by that
	 * method) to set up the {@link Grid} of this {@link VacuumWorldState}.
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
						this.grid.randomDirts(dimension));
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

	private void multiInitError() {
		throw new IllegalStateException(
				"a grid initialisation method should never be called more than once");
	}

	public Grid getGrid() {
		return grid;
	}

	public class LocalAgentFilter extends AppearanceFilter {
		@Override
		public Set<Appearance> get(SensingAction action, Object... args) {
			Set<Appearance> result = new HashSet<>();
			System.out.println(Arrays.toString(args));
			result.remove(null);
			return result;
		}
	}

	public class LocalFilter implements Filter {

		@Override
		public Set<Position> get(SensingAction action, Object... args) {
			// Map<ActiveBody, Pair<Integer, Integer>>
			Grid map = (Grid) args[0];
			Position position = map.getAgentPosition((VacuumWorldAgentAppearance) action
					.getActor());
			Integer x = position.getX();
			Integer y = position.getY();
			Integer dimension = grid.getDimension();
			Set<Position> result = new HashSet<>();
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					result.add(new Position(
							(x + i >= 0 && x + i < dimension) ? x + i : x, (y
									+ j >= 0 && y + j < dimension) ? y + j : y));
				}
			}
			result.remove(new Pair<>(x, y));
			return result;
		}
	}

}
