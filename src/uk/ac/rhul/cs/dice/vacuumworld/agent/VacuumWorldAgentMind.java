package uk.ac.rhul.cs.dice.vacuumworld.agent;

import java.util.ArrayList;
import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.vacuumworld.actions.MoveAction;

public class VacuumWorldAgentMind extends AbstractAgentMind {

	@Override
	public Perception<?> perceive(Object... parameters) {
		return null;
	}

	@Override
	public Collection<Action> decide(Object... parameters) {
		Collection<Action> actions = new ArrayList<>();
		actions.add(new MoveAction());
		return actions;

	}

	@Override
	public Action execute(Object... parameters) {
		@SuppressWarnings("unchecked")
		Collection<Action> actions = (Collection<Action>) parameters[0];
		return doAct((AbstractEnvironmentalAction) actions.toArray()[0]);
	}

}
