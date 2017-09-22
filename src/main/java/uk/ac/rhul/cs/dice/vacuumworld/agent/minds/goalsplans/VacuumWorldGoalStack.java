package uk.ac.rhul.cs.dice.vacuumworld.agent.minds.goalsplans;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans.GoalStack;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;

public abstract class VacuumWorldGoalStack extends
		GoalStack<VacuumWorldGoal, VacuumWorldAction> {

	@Override
	public String toString() {
		if (goals.isEmpty()) {
			return "No goals";
		}
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < goals.size(); i++) {
			b.append(goals.get(i).getClass().getSimpleName()
					+ System.lineSeparator());
		}
		b.append(goals.get(goals.size() - 1));
		return b.toString();
	}
}
