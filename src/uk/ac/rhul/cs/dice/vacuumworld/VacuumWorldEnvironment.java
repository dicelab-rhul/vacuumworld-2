package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractState;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.Subscriber;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;

public class VacuumWorldEnvironment extends AbstractEnvironment implements
		Universe {

	private static final Integer GRIDDIM = 8;

	public VacuumWorldEnvironment(
			AbstractState state,
			AbstractPhysics physics,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(new Subscriber(), state, physics, false,
				new EnvironmentAppearance(IDFactory.getInstance().getNewID(),
						false, true), possibleActions);
	}

	@Override
	public void postInitialisation() {
		this.getState().initialiseRandomGrid(GRIDDIM);
	}

	@Override
	public Boolean isSimple() {
		return true;
	}

	@Override
	public boolean isDistributed() {
		return false;
	}

	@Override
	public VacuumWorldState getState() {
		return (VacuumWorldState) super.getState();
	}

	@Override
	public void simulate() {
		System.out.println("STARTING VACUUM WORLD");
		physics.simulate();
	}

	@Override
	public void run() {
		this.simulate();
	}
}
