package uk.ac.rhul.cs.dice.vacuumworld.agent.minds.goalsplans;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans.GoalStack;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;

public abstract class VacuumWorldGoalStack extends GoalStack<VacuumWorldAction> {

	public abstract GoalStack<VacuumWorldAction> validate(
			VacuumWorldGridPerception perception);

	@Override
	public final GoalStack<VacuumWorldAction> validate(
			Collection<Perception<?>> perceptions) {
		return validate((VacuumWorldGridPerception) perceptions.toArray()[0]);
	}

	@Override
	public String toString() {
		if (stack.isEmpty()) {
			return "No goals";
		}
		StringBuilder b = new StringBuilder();
		b.append(stack.get(stack.size() - 1) + System.lineSeparator());
		for (int i = stack.size() - 2; i >= 0; i--) {
			b.append(stack.get(i).getClass().getSimpleName()
					+ System.lineSeparator());
		}
		return b.toString();
	}

	@Override
	public VacuumWorldGoal peek() {
		return (VacuumWorldGoal) super.peek();
	}

	@Override
	public VacuumWorldGoal pop() {
		return (VacuumWorldGoal) super.pop();
	}
}
