package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observer;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractSelfishAvatarMind;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.Avatar;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultSimpleUniverse;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;
import uk.ac.rhul.cs.dice.vacuumworld.actions.CleanAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.MoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.PlaceDirtAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.TurnAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldCommunicationAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.avatar.VacuumWorldAvatar;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.avatar.VacuumWorldSelfishAvatarMind;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.grid.Grid;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.LogUtils;

/**
 * The {@link Universe} of {@link VacuumWorld}. The {@link Universe} contains
 * the {@link VacuumWorldAmbient} and {@link VacuumWorldPhysics}. It has various
 * control methods, such as starting, restarting and pausing as well as
 * initialisation methods for building a new simulation. It also contains all of
 * the possible {@link Action}s in {@link VacuumWorldUniverse#POSSIBLE_ACTIONS}.
 * 
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class VacuumWorldUniverse extends DefaultSimpleUniverse {

    /**
     * All {@link Action}s that are possible in the {@link VacuumWorldUniverse}.
     */
    private static final Collection<Class<? extends AbstractEnvironmentalAction>> POSSIBLE_ACTIONS = new ArrayList<>();

    static {
	POSSIBLE_ACTIONS.add(CleanAction.class);
	POSSIBLE_ACTIONS.add(TurnAction.class);
	POSSIBLE_ACTIONS.add(MoveAction.class);
	POSSIBLE_ACTIONS.add(PlaceDirtAction.class);
	POSSIBLE_ACTIONS.add(VacuumWorldCommunicationAction.class);
	POSSIBLE_ACTIONS.add(VacuumWorldSensingAction.class);
    }

    private volatile boolean paused = false;
    private volatile boolean stop = false;
    private volatile boolean pausedSafe = false;

    /**
     * Constructor.
     * 
     * @param ambient
     * @param physics
     */
    public VacuumWorldUniverse() {
	super(new VacuumWorldAmbient(null, null, null, null), new VacuumWorldPhysics(),
		new EnvironmentAppearance(IDFactory.getInstance().getNewID(), false, true), POSSIBLE_ACTIONS);
    }

    /**
     * Notifies the any {@link Observer}s (usually a view) that something has
     * changed.
     */
    public void updateView() {
	this.setChanged();
	this.notifyObservers();
    }

    /**
     * Initialises a new {@link Grid} within the {@link Ambient}. Adding and
     * subscribing all {@link Agent}s, {@link Dirt}s, {@link Avatar} to the
     * {@link Universe}. If a selfish {@link Avatar} is provided, (i.e. a
     * {@link VacuumWorldAvatar} with a {@link VacuumWorldSelfishAvatarMind}) the
     * simulation speed is ignored. The system will wait for the {@link Avatar} to
     * attempt an {@link Action} before continuing.
     * 
     * @param dimension
     *            : of the {@link Grid}.
     * @param simulationrate
     *            : speed of the simulation
     * @param agents
     *            : the {@link VacuumWorldAgent}s
     * @param dirts
     *            : the {@link Dirt}s
     * @param avatars
     *            : the {@link Avatar}s
     */
    public void initialiseGrid(int dimension, int simulationrate, Collection<VacuumWorldAgent> agents,
	    Collection<Dirt> dirts, Collection<VacuumWorldAvatar> avatars) {
	if (this.getAmbient().getGrid() != null) {
	    this.getAmbient().clear();
	    this.getSubscriber().clearSensorSubscriptions();
	}
	agents.forEach(this::addAgent);
	dirts.forEach(this::addPassiveBody);
	avatars.forEach(this::addAvatar);
	// if there is a selfish avatar, the frame gap should be 0
	if (!avatars.isEmpty()) {
	    for (VacuumWorldAvatar a : avatars) {
		if (AbstractSelfishAvatarMind.class.isAssignableFrom(a.getMind().getClass())) {
		    this.physics.setFramelength(0);
		    break;
		}
	    }
	} else {
	    this.physics.setFramelength(simulationrate);
	}
	this.getAmbient().initialiseGrid(dimension);
    }

    @Override
    public VacuumWorldAmbient getState() {
	return (VacuumWorldAmbient) super.getState();
    }

    @Override
    public void simulate() {
	LogUtils.log("STARTING VACUUM WORLD");
	this.stop = false;
	this.physics.simulate();
    }

    public VacuumWorldAmbient getAmbient() {
	return (VacuumWorldAmbient) this.ambient;
    }

    /**
     * Has there been an indication that the simulation should pause. This does not
     * mean the simulation is actually paused. See
     * {@link VacuumWorldUniverse#isPausedSafe()}.
     * 
     * @return true if the flag has been set, false otherwise.
     */
    public boolean isPaused() {
	return this.paused;
    }

    /**
     * Indicates to this {@link VacuumWorldUniverse} that the simulation should be
     * paused or resumed. This does not actually pause or resume the simulation.
     * 
     * @param value
     *            : pause (true) or resume (false)
     */
    public void setPaused(boolean value) {
	this.paused = value;
    }

    /**
     * Sets a flag indicating the simulation should stop.
     */
    public void stop() {
	this.stop = true;
    }

    /**
     * Should the simulation stop.
     * 
     * @return yes (true), no (false)
     */
    public boolean shouldStop() {
	return this.stop;
    }

    /**
     * Is the simulation actually paused.
     * 
     * @return true if the simulation is really paused, false otherwise
     */
    public boolean isPausedSafe() {
	return this.pausedSafe;
    }

    /**
     * This should be set internally (by {@link VacuumWorldPhysics}) when the
     * simulation is actually paused, or when it has really resumed.
     * 
     * @param value
     *            : paused (true), resumed (false)
     */
    protected void setPausedSafe(boolean value) {
	this.pausedSafe = value;
    }
}