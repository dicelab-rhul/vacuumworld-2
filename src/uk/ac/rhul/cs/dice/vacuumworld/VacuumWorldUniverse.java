package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractSelfishAvatarMind;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.filter.SelfFilter;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultSimpleUniverse;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.avatar.VacuumWorldAvatar;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;

public class VacuumWorldUniverse extends DefaultSimpleUniverse {

	private volatile boolean paused = false;

	public VacuumWorldUniverse(
			VacuumWorldAmbient ambient,
			VacuumWorldPhysics physics,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(ambient, physics, new EnvironmentAppearance(IDFactory
				.getInstance().getNewID(), false, true), possibleActions);
	}

	public void updateView() {
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void notify(AbstractEnvironmentalAction action,
			ActiveBodyAppearance toNotify,
			Collection<AbstractPerception<?>> perceptions, Ambient context) {
		super.notify(action, toNotify, perceptions, context);
	}

	public void initialiseGrid(int dimension, int simulationrate,
			Collection<VacuumWorldAgent> agents, Collection<Dirt> dirts,
			Collection<VacuumWorldAvatar> avatars) {
		agents.forEach((a) -> this.addAgent(a));
		dirts.forEach((d) -> this.addPassiveBody(d));
		avatars.forEach((a) -> this.addAvatar(a));
		// if there is a selfish avatar, the frame gap should be 0
		if (avatars.size() != 0) {
			for (VacuumWorldAvatar a : avatars) {
				if (AbstractSelfishAvatarMind.class.isAssignableFrom(a
						.getMind().getClass())) {
					this.physics.setFramelength(0);
					break;
				}
			}
		} else {
			this.physics.setFramelength(simulationrate);
		}
		this.getAmbient().initialiseGrid(dimension);
		System.out.println(this.subscriber.getSensors());
		System.out.println(this.subscriber.getSubscribedSensors());
		System.out.println(this.subscriber.getActionPerceptions());
		System.out.println(this.subscriber.getPerceptionSensors());
	}

	@Override
	public VacuumWorldAmbient getState() {
		return (VacuumWorldAmbient) super.getState();
	}

	@Override
	public void simulate() {
		System.out.println("STARTING VACUUM WORLD");
		physics.simulate();
	}

	public VacuumWorldAmbient getAmbient() {
		return (VacuumWorldAmbient) this.ambient;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
}
