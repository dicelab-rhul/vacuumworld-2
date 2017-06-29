package uk.ac.rhul.cs.dice.vacuumworld.agent;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldPerceptionContent;

public abstract class AbstractVacuumWorldAgentMind extends AbstractAgentMind {

	@Override
	public final Perception<?> perceive(Object... parameters) {
		perceive(unpackVacuumWorldPerception(parameters));
		return null;
	}

	@Override
	public final Action decide(Object... parameters) {
		return decide();
	}

	@Override
	public final Action execute(Object... parameters) {
		return doAct((AbstractEnvironmentalAction) execute((VacuumWorldAction) parameters[0]));
	}

	/**
	 * The perceive method. This receives a {@link Collection} of
	 * {@link VacuumWorldPerception}s as input. To access their content call
	 * {@link VacuumWorldPerception#getPerception()}. The
	 * {@link VacuumWorldPerceptionContent} may be communication or the 3x2 view
	 * of the {@link Environment}. It is up to you to deal with these
	 * {@link Perception}s in a way you feel suitable.
	 * 
	 * @param perceptions
	 *            received from the current cycle
	 */
	public abstract void perceive(Collection<VacuumWorldPerception> perceptions);

	/**
	 * The decide method. This method should use any relevant {@link Perception}
	 * s to decide which {@link Action} to take. An {@link Agent} may only
	 * decide on one {@link Action} per cycle. That {@link Action} should be
	 * returned by this method.
	 * 
	 * @return the action to attempt
	 */
	public abstract VacuumWorldAction decide();

	/**
	 * The execute method. This method receives the {@link Action} that was
	 * decided upon in the {@link AbstractVacuumWorldAgentMind#decide()
	 * decide()} method. The execute method may be used to record which
	 * {@link Action}s have been attempted. To execute the {@link Action} in the
	 * next cycle, return the {@link Action}. A successfully executed
	 * {@link Action} will give a {@link Perception} which will be received by
	 * the {@link AbstractVacuumWorldAgentMind#perceive(Collection)
	 * perceive(Collection)} method in the next cycle.
	 * 
	 * @param action
	 *            : from {@link AbstractVacuumWorldAgentMind#decide() decide()}
	 * @return the {@link Action} to be executed in the next cycle.
	 */
	public abstract VacuumWorldAction execute(VacuumWorldAction action);

	private Collection<VacuumWorldPerception> unpackVacuumWorldPerception(
			Object... parameters) {
		Collection<VacuumWorldPerception> perceptions = new HashSet<>();
		try {
			if (parameters.length == 1) {
				if (Collection.class.isAssignableFrom(parameters[0].getClass())) {
					((Collection<?>) parameters[0])
							.forEach((Object o) -> {
								if (Perception.class.isAssignableFrom(o
										.getClass())) {
									Perception<?> p = (Perception<?>) o;
									if (VacuumWorldPerception.class
											.isAssignableFrom(p.getClass())) {
										perceptions
												.add((VacuumWorldPerception) o);
									} else {
										DefaultPerception<?> dp = (DefaultPerception<?>) o;
										perceptions
												.add(new VacuumWorldPerception(
														(VacuumWorldPerceptionContent) ((Pair<?, ?>) dp
																.getPerception())
																.getSecond()));
									}
								}
							});
					return perceptions;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("Error: Failed to unpack perceptions: "
				+ Arrays.toString(parameters));
		return null;
	}
}
