package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultSimpleUniverse;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;

public class VacuumWorldUniverse extends DefaultSimpleUniverse {

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
		System.out.println(action);
		super.notify(action, toNotify, perceptions, context);
	}

	public void initialiseGrid(int dimension,
			Collection<VacuumWorldAgent> agents, Collection<Dirt> dirts) {
		agents.forEach((a) -> this.addAgent(a));
		dirts.forEach((d) -> this.addPassiveBody(d));
		this.getAmbient().initialiseGrid(dimension, agents, dirts);
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
}
