package uk.ac.rhul.cs.dice.vacuumworld.perceptions;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.grid.tiles.Tile;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class VacuumWorldGridContent {
    private Map<Position, Tile> view;
    private Position selfposition;
    private Orientation selfOrientation;

    public VacuumWorldGridContent(Map<Position, Tile> view, Position selfposition) {
	this.selfposition = selfposition;
	this.view = view;
	this.selfOrientation = getSelf().getOrientation();
    }

    public boolean isAgentForward() {
	return isAgentOnPosition(this.selfposition.getForward(this.selfOrientation));
    }
    
    public boolean isAgentOnTheLeft() {
	return isAgentOnPosition(this.selfposition.getLeft(this.selfOrientation));
    }
    
    public boolean isAgentOnTheRight() {
	return isAgentOnPosition(this.selfposition.getRight(this.selfOrientation));
    }
    
    public boolean isAgentForwardLeft() {
	return isAgentOnPosition(this.selfposition.getForwardLeft(this.selfOrientation));
    }
    
    public boolean isAgentForwardRight() {
	return isAgentOnPosition(this.selfposition.getForwardRight(this.selfOrientation));
    }
    
    private boolean isAgentOnPosition(Position position) {
	Tile t = this.view.get(position);
	
	return t == null ? false : t.containsAgent();
    }
    
    public boolean isDirtOnAgentPosition() {
	return isDirtOnPosition(this.selfposition);
    }
    
    public boolean isDirtForward() {
	return isDirtOnPosition(this.selfposition.getForward(this.selfOrientation));
    }
    
    public boolean isDirtOnTheLeft() {
	return isDirtOnPosition(this.selfposition.getLeft(this.selfOrientation));
    }
    
    public boolean isDirtOnTheRight() {
	return isDirtOnPosition(this.selfposition.getRight(this.selfOrientation));
    }
    
    public boolean isDirtForwardLeft() {
	return isDirtOnPosition(this.selfposition.getForwardLeft(this.selfOrientation));
    }
    
    public boolean isDirtForwardRight() {
	return isDirtOnPosition(this.selfposition.getForwardRight(this.selfOrientation));
    }
    
    private boolean isDirtOnPosition(Position position) {
	Tile t = this.view.get(position);
	
	return t == null ? false : t.containsDirt();
    }
    
    public boolean isWallForward() {
	return isWallOnPosition(this.selfposition.getForward(this.selfOrientation));
    }
    
    public boolean isWallOnTheLeft() {
	return isWallOnPosition(this.selfposition.getLeft(this.selfOrientation));
    }
    
    public boolean isWallOnTheRight() {
	return isWallOnPosition(this.selfposition.getRight(this.selfOrientation));
    }
    
    public boolean isWallForwardLeft() {
	return isWallOnPosition(this.selfposition.getForwardLeft(this.selfOrientation));
    }
    
    public boolean isWallForwardRight() {
	return isWallOnPosition(this.selfposition.getForwardRight(this.selfOrientation));
    }
    
    private boolean isWallOnPosition(Position position) {
	Tile t = this.view.get(position);
	
	return t == null || t.isWall();
    }
    
    private boolean isWallNotOnPosition(Position position) {
	Tile t = this.view.get(position);
	
	return t != null && !t.isWall();
    }
    
    public boolean isForwardAccessible() {
	return isPositionAccessible(this.selfposition.getForward(this.selfOrientation));
    }
    
    public boolean isLeftAccessible() {
	return isPositionAccessible(this.selfposition.getLeft(this.selfOrientation));
    }
    
    public boolean isRightAccessible() {
	return isPositionAccessible(this.selfposition.getRight(this.selfOrientation));
    }
    
    public boolean isForwardLeftAccessible() {
	return isPositionAccessible(this.selfposition.getForwardLeft(this.selfOrientation));
    }
    
    public boolean isForwardRightAccessible() {
	return isPositionAccessible(this.selfposition.getForwardRight(this.selfOrientation));
    }
    
    private boolean isPositionAccessible(Position position) {
	Tile t = this.view.get(position);
	
	return !(t == null || t.isWall() || t.containsAgent());
    }
    
    private boolean isPositionInaccessible(Position position) {
	Tile t = this.view.get(position);
	
	return t == null || t.isWall() || t.containsAgent();
    }
    
    /**
     * Gets the {@link Agent} at the given {@link Position} is it exists.
     * 
     * @param position
     *            : of the {@link Agent}
     * @return the {@link VacuumWorldAgentAppearance} of the {@link Agent} at the
     *         given {@link Position}, null if there is no {@link Agent} at the
     *         given {@link Position}.
     */
    private VacuumWorldAgentAppearance getAgent(Position position) {
	return isAgentOnPosition(position) ? this.view.get(position).getAgent() : null;
    }
    
    public VacuumWorldAgentAppearance getAgentForwardIfAny() {
	return getAgent(this.selfposition.getForward(this.selfOrientation));
    }
    
    public VacuumWorldAgentAppearance getAgentOnTheLeftIfAny() {
	return getAgent(this.selfposition.getLeft(this.selfOrientation));
    }
    
    public VacuumWorldAgentAppearance getAgentOnTheRightIfAny() {
	return getAgent(this.selfposition.getRight(this.selfOrientation));
    }
    
    public VacuumWorldAgentAppearance getAgentOnForwardLeftIfAny() {
	return getAgent(this.selfposition.getForwardLeft(this.selfOrientation));
    }
    
    public VacuumWorldAgentAppearance getAgentOnForwardRightIfAny() {
	return getAgent(this.selfposition.getForwardRight(this.selfOrientation));
    }

    /**
     * Gets the {@link Dirt} at the given {@link Position} is it exists.
     * 
     * @param position
     *            : of the {@link Dirt}
     * @return the {@link DirtAppearance} of the {@link Dirt} at the given
     *         {@link Position}, null if there is no {@link Dirt} at the given
     *         {@link Position}.
     */
    private DirtAppearance getDirt(Position position) {
	return isDirtOnPosition(position) ? this.view.get(position).getDirt() : null;
    }
    
    public DirtAppearance getDirtOnCurrentPositionIfAny() {
	return getDirt(this.selfposition);
    }
    
    public DirtAppearance getDirtForwardIfAny() {
	return getDirt(this.selfposition.getForward(this.selfOrientation));
    }
    
    public DirtAppearance getDirtOnTheLeftIfAny() {
	return getDirt(this.selfposition.getLeft(this.selfOrientation));
    }
    
    public DirtAppearance getDirtOnTheRightIfAny() {
	return getDirt(this.selfposition.getRight(this.selfOrientation));
    }
    
    public DirtAppearance getDirtOnForwardLeftIfAny() {
	return getDirt(this.selfposition.getForwardLeft(this.selfOrientation));
    }
    
    public DirtAppearance getDirtOnForwardRightIfAny() {
	return getDirt(this.selfposition.getForwardRight(this.selfOrientation));
    }

    /**
     * Gets the {@link Appearance} of the {@link Agent} who has received the
     * {@link VacuumWorldGridPerception}. The receiving {@link Agent} is always in
     * the centre of the 3x2 or 2x3 view.
     * 
     * @return self {@link Appearance}
     */
    public VacuumWorldAgentAppearance getSelf() {
	return getAgent(this.selfposition);
    }

    /**
     * Gets all of the {@link Position}s that the {@link Agent} can currently view.
     * This does not include the {@link Position}s of any walls (as they are
     * technically out of bounds).
     * 
     * @return all {@link Position}s
     */
    public Collection<Position> getAllPositions() {
	return this.view.entrySet().stream().map(Entry::getKey).filter(this::isWallNotOnPosition).collect(Collectors.toSet());
    }

    /**
     * Gets all of the free {@link Position}s in this {@link Agent}s current
     * {@link Perception}. That is, all {@link Position}s that have {@link Dirt} or
     * nothing in them.
     * 
     * @return the free {@link Position}s
     */
    public Collection<Position> getFreePositions() {
	return this.view.entrySet().stream().map(Entry::getKey).filter(this::isPositionAccessible).collect(Collectors.toSet());
    }

    /**
     * Gets all of the filled {@link Position}s in this {@link Agent}s current
     * {@link Perception}. That is, all {@link Position}s that are filled with an
     * {@link Agent} or are a Wall. (Note that this {@link Agent} fills a
     * {@link Position}).
     * 
     * @return the filled {@link Position}s
     */
    public Collection<Position> getFilledPositions() {
	return this.view.entrySet().stream().map(Entry::getKey).filter(this::isPositionInaccessible).collect(Collectors.toSet());
    }

    /**
     * Gets the {@link Position}s of all the Walls that this {@link Agent} can
     * currently see.
     * 
     * @return wall {@link Position}s
     */
    public Collection<Position> getWallPositions() {
	return this.view.entrySet().stream().map(Entry::getKey).filter(this::isWallOnPosition).collect(Collectors.toSet());
    }

    /**
     * Gets the {@link Position}s of all the {@link Dirt}s that this {@link Dirt}
     * can currently see.
     * 
     * @return {@link Dirt} {@link Position}s
     */
    public Collection<Position> getDirtPositions() {
	return this.view.entrySet().stream().map(Entry::getKey).filter(this::isWallOnPosition).collect(Collectors.toSet());
    }

    /**
     * Gets the {@link Position}s of all the {@link Agent}s that this {@link Agent}
     * can currently see.
     * 
     * @return {@link Agent} {@link Position}s
     */
    public Collection<Position> getAgentPositions() {
	return this.view.entrySet().stream().map(Entry::getKey).filter(this::isAgentOnPosition).collect(Collectors.toSet());
    }

    /**
     * Gets all {@link Appearance}s of the {@link Dirt}s that this {@link Agent} can
     * currently see.
     * 
     * @return {@link Dirt} {@link Appearance}s
     */
    public Collection<DirtAppearance> getDirts() {
	return this.view.entrySet().stream().filter(e -> isDirtOnPosition(e.getKey())).map(e -> e.getValue().getDirt()).collect(Collectors.toSet());
    }

    /**
     * Gets all {@link Appearance}s of the {@link Agent}s that this {@link Agent}
     * can currently see.
     * 
     * @return other {@link Agent} {@link Appearance}s
     */
    public Collection<VacuumWorldAgentAppearance> getAgents() {
	return this.view.entrySet().stream().filter(e -> isAgentOnPosition(e.getKey())).map(e -> e.getValue().getAgent()).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
	return view.toString();
    }
}