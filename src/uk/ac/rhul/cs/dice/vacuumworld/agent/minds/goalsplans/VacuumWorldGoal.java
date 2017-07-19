package uk.ac.rhul.cs.dice.vacuumworld.agent.minds.goalsplans;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans.Goal;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans.StackPlan;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;

public abstract class VacuumWorldGoal extends Goal<VacuumWorldAction> {

	public void setPlan(StackPlan<VacuumWorldAction> plan) {
		this.plan = plan;
	}

	public abstract boolean validate(VacuumWorldGridPerception perception);

	@Override
	public final boolean validate(Collection<Perception<?>> perceptions) {
		return validate(perceptions.toArray(new VacuumWorldGridPerception[1])[0]);
	}

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
		return this.getClass().getSimpleName() + " : "
				+ ((this.plan != null) ? this.plan : "");
	}

	@Override
	public VacuumWorldGoal peekSubGoal() {
		return (VacuumWorldGoal) super.peekSubGoal();
	}

	@Override
	public VacuumWorldGoal popSubGoal() {
		return (VacuumWorldGoal) super.popSubGoal();
	}
}
