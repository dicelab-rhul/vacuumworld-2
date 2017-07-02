package uk.ac.rhul.cs.dice.vacuumworld.agent;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldPerceptionContent;
import uk.ac.rhul.cs.dice.vacuumworld.readonly.ReadOnlyWrap;

public abstract class VacuumWorldAgentMind extends AbstractAgentMind {

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

	private boolean check(Collection<Position> positions,
			Orientation orientation) {
		if (!positions.isEmpty()) {
			Position current = this.getAppearance().getPosition();
			Position search = new Position(current.getX() + orientation.getI(),
					current.getY() + orientation.getJ());
			return positions.contains(search);
		}
		return false;
	}

	private boolean checkWall(VacuumWorldPerceptionContent perception,
			Orientation orientation) {
		return check(perception.getWalls(), orientation);
	}

	private boolean checkAgent(VacuumWorldPerceptionContent perception,
			Orientation orientation) {
		return check(perception.getAgentPositions(), orientation);
	}

	private boolean checkFilled(VacuumWorldPerceptionContent perception,
			Orientation orientation) {
		return check(perception.getFilledPositions(), orientation);
	}

	/**
	 * Check if this {@link Agent} is currently facing another {@link Agent} or
	 * a Wall.
	 * 
	 * @param perception
	 *            to check
	 * @return true if the {@link Agent} is currently facing a Wall, false
	 *         otherwise
	 */
	public boolean filledForward(VacuumWorldPerceptionContent perception) {
		return checkFilled(perception, this.getAppearance().getOrientation());
	}

	/**
	 * Check if there is a {@link Agent} or a Wall immediately to the right of
	 * this {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a {@link Agent} or a Wall immediately to the
	 *         right, false otherwise
	 */
	public boolean filledRight(VacuumWorldPerceptionContent perception) {
		return checkFilled(perception, this.getAppearance().getOrientation()
				.getRight());
	}

	/**
	 * Check if there is a {@link Agent} or a Wall immediately to the left of
	 * this {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a {@link Agent} or a Wall immediately to the
	 *         left, false otherwise
	 */
	public boolean filledLeft(VacuumWorldPerceptionContent perception) {
		return checkFilled(perception, this.getAppearance().getOrientation()
				.getLeft());
	}

	/**
	 * Check if this {@link Agent} is currently facing another {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if the {@link Agent} is currently another {@link Agent},
	 *         false otherwise
	 */
	public boolean agentForward(VacuumWorldPerceptionContent perception) {
		return checkAgent(perception, this.getAppearance().getOrientation());
	}

	/**
	 * Check if there is a {@link Agent} immediately to the right of this
	 * {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a {@link Agent} immediately to the right, false
	 *         otherwise
	 */
	public boolean agentRight(VacuumWorldPerceptionContent perception) {
		return checkAgent(perception, this.getAppearance().getOrientation()
				.getRight());
	}

	/**
	 * Check if there is a {@link Agent} immediately to the left of this
	 * {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a {@link Agent} immediately to the left, false
	 *         otherwise
	 */
	public boolean agentLeft(VacuumWorldPerceptionContent perception) {
		return checkAgent(perception, this.getAppearance().getOrientation()
				.getLeft());
	}

	/**
	 * Check if this {@link Agent} is currently facing a Wall.
	 * 
	 * @param perception
	 *            to check
	 * @return true if the {@link Agent} is currently facing a Wall, false
	 *         otherwise
	 */
	public boolean wallForward(VacuumWorldPerceptionContent perception) {
		return checkWall(perception, this.getAppearance().getOrientation());
	}

	/**
	 * Check if there is a Wall immediately to the right of this {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a Wall immediately to the right, false otherwise
	 */
	public boolean wallRight(VacuumWorldPerceptionContent perception) {
		return checkWall(perception, this.getAppearance().getOrientation()
				.getRight());
	}

	/**
	 * Check if there is a Wall immediately to the left of this {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a Wall immediately to the left, false otherwise
	 */
	public boolean wallLeft(VacuumWorldPerceptionContent perception) {
		return checkWall(perception, this.getAppearance().getOrientation()
				.getLeft());
	}

	/**
	 * Checks if this {@link Agent} can clean the given {@link Dirt}. </br>
	 * {@link BodyColor#ORANGE Orange} {@link Agent}s can clean
	 * {@link BodyColor#ORANGE Orange} {@link Dirt}, </br>
	 * {@link BodyColor#GREEN Green} {@link Agent}s can clean
	 * {@link BodyColor#GREEN Green} {@link Dirt}, </br> {@link BodyColor#WHITE
	 * White} {@link Agent}s can clean {@link BodyColor#ORANGE Orange}
	 * {@link Dirt} and {@link BodyColor#GREEN Green} {@link Dirt}
	 * 
	 * @param dirt
	 *            to check
	 * @return <code>true</code> if this {@link Agent} is able to clean the
	 *         given {@link Dirt}, <code>false</code> otherwise.
	 */
	public boolean canCleanDirt(DirtAppearance dirt) {
		return this.getAppearance().getColor().canClean(dirt.getColor());
	}

	/**
	 * Checks if this {@link Agent} is currently on top of some {@link Dirt}.
	 * 
	 * @return the {@link DirtAppearance} of the {@link Dirt} that this
	 *         {@link Agent} is currently on top of or <code>null</code> if the
	 *         {@link Agent} is not currently on top of any {@link Dirt}.
	 */
	public DirtAppearance onDirt(VacuumWorldPerceptionContent perception) {
		Collection<DirtAppearance> dirts = perception.getDirts();
		for (DirtAppearance d : dirts) {
			if (d.getPosition().equals(this.getAppearance().getPosition())) {
				return d;
			}
		}
		return null;
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
	 * decided upon in the {@link VacuumWorldAgentMind#decide()
	 * decide()} method. The execute method may be used to record which
	 * {@link Action}s have been attempted. To execute the {@link Action} in the
	 * next cycle, return the {@link Action}. A successfully executed
	 * {@link Action} will give a {@link Perception} which will be received by
	 * the {@link VacuumWorldAgentMind#perceive(Collection)
	 * perceive(Collection)} method in the next cycle.
	 * 
	 * @param action
	 *            : from {@link VacuumWorldAgentMind#decide() decide()}
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

	protected VacuumWorldAgentAppearance getAppearance() {
		try {
			return ReadOnlyWrap.readOnlyCopy((VacuumWorldAgentAppearance) super
					.getBody().getAppearance());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected AbstractAgent getBody() {
		return null;
	}
}
