package uk.ac.rhul.cs.dice.vacuumworld.agent.minds.goalsplans;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans.Goal;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans.StackPlan;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;

public abstract class VacuumWorldPlan extends StackPlan<VacuumWorldAction> {

	@Override
	public final boolean validate(Collection<Perception<?>> perceptions) {
		return validate((VacuumWorldGridPerception) perceptions.toArray()[0]);
	}

	public abstract boolean validate(VacuumWorldGridPerception perception);

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (VacuumWorldAction a : this.actions) {
			b.append(System.lineSeparator() + "    " + a);
		}
		return this.getClass().getSimpleName() + b.toString();
	}

	@Override
	public VacuumWorldGoalStack getSubgoals() {
		return (VacuumWorldGoalStack) super.getSubgoals();
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
