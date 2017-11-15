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

/**
 * 
 * This class is the <b>API</b> for <b><code>VacuumWorld-2.0</code></b>.
 * 
 * @author Ben Wilkins
 * @author cloudstrife9999
 *
 */
public class VacuumWorldGridContent {
    private Map<Position, Tile> view;
    private Position selfposition;
    private Orientation selfOrientation;

    /**
     * 
     * Constructs the {@link VacuumWorldGridContent} from the {@link Map} from
     * {@link Position} to {@link Tile} (i.e., the content of the environment), and
     * the {@link Position} of the {@link Agent} this perception is for.
     * 
     * @param view the content of the environment.
     * @param selfposition the {@link Position} of the {@link Agent} this perception is for.
     * 
     */
    public VacuumWorldGridContent(Map<Position, Tile> view, Position selfposition) {
	this.selfposition = selfposition;
	this.view = view;
	this.selfOrientation = getSelf().getOrientation();
    }

    /**
     * 
     * Returns whether there is an {@link Agent} (cleaning-agent/user/avatar) on the {@link Tile} in front of the one where the current {@link Agent} is.<br /><br />
     * "In front of" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not there is an {@link Agent} (cleaning-agent/user/avatar) on the {@link Tile} in front of the one where the current {@link Agent} is.
     * 
     */
    public boolean isAgentForward() {
	return isAgentOnPosition(this.selfposition.getForward(this.selfOrientation));
    }

    /**
     * 
     * Returns whether there is an {@link Agent} (cleaning-agent/user/avatar) on the {@link Tile} on the left w.r.t the one where the current {@link Agent} is.<br /><br />
     * "Left" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not there is an {@link Agent} (cleaning-agent/user/avatar) on the {@link Tile} on the left w.r.t the one where the current {@link Agent} is.
     * 
     */
    public boolean isAgentOnTheLeft() {
	return isAgentOnPosition(this.selfposition.getLeft(this.selfOrientation));
    }

    /**
     * 
     * Returns whether there is an {@link Agent} (cleaning-agent/user/avatar) on the {@link Tile} on the right w.r.t the one where the current {@link Agent} is.<br /><br />
     * "Right" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not there is an {@link Agent} (cleaning-agent/user/avatar) on the {@link Tile} on the right w.r.t the one where the current {@link Agent} is.
     * 
     */
    public boolean isAgentOnTheRight() {
	return isAgentOnPosition(this.selfposition.getRight(this.selfOrientation));
    }

    /**
     * 
     * Returns whether there is an {@link Agent} (cleaning-agent/user/avatar) on the {@link Tile} on the forward-left w.r.t the one where the current {@link Agent} is.<br /><br />
     * "Forward-left" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not there is an {@link Agent} (cleaning-agent/user/avatar) on the {@link Tile} on the forward-left w.r.t the one where the current {@link Agent} is.
     * 
     */
    public boolean isAgentForwardLeft() {
	return isAgentOnPosition(this.selfposition.getForwardLeft(this.selfOrientation));
    }

    /**
     * 
     * Returns whether there is an {@link Agent} (cleaning-agent/user/avatar) on the {@link Tile} on the forward-right w.r.t the one where the current {@link Agent} is.<br /><br />
     * "Forward-right" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not there is an {@link Agent} (cleaning-agent/user/avatar) on the {@link Tile} on the forward-right w.r.t the one where the current {@link Agent} is.
     * 
     */
    public boolean isAgentForwardRight() {
	return isAgentOnPosition(this.selfposition.getForwardRight(this.selfOrientation));
    }

    private boolean isAgentOnPosition(Position position) {
	Tile t = this.view.get(position);

	return t == null ? false : t.containsAgent();
    }

    /**
     * 
     * Returns whether there is a {@link Dirt} on the {@link Tile} where the current {@link Agent} is.
     * 
     * @return whether or not there is a {@link Dirt} on the {@link Tile} where the current {@link Agent} is.
     * 
     */
    public boolean isDirtOnAgentPosition() {
	return isDirtOnPosition(this.selfposition);
    }

    /**
     * 
     * Returns whether there is a {@link Dirt} on the {@link Tile} in front of the one where the current {@link Agent} is.<br /><br />
     * "In front of" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not there is a {@link Dirt} on the {@link Tile} in front of the one where the current {@link Agent} is.
     * 
     */
    public boolean isDirtForward() {
	return isDirtOnPosition(this.selfposition.getForward(this.selfOrientation));
    }

    /**
     * 
     * Returns whether there is a {@link Dirt} on the {@link Tile} on the left w.r.t the one where the current {@link Agent} is.<br /><br />
     * "Left" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not there is a {@link Dirt} on the {@link Tile} on the left w.r.t the one where the current {@link Agent} is.
     * 
     */
    public boolean isDirtOnTheLeft() {
	return isDirtOnPosition(this.selfposition.getLeft(this.selfOrientation));
    }

    /**
     * 
     * Returns whether there is a {@link Dirt} on the {@link Tile} on the right w.r.t the one where the current {@link Agent} is.<br /><br />
     * "Right" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not there is a {@link Dirt} on the {@link Tile} on the right w.r.t the one where the current {@link Agent} is.
     * 
     */
    public boolean isDirtOnTheRight() {
	return isDirtOnPosition(this.selfposition.getRight(this.selfOrientation));
    }

    /**
     * 
     * Returns whether there is a {@link Dirt} on the {@link Tile} on the forward-left w.r.t the one where the current {@link Agent} is.<br /><br />
     * "Forward-left" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not there is a {@link Dirt} on the {@link Tile} on the forward-left w.r.t the one where the current {@link Agent} is.
     * 
     */
    public boolean isDirtForwardLeft() {
	return isDirtOnPosition(this.selfposition.getForwardLeft(this.selfOrientation));
    }

    /**
     * 
     * Returns whether there is a {@link Dirt} on the {@link Tile} on the forward-right w.r.t the one where the current {@link Agent} is.<br /><br />
     * "Forward-right" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not there is a {@link Dirt} on the {@link Tile} on the forward-right w.r.t the one where the current {@link Agent} is.
     * 
     */
    public boolean isDirtForwardRight() {
	return isDirtOnPosition(this.selfposition.getForwardRight(this.selfOrientation));
    }

    private boolean isDirtOnPosition(Position position) {
	Tile t = this.view.get(position);

	return t == null ? false : t.containsDirt();
    }

    /**
     * 
     * Returns whether there is a wall on the {@link Tile} in front of the one where the current {@link Agent} is.<br /><br />
     * "In front of" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not there is a wall on the {@link Tile} in front of the one where the current {@link Agent} is.
     * 
     */
    public boolean isWallForward() {
	return isWallOnPosition(this.selfposition.getForward(this.selfOrientation));
    }

    /**
     * 
     * Returns whether there is a wall on the {@link Tile} on the left w.r.t the one where the current {@link Agent} is.<br /><br />
     * "Left" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not there is a wall on the {@link Tile} on the left w.r.t the one where the current {@link Agent} is.
     * 
     */
    public boolean isWallOnTheLeft() {
	return isWallOnPosition(this.selfposition.getLeft(this.selfOrientation));
    }

    /**
     * 
     * Returns whether there is a wall on the {@link Tile} on the right w.r.t the one where the current {@link Agent} is.<br /><br />
     * "Right" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not there is a wall on the {@link Tile} on the right w.r.t the one where the current {@link Agent} is.
     * 
     */
    public boolean isWallOnTheRight() {
	return isWallOnPosition(this.selfposition.getRight(this.selfOrientation));
    }

    /**
     * 
     * Returns whether there is a wall on the {@link Tile} on the forward-left w.r.t the one where the current {@link Agent} is.<br /><br />
     * "Forward-left" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not there is a wall on the {@link Tile} on the forward-left w.r.t the one where the current {@link Agent} is.
     * 
     */
    public boolean isWallForwardLeft() {
	return isWallOnPosition(this.selfposition.getForwardLeft(this.selfOrientation));
    }

    /**
     * 
     * Returns whether there is a wall on the {@link Tile} on the forward-right w.r.t the one where the current {@link Agent} is.<br /><br />
     * "Forward-right" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not there is a wall on the {@link Tile} on the forward-right w.r.t the one where the current {@link Agent} is.
     * 
     */
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

    /**
     * 
     * Returns whether the {@link Tile} ahead of the one where the current {@link Agent} resides is accessible (i.e., it does not contain any cleaning-agent/user/avatar and it is not a wall). Note that it may or may not contain {@link Dirt}.<br /><br />
     * "Ahead" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not the {@link Tile} ahead of the one where the current {@link Agent} resides is accessible (i.e., it does not contain any cleaning-agent/user/avatar and it is not a wall).
     * 
     */
    public boolean isForwardAccessible() {
	return isPositionAccessible(this.selfposition.getForward(this.selfOrientation));
    }

    /**
     * 
     * Returns whether the {@link Tile} on the left w.r.t. the one where the current {@link Agent} resides is accessible (i.e., it does not contain any cleaning-agent/user/avatar and it is not a wall). Note that it may or may not contain {@link Dirt}.<br /><br />
     * "Left" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not the {@link Tile} on the left w.r.t. the one where the current {@link Agent} resides is accessible (i.e., it does not contain any cleaning-agent/user/avatar and it is not a wall).
     * 
     */
    public boolean isLeftAccessible() {
	return isPositionAccessible(this.selfposition.getLeft(this.selfOrientation));
    }

    /**
     * 
     * Returns whether the {@link Tile} on the right w.r.t. the one where the current {@link Agent} resides is accessible (i.e., it does not contain any cleaning-agent/user/avatar and it is not a wall). Note that it may or may not contain {@link Dirt}.<br /><br />
     * "Right" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not the {@link Tile} on the right w.r.t. the one where the current {@link Agent} resides is accessible (i.e., it does not contain any cleaning-agent/user/avatar and it is not a wall).
     * 
     */
    public boolean isRightAccessible() {
	return isPositionAccessible(this.selfposition.getRight(this.selfOrientation));
    }

    /**
     * 
     * Returns whether the {@link Tile} on the forward-left w.r.t. the one where the current {@link Agent} resides is accessible (i.e., it does not contain any cleaning-agent/user/avatar and it is not a wall). Note that it may or may not contain {@link Dirt}.<br /><br />
     * "Forward-left" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not the {@link Tile} on the forward-left w.r.t. the one where the current {@link Agent} resides is accessible (i.e., it does not contain any cleaning-agent/user/avatar and it is not a wall).
     * 
     */
    public boolean isForwardLeftAccessible() {
	return isPositionAccessible(this.selfposition.getForwardLeft(this.selfOrientation));
    }

    /**
     * 
     * Returns whether the {@link Tile} on the forward-right w.r.t. the one where the current {@link Agent} resides is accessible (i.e., it does not contain any cleaning-agent/user/avatar and it is not a wall). Note that it may or may not contain {@link Dirt}.<br /><br />
     * "Forward-right" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return whether or not the {@link Tile} on the forward-right w.r.t. the one where the current {@link Agent} resides is accessible (i.e., it does not contain any cleaning-agent/user/avatar and it is not a wall).
     * 
     */
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

    private VacuumWorldAgentAppearance getAgent(Position position) {
	return isAgentOnPosition(position) ? this.view.get(position).getAgent() : null;
    }

    /**
     * 
     * Returns the {@link VacuumWorldAgentAppearance} of the agent/user/avatar which resides on the {@link Tile} ahead of the one where the current {@link Agent} is, if any.<br /><br />
     * "Ahead" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return the {@link VacuumWorldAgentAppearance} of the agent/user/avatar which resides on the {@link Tile} ahead of the one where the current {@link Agent} is, if any, <code>null</code> otherwise.
     * 
     */
    public VacuumWorldAgentAppearance getAgentForwardIfAny() {
	return getAgent(this.selfposition.getForward(this.selfOrientation));
    }

    /**
     * 
     * Returns the {@link VacuumWorldAgentAppearance} of the agent/user/avatar which resides on the {@link Tile} on the left w.r.t. the one where the current {@link Agent} is, if any.<br /><br />
     * "Left" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return the {@link VacuumWorldAgentAppearance} of the agent/user/avatar which resides on the {@link Tile} on the left w.r.t. the one where the current {@link Agent} is, if any, <code>null</code> otherwise.
     * 
     */
    public VacuumWorldAgentAppearance getAgentOnTheLeftIfAny() {
	return getAgent(this.selfposition.getLeft(this.selfOrientation));
    }

    /**
     * 
     * Returns the {@link VacuumWorldAgentAppearance} of the agent/user/avatar which resides on the {@link Tile} on the right w.r.t. the one where the current {@link Agent} is, if any.<br /><br />
     * "Right" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return the {@link VacuumWorldAgentAppearance} of the agent/user/avatar which resides on the {@link Tile} on the right w.r.t. the one where the current {@link Agent} is, if any, <code>null</code> otherwise.
     * 
     */
    public VacuumWorldAgentAppearance getAgentOnTheRightIfAny() {
	return getAgent(this.selfposition.getRight(this.selfOrientation));
    }

    /**
     * 
     * Returns the {@link VacuumWorldAgentAppearance} of the agent/user/avatar which resides on the {@link Tile} on the forward-left w.r.t. the one where the current {@link Agent} is, if any.<br /><br />
     * "Forward-left" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return the {@link VacuumWorldAgentAppearance} of the agent/user/avatar which resides on the {@link Tile} on the forward-left w.r.t. the one where the current {@link Agent} is, if any, <code>null</code> otherwise.
     * 
     */
    public VacuumWorldAgentAppearance getAgentOnForwardLeftIfAny() {
	return getAgent(this.selfposition.getForwardLeft(this.selfOrientation));
    }

    /**
     * 
     * Returns the {@link VacuumWorldAgentAppearance} of the agent/user/avatar which resides on the {@link Tile} on the forward-right w.r.t. the one where the current {@link Agent} is, if any.<br /><br />
     * "Forward-right" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return the {@link VacuumWorldAgentAppearance} of the agent/user/avatar which resides on the {@link Tile} on the forward-right w.r.t. the one where the current {@link Agent} is, if any, <code>null</code> otherwise.
     * 
     */
    public VacuumWorldAgentAppearance getAgentOnForwardRightIfAny() {
	return getAgent(this.selfposition.getForwardRight(this.selfOrientation));
    }

    private DirtAppearance getDirt(Position position) {
	return isDirtOnPosition(position) ? this.view.get(position).getDirt() : null;
    }

    /**
     * 
     * Returns the {@link DirtAppearance} of the {@link Dirt} which resides on the {@link Tile} where the current {@link Agent} is, if any.
     * 
     * @return the {@link DirtAppearance} of the {@link Dirt} which resides on the {@link Tile} where the current {@link Agent} is, if any, <code>null</code> otherwise.
     * 
     */
    public DirtAppearance getDirtOnCurrentPositionIfAny() {
	return getDirt(this.selfposition);
    }

    /**
     * 
     * Returns the {@link DirtAppearance} of the {@link Dirt} which resides on the {@link Tile} ahead of the one where the current {@link Agent} is, if any.<br /><br />
     * "Ahead" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return the {@link DirtAppearance} of the {@link Dirt} which resides on the {@link Tile} ahead of the one where the current {@link Agent} is, if any, <code>null</code> otherwise.
     * 
     */
    public DirtAppearance getDirtForwardIfAny() {
	return getDirt(this.selfposition.getForward(this.selfOrientation));
    }

    /**
     * 
     * Returns the {@link DirtAppearance} of the {@link Dirt} which resides on the {@link Tile} on the left w.r.t the one where the current {@link Agent} is, if any.<br /><br />
     * "Left" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return the {@link DirtAppearance} of the {@link Dirt} which resides on the {@link Tile} on the left w.r.t the one where the current {@link Agent} is, if any, <code>null</code> otherwise.
     * 
     */
    public DirtAppearance getDirtOnTheLeftIfAny() {
	return getDirt(this.selfposition.getLeft(this.selfOrientation));
    }

    /**
     * 
     * Returns the {@link DirtAppearance} of the {@link Dirt} which resides on the {@link Tile} on the right w.r.t the one where the current {@link Agent} is, if any.<br /><br />
     * "Right" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return the {@link DirtAppearance} of the {@link Dirt} which resides on the {@link Tile} on the right w.r.t the one where the current {@link Agent} is, if any, <code>null</code> otherwise.
     * 
     */
    public DirtAppearance getDirtOnTheRightIfAny() {
	return getDirt(this.selfposition.getRight(this.selfOrientation));
    }

    /**
     * 
     * Returns the {@link DirtAppearance} of the {@link Dirt} which resides on the {@link Tile} on the forward-left w.r.t the one where the current {@link Agent} is, if any.<br /><br />
     * "Forward-left" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return the {@link DirtAppearance} of the {@link Dirt} which resides on the {@link Tile} on the forward-left w.r.t the one where the current {@link Agent} is, if any, <code>null</code> otherwise.
     * 
     */
    public DirtAppearance getDirtOnForwardLeftIfAny() {
	return getDirt(this.selfposition.getForwardLeft(this.selfOrientation));
    }

    /**
     * 
     * Returns the {@link DirtAppearance} of the {@link Dirt} which resides on the {@link Tile} on the forward-right w.r.t the one where the current {@link Agent} is, if any.<br /><br />
     * "Forward-right" is relative to the current {@link Agent} {@link Orientation}.
     * 
     * @return the {@link DirtAppearance} of the {@link Dirt} which resides on the {@link Tile} on the forward-right w.r.t the one where the current {@link Agent} is, if any, <code>null</code> otherwise.
     * 
     */
    public DirtAppearance getDirtOnForwardRightIfAny() {
	return getDirt(this.selfposition.getForwardRight(this.selfOrientation));
    }

    /**
     * 
     * Gets the {@link VacuumWorldAgentAppearance} of the {@link Agent} who has received the
     * {@link VacuumWorldGridPerception}. The receiving {@link Agent} is always in
     * the centre of the 3x2 or 2x3 view.
     * 
     * @return the self {@link VacuumWorldAgentAppearance}.
     * 
     */
    public VacuumWorldAgentAppearance getSelf() {
	return getAgent(this.selfposition);
    }

    /**
     * 
     * Gets all of the {@link Position}s that the {@link Agent} can currently view.
     * This does not include the {@link Position}s of any walls (as they are
     * technically out of bounds).
     * 
     * @return a {@link Collection} of the non-wall {@link Position}s.
     * 
     */
    public Collection<Position> getAllPositions() {
	return this.view.entrySet().stream().map(Entry::getKey).filter(this::isWallNotOnPosition).collect(Collectors.toSet());
    }

    /**
     * Gets all of the accessible {@link Position}s in this {@link Agent}s current
     * {@link Perception}. That is, all {@link Position}s that have only a {@link Dirt} or
     * nothing in them.
     * 
     * @return a {@link Collection} of the accessible  {@link Position}s.
     * 
     */
    public Collection<Position> getAccessiblePositions() {
	return this.view.entrySet().stream().map(Entry::getKey).filter(this::isPositionAccessible).collect(Collectors.toSet());
    }

    /***
     * 
     * @deprecated This method is deprecated, as its name might be misleading. Use
     * {@link #getAccessiblePositions()} instead.
     *
     */
    @Deprecated
    public Collection<Position> getFreePositions() {
	return getAccessiblePositions();
    }

    /**
     * Gets all of the inaccessible {@link Position}s in this {@link Agent}s current
     * {@link Perception}. That is, all {@link Position}s that are filled with an
     * {@link Agent} (cleaning-agent/user/avatar) or are a wall. (Note that this {@link Agent} fills a
     * {@link Position}).
     * 
     * @return a {@link Collection} of the inaccessible {@link Position}s.
     * 
     */
    public Collection<Position> getInaccessiblePositions() {
	return this.view.entrySet().stream().map(Entry::getKey).filter(this::isPositionInaccessible).collect(Collectors.toSet());
    }

    /**
     * 
     * @deprecated This method is deprecated, as its name might be misleading. Use
     * {@link #getInaccessiblePositions()} instead.
     * 
     */
    @Deprecated
    public Collection<Position> getFilledPositions() {
	return getInaccessiblePositions();
    }

    /**
     * Gets the {@link Position}s of all the Walls that this {@link Agent} can
     * currently see.
     * 
     * @return a {@link Collection} of the wall {@link Position}s.
     * 
     */
    public Collection<Position> getWallPositions() {
	return this.view.entrySet().stream().map(Entry::getKey).filter(this::isWallOnPosition).collect(Collectors.toSet());
    }

    /**
     * Gets the {@link Position}s of all the {@link Dirt}s that this {@link Agent}
     * can currently see.
     * 
     * @return a {@link Collection} of the {@link Dirt} {@link Position}s.
     * 
     */
    public Collection<Position> getDirtPositions() {
	return this.view.entrySet().stream().map(Entry::getKey).filter(this::isDirtOnPosition).collect(Collectors.toSet());
    }

    /**
     * Gets the {@link Position}s of all the {@link Agent}s (cleaning-agents/users/avatars) that this {@link Agent}
     * can currently see.
     * 
     * @return a {@link Collection} of the {@link Agent}s (cleaning-agents/users/avatars) {@link Position}s.
     * 
     */
    public Collection<Position> getAgentPositions() {
	return this.view.entrySet().stream().map(Entry::getKey).filter(this::isAgentOnPosition).collect(Collectors.toSet());
    }

    /**
     * Gets all {@link DirtAppearance}s of the {@link Dirt}s that this {@link Agent} can
     * currently see.
     * 
     * @return a {@link Collection} of the {@link Dirt}s {@link Appearance}s.
     * 
     */
    public Collection<DirtAppearance> getDirts() {
	return this.view.entrySet().stream().filter(e -> isDirtOnPosition(e.getKey())).map(e -> e.getValue().getDirt()).collect(Collectors.toSet());
    }

    /**
     * Gets all {@link Appearance}s of the {@link Agent}s that this {@link Agent}
     * can currently see, excluding self.
     * 
     * @return a {@link Collection} of the other {@link Agent}s {@link Appearance}s.
     * 
     */
    public Collection<VacuumWorldAgentAppearance> getAgents() {
	return this.view.entrySet().stream().filter(e -> isAgentOnPosition(e.getKey())).map(e -> e.getValue().getAgent()).filter(a -> !getSelf().getId().equals(a.getId())).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
	return view.toString();
    }
}