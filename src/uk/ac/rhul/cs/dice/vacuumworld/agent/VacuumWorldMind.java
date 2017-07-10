package uk.ac.rhul.cs.dice.vacuumworld.agent;

import java.util.ArrayList;
import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldCommunicationAction;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridContent;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldMessageContent;
import uk.ac.rhul.cs.dice.vacuumworld.readonly.ReadOnlyWrap;

public abstract class VacuumWorldMind extends AbstractAgentMind {

	// **************************************************************** //
	// ************** DELEGATED PERCEIVE DECIDE EXECUTE *************** //
	// **************************************************************** //
	/*
	 * The following methods make the perceive, decide, execute methods simpler,
	 * these are the methods that you should override.
	 */

	/**
	 * The perceive method.
	 * 
	 * @param perception
	 *            : a {@link VacuumWorldGridPerception} that is what this
	 *            {@link Agent} perceived in the most recent cycle. To access
	 *            its content call
	 *            {@link VacuumWorldGridPerception#getPerception()}. The
	 *            {@link VacuumWorldGridContent} is a 3x2 view of the
	 *            {@link Environment}.
	 * @param messages
	 *            : a {@link Collection} of messages that this {@link Agent} has
	 *            received.
	 */
	public abstract void perceive(
			VacuumWorldGridPerception perception,
			Collection<CommunicationPerception<VacuumWorldMessageContent>> messages);

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
	 * decided upon in the {@link VacuumWorldMind#decide() decide()} method. The
	 * execute method may be used to record which {@link Action}s have been
	 * attempted. To execute the {@link Action} in the next cycle, return the
	 * {@link Action}. A successfully executed {@link Action} will give a
	 * {@link Perception} which will be received by the
	 * {@link VacuumWorldMind#perceive(Collection) perceive(Collection)} method
	 * in the next cycle.
	 * 
	 * @param action
	 *            : from {@link VacuumWorldMind#decide() decide()}
	 * @return the {@link Action} to be executed in the next cycle.
	 */
	public abstract VacuumWorldAction execute(VacuumWorldAction action);

	private boolean check(Collection<Position> positions) {
		if (!positions.isEmpty()) {
			System.out.println(positions);
			System.out.println(this.getunsafeAppearance().getPosition());
			return positions.contains(this.getunsafeAppearance().getPosition());
		}
		return false;
	}

	private boolean check(Collection<Position> positions,
			Orientation orientation) {
		if (!positions.isEmpty()) {
			Position current = this.getunsafeAppearance().getPosition();
			Position search = new Position(current.getX() + orientation.getI(),
					current.getY() + orientation.getJ());
			return positions.contains(search);
		}
		return false;
	}

	private boolean checkDirt(VacuumWorldGridContent perception,
			Orientation orientation) {
		return check(perception.getDirtPositions(), orientation);
	}

	private boolean checkWall(VacuumWorldGridContent perception,
			Orientation orientation) {
		return check(perception.getWalls(), orientation);
	}

	private boolean checkAgent(VacuumWorldGridContent perception,
			Orientation orientation) {
		return check(perception.getAgentPositions(), orientation);
	}

	private boolean checkFilled(VacuumWorldGridContent perception,
			Orientation orientation) {
		return check(perception.getFilledPositions(), orientation);
	}

	public DirtAppearance getDirt(VacuumWorldGridContent perception) {
		return perception.getDirt(this.getunsafeAppearance().getPosition());
	}

	public DirtAppearance getFowardDirt(VacuumWorldGridContent perception) {
		Position current = this.getAppearance().getPosition();
		Orientation orientation = this.getAppearance().getOrientation();
		return perception.getDirt(new Position(current.getX()
				+ orientation.getI(), current.getY() + orientation.getJ()));
	}

	public DirtAppearance getLeftDirt(VacuumWorldGridContent perception) {
		Position current = this.getAppearance().getPosition();
		Orientation orientation = this.getAppearance().getOrientation()
				.getLeft();
		return perception.getDirt(new Position(current.getX()
				+ orientation.getI(), current.getY() + orientation.getJ()));
	}

	public DirtAppearance getRightDirt(VacuumWorldGridContent perception) {
		Position current = this.getAppearance().getPosition();
		Orientation orientation = this.getAppearance().getOrientation()
				.getRight();
		return perception.getDirt(new Position(current.getX()
				+ orientation.getI(), current.getY() + orientation.getJ()));
	}

	public VacuumWorldAgentAppearance getFowardAgent(
			VacuumWorldGridContent perception) {
		Position current = this.getAppearance().getPosition();
		Orientation orientation = this.getAppearance().getOrientation();
		return perception.getAgent(new Position(current.getX()
				+ orientation.getI(), current.getY() + orientation.getJ()));
	}

	public VacuumWorldAgentAppearance getLeftAgent(
			VacuumWorldGridContent perception) {
		Position current = this.getAppearance().getPosition();
		Orientation orientation = this.getAppearance().getOrientation()
				.getLeft();
		return perception.getAgent(new Position(current.getX()
				+ orientation.getI(), current.getY() + orientation.getJ()));
	}

	public VacuumWorldAgentAppearance getRightAgent(
			VacuumWorldGridContent perception) {
		Position current = this.getAppearance().getPosition();
		Orientation orientation = this.getAppearance().getOrientation()
				.getRight();
		return perception.getAgent(new Position(current.getX()
				+ orientation.getI(), current.getY() + orientation.getJ()));
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
	public boolean filledForward(VacuumWorldGridContent perception) {
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
	public boolean filledRight(VacuumWorldGridContent perception) {
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
	public boolean filledLeft(VacuumWorldGridContent perception) {
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
	public boolean agentForward(VacuumWorldGridContent perception) {
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
	public boolean agentRight(VacuumWorldGridContent perception) {
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
	public boolean agentLeft(VacuumWorldGridContent perception) {
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
	public boolean wallForward(VacuumWorldGridContent perception) {
		return checkWall(perception, this.getAppearance().getOrientation());
	}

	/**
	 * Check if there is a Wall immediately to the right of this {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a Wall immediately to the right, false otherwise
	 */
	public boolean wallRight(VacuumWorldGridContent perception) {
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
	public boolean wallLeft(VacuumWorldGridContent perception) {
		return checkWall(perception, this.getAppearance().getOrientation()
				.getLeft());
	}

	/**
	 * Checks if this {@link Agent} is currently on top of some {@link Dirt}.
	 * 
	 * @return true if this {@link Agent} is currently on top of some
	 *         {@link Dirt}, false otherwise
	 */
	public boolean onDirt(VacuumWorldGridContent perception) {
		return check(perception.getDirtPositions());
	}

	/**
	 * Check if this {@link Agent} is currently facing a {@link Dirt}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if the {@link Agent} is currently facing a {@link Dirt},
	 *         false otherwise
	 */
	public boolean dirtForward(VacuumWorldGridContent perception) {
		return checkDirt(perception, this.getAppearance().getOrientation());
	}

	/**
	 * Check if there is a {@link Dirt} immediately to the right of this
	 * {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a {@link Dirt} immediately to the right, false
	 *         otherwise
	 */
	public boolean dirtRight(VacuumWorldGridContent perception) {
		return checkDirt(perception, this.getAppearance().getOrientation()
				.getRight());
	}

	/**
	 * Check if there is a {@link Dirt} immediately to the left of this
	 * {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a {@link Dirt} immediately to the left, false
	 *         otherwise
	 */
	public boolean dirtLeft(VacuumWorldGridContent perception) {
		return checkDirt(perception, this.getAppearance().getOrientation()
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

	private final VacuumWorldAgentAppearance getunsafeAppearance() {
		return (VacuumWorldAgentAppearance) super.getBody().getAppearance();
	}

	protected final VacuumWorldAgentAppearance getAppearance() {
		try {
			return ReadOnlyWrap.readOnlyCopy((VacuumWorldAgentAppearance) super
					.getBody().getAppearance());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected VacuumWorldAgent getBody() {
		return (VacuumWorldAgent) super.getBody();
	}

	// **************************************************************** //
	// ***************** REAL PERCEIVE DECIDE EXECUTE ***************** //
	// **************************************************************** //
	@SuppressWarnings("unchecked")
	@Override
	public final Perception<?> perceive(Collection<Perception<?>> perceptions) {
		VacuumWorldGridPerception vwpercepts = null;
		Collection<CommunicationPerception<VacuumWorldMessageContent>> messages = new ArrayList<>();
		for (Perception<?> p : perceptions) {
			if (VacuumWorldGridPerception.class.isAssignableFrom(p.getClass())) {
				vwpercepts = (VacuumWorldGridPerception) p;
			} else if (CommunicationPerception.class.isAssignableFrom(p
					.getClass())) {
				// this will always be the case
				messages.add((CommunicationPerception<VacuumWorldMessageContent>) p);
			} else {
				vwpercepts = (VacuumWorldGridPerception) getVacuumWorldPerceptionsFromDefaultPerception((DefaultPerception<?>) p);
			}
		}
		perceive(vwpercepts, messages);
		return null;
	}

	private VacuumWorldGridPerception getVacuumWorldPerceptionsFromDefaultPerception(
			DefaultPerception<?> perception) {
		return new VacuumWorldGridPerception(
				(VacuumWorldGridContent) ((Pair<?, ?>) perception
						.getPerception()).getSecond());

	}

	@Override
	public final Action decide(Perception<?> perception) {
		return decide();
	}

	@Override
	public final Action execute(Action action) {
		if (VacuumWorldCommunicationAction.class.isAssignableFrom(action
				.getClass())) {
			VacuumWorldCommunicationAction vwca = (VacuumWorldCommunicationAction) action;
			return new CommunicationAction<String>(vwca.getPayload(),
					vwca.getRecipientsIds());
		}
		return action;
	}
}
