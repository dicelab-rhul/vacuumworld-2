package uk.ac.rhul.cs.dice.vacuumworld.agent.minds.goalsplans;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans.Goal;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.goalsplans.StackPlan;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorld;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;

public abstract class VacuumWorldPlan extends StackPlan<VacuumWorldAction> {

    @Override
    public String toString() {
	return toString("");
    }

    public String toString(String gap) {
	StringBuilder b = new StringBuilder();
	b.append(this.getClass().getSimpleName());
	final String egap = gap + "  ";
	if (this.hasSubGoals()) {
	    this.getSubgoals().forEach(g -> b.append(System.lineSeparator() + g.toString(egap)));
	}
	if (this.hasActions()) {
	    for (int i = 0; i < this.actions.size() - 1; i++) {
		b.append(System.lineSeparator() + egap + actions.get(i));
	    }
	    b.append(System.lineSeparator() + egap + actions.get(this.actions.size() - 1));
	}
	return b.toString();
    }

    @Override
    public VacuumWorldGoalStack getSubgoals() {
	return (VacuumWorldGoalStack) super.getSubgoals();
    }

    /**
     * Equivalent to {@link Plan#isPossible(Collection)}, this a convenience method
     * specific to {@link VacuumWorld}.
     * 
     * @param perception
     *            : of the {@link Agent}
     * @return <code>true</code> if this {@link Goal} is possible,
     *         <code>false</code> otherwise
     */
    public abstract boolean isPossible(VacuumWorldGridPerception perception);

    @Override
    public final boolean isPossible(Collection<Perception<?>> perceptions) {
	return this.isPossible((VacuumWorldGridPerception) perceptions.toArray()[0]);
    }
}
