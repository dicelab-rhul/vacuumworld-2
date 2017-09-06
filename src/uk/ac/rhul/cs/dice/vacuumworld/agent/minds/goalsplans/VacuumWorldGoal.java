package uk.ac.rhul.cs.dice.vacuumworld.agent.minds.goalsplans;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans.Goal;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorld;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;

public abstract class VacuumWorldGoal extends Goal<VacuumWorldAction> {

	@Override
	public VacuumWorldPlan getPlan() {
		return (VacuumWorldPlan) super.getPlan();
	}

	@Override
	public VacuumWorldGoalStack getSubgoals() {
		return (VacuumWorldGoalStack) super.getSubgoals();
	}

	@Override
	public String toString() {
		return toString("");
	}

	public String toString(String gap) {
		return gap
				+ this.getClass().getSimpleName()
				+ " : "
				+ ((this.plan != null) ? this.getPlan().toString(gap + "  ")
						: "");
	}

	/**
	 * Equivalent to {@link Goal#isPossible(Collection)}, this a convenience
	 * method specific to {@link VacuumWorld}.
	 * 
	 * @param perception
	 *            : of the {@link Agent}
	 * @return <code>true</code> if this {@link Goal} is possible,
	 *         <code>false</code> otherwise
	 */
	public abstract boolean isPossible(VacuumWorldGridPerception perception);

	@Override
	public final boolean isPossible(Collection<Perception<?>> perceptions) {
		return this.isPossible((VacuumWorldGridPerception) perceptions
				.toArray()[0]);
	}
}
