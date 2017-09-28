package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.Avatar;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.query.Query;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.grid.Grid;
import uk.ac.rhul.cs.dice.vacuumworld.grid.tiles.Tile;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridContent;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;

/**
 * The {@link Ambient} of {@link VacuumWorld}. This {@link Ambient} contains a
 * {@link Grid} which is the two dimensional {@link Grid} space that
 * {@link Agent}s, {@link Dirt} and {@link Avatar}s reside in physically.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class VacuumWorldAmbient extends AbstractAmbient {

    /** The Key for the instance of {@link Grid}. */
    public static final String GRIDKEY = "GRID";

    /** The {@link Query} that will provide {@link VacuumWorldGridContent}. */
    public static final String PERCEPTIONQUERYKEY = "PERCEPTION";

    /* AmbientAttributes. */
    private PerceptionQuery perceptionQuery;
    private Grid grid;

    /**
     * Constructor.
     * 
     * @param agents
     * @param activeBodies
     * @param passiveBodies
     * @param avatars
     */
    public VacuumWorldAmbient(Set<AbstractAutonomousAgent> agents, Set<ActiveBody> activeBodies,
	    Set<PassiveBody> passiveBodies, Set<AbstractAvatarAgent<?>> avatars) {
	super(agents, activeBodies, passiveBodies, avatars);
	this.grid = new Grid();
	super.addAmbientAttribute(GRIDKEY, this.grid);
	this.perceptionQuery = new PerceptionQuery();
	super.addQuery(PERCEPTIONQUERYKEY, this.perceptionQuery);
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

	if (this.grid.isClear()) {
	    this.grid.setDimension(dimension);
	    this.grid.fillGrid(this.agents.values(), this.passiveBodies.values(), this.avatars.values());
	}
	else {
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
	throw new IllegalStateException("a grid initialisation method should never be called more than once");
    }

    /**
     * Getter for the dimension of the {@link Grid}.
     * 
     * @return the dimension
     */
    public Integer getDimension() {
	return grid.getDimension();
    }

    /**
     * Getter for the {@link VacuumWorldAgentAppearance} at the given
     * {@link Position}.
     * 
     * @param position
     *            : of the {@link VacuumWorldAgentAppearance}
     * @return the {@link VacuumWorldAgentAppearance}
     */
    public VacuumWorldAgentAppearance getAgent(Position position) {
	return this.grid.getAgent(position);
    }

    /**
     * Getter for the {@link DirtAppearance} at the given {@link Position}.
     * 
     * @param position
     *            : of the {@link DirtAppearance}
     * @return the {@link DirtAppearance}
     */
    public DirtAppearance getDirt(Position position) {
	return this.grid.getDirt(position);
    }

    /**
     * Cleans some {@link Dirt} from this {@link Ambient}. This will remove the dirt
     * completely (i.e. remove the {@link PassiveBody}).
     * 
     * @param position
     *            : of the {@link Dirt}
     */
    public void cleanDirt(Position position) {
	this.passiveBodies.remove(this.grid.getDirt(position).getId());
	this.grid.cleanDirt(position);
    }

    /**
     * Places some {@link Dirt} in this {@link Ambient}. This will add a new
     * {@link PassiveBody} that is the {@link Dirt}.
     * 
     * @param position
     *            : of the {@link Dirt}
     * @param dirt
     *            : the {@link Dirt}
     */
    public void placeDirt(Position position, Dirt dirt) {
	this.addPassiveBody(dirt);
	this.grid.placeDirt(dirt, position);
    }

    /**
     * Getter for the {@link Grid}.
     * 
     * @return the {@link Grid}
     */
    public Grid getGrid() {
	return this.grid;
    }

    /**
     * Prints the {@link Grid} to stdout.
     */
    public void print() {
	this.grid.printGrid();
    }

    /**
     * A {@link Query} that takes in the {@link Grid} and provides the limited
     * {@link Agent} view of it. That is, the {@link VacuumWorldGridContent} of a
     * {@link VacuumWorldGridPerception}.
     * 
     * @author Ben Wilkins
     *
     */
    public class PerceptionQuery implements Query {

	@Override
	public VacuumWorldGridContent get(AbstractEnvironmentalAction action, Object... args) {
	    Grid g = (Grid) args[0];
	    Position position = ((VacuumWorldAgentAppearance) action.getActor()).getPosition();
	    Orientation orientation = ((VacuumWorldAgentAppearance) action.getActor()).getOrientation();
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
		    result.put(p, g.getReadOnlyTile(p));
		}
	    }
	    return new VacuumWorldGridContent(result, position);
	}
    }

    /**
     * Gets an {@link Agent}s {@link VacuumWorldGridContent} (its view of the grid)
     * using a {@link PerceptionQuery}.
     * 
     * @param action
     *            : that the {@link Agent} attempted.
     * @return the {@link VacuumWorldGridContent}
     */
    public VacuumWorldGridContent getAgentPerception(AbstractEnvironmentalAction action) {
	return this.perceptionQuery.get(action, this.grid);
    }
}
