package uk.ac.rhul.cs.dice.vacuumworld.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.perception.ActivePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.grid.Grid;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridContent;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldMessageContent;
import uk.ac.rhul.cs.dice.vacuumworld.readonly.ReadOnlyWrap;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.SuperSimpleFormatter;

public abstract class VacuumWorldMind extends AbstractAgentMind {

	public static final Logger LOGGER = Logger.getLogger("MindLogger");
	static {
		// set up logger
		for (Handler handler : LOGGER.getParent().getHandlers()) {
			LOGGER.getParent().removeHandler(handler);
		}
		ConsoleHandler handler = new ConsoleHandler();
		LOGGER.addHandler(handler);
		SuperSimpleFormatter formatter = new SuperSimpleFormatter();
		handler.setFormatter(formatter);
	}

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
	 *            {@link VacuumWorldGridContent} is a 3x2 or 2x3 view of the
	 *            {@link Grid}. See {@link VacuumWorldSensingAction} for
	 *            details.
	 * @param messages
	 *            : a {@link Collection} of messages that this {@link Agent} has
	 *            received in the most recent cycle.
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

	public static DirtAppearance getDirtOn(VacuumWorldGridContent perception) {
		return perception.getDirt(perception.getSelf().getPosition());
	}

	public static DirtAppearance getDirtFoward(VacuumWorldGridContent perception) {
		Position current = perception.getSelf().getPosition();
		Orientation orientation = perception.getSelf().getOrientation();
		return perception.getDirt(new Position(current.getX()
				+ orientation.getI(), current.getY() + orientation.getJ()));
	}

	public static DirtAppearance getDirtLeft(VacuumWorldGridContent perception) {
		Position current = perception.getSelf().getPosition();
		Orientation orientation = perception.getSelf().getOrientation()
				.getLeft();
		return perception.getDirt(new Position(current.getX()
				+ orientation.getI(), current.getY() + orientation.getJ()));
	}

	public static DirtAppearance getDirtRight(VacuumWorldGridContent perception) {
		Position current = perception.getSelf().getPosition();
		Orientation orientation = perception.getSelf().getOrientation()
				.getRight();
		return perception.getDirt(new Position(current.getX()
				+ orientation.getI(), current.getY() + orientation.getJ()));
	}

	public static VacuumWorldAgentAppearance getAgentFoward(
			VacuumWorldGridContent perception) {
		Position current = perception.getSelf().getPosition();
		Orientation orientation = perception.getSelf().getOrientation();
		return perception.getAgent(new Position(current.getX()
				+ orientation.getI(), current.getY() + orientation.getJ()));
	}

	public static VacuumWorldAgentAppearance getAgentLeft(
			VacuumWorldGridContent perception) {
		Position current = perception.getSelf().getPosition();
		Orientation orientation = perception.getSelf().getOrientation()
				.getLeft();
		return perception.getAgent(new Position(current.getX()
				+ orientation.getI(), current.getY() + orientation.getJ()));
	}

	public static VacuumWorldAgentAppearance getAgentRight(
			VacuumWorldGridContent perception) {
		Position current = perception.getSelf().getPosition();
		Orientation orientation = perception.getSelf().getOrientation()
				.getRight();
		return perception.getAgent(new Position(current.getX()
				+ orientation.getI(), current.getY() + orientation.getJ()));
	}

	/**
	 * Check if the perceiving {@link Agent} is currently facing another
	 * {@link Agent} or a Wall.
	 * 
	 * @param perception
	 *            to check
	 * @return true if the {@link Agent} is currently facing a Wall, false
	 *         otherwise
	 */
	public static boolean isFilledForward(VacuumWorldGridContent perception) {
		return checkFilled(perception, perception.getSelf().getOrientation());
	}

	/**
	 * Check if there is a {@link Agent} or a Wall immediately to the right of
	 * the perceiving {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a {@link Agent} or a Wall immediately to the
	 *         right, false otherwise
	 */
	public static boolean isFilledRight(VacuumWorldGridContent perception) {
		return checkFilled(perception, perception.getSelf().getOrientation()
				.getRight());
	}

	/**
	 * Check if there is a {@link Agent} or a Wall immediately to the left of
	 * the perceiving {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a {@link Agent} or a Wall immediately to the
	 *         left, false otherwise
	 */
	public static boolean isFilledLeft(VacuumWorldGridContent perception) {
		return checkFilled(perception, perception.getSelf().getOrientation()
				.getLeft());
	}

	/**
	 * Check if the perceiving {@link Agent} is currently facing another
	 * {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if the {@link Agent} is currently another {@link Agent},
	 *         false otherwise
	 */
	public static boolean isAgentForward(VacuumWorldGridContent perception) {
		return checkAgent(perception, perception.getSelf().getOrientation());
	}

	/**
	 * Check if there is a {@link Agent} immediately to the right of the
	 * perceiving {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a {@link Agent} immediately to the right, false
	 *         otherwise
	 */
	public static boolean isAgentRight(VacuumWorldGridContent perception) {
		return checkAgent(perception, perception.getSelf().getOrientation()
				.getRight());
	}

	/**
	 * Check if there is a {@link Agent} immediately to the left of the
	 * perceiving {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a {@link Agent} immediately to the left, false
	 *         otherwise
	 */
	public static boolean isAgentLeft(VacuumWorldGridContent perception) {
		return checkAgent(perception, perception.getSelf().getOrientation()
				.getLeft());
	}

	/**
	 * Check if the perceiving {@link Agent} is currently facing a Wall.
	 * 
	 * @param perception
	 *            to check
	 * @return true if the {@link Agent} is currently facing a Wall, false
	 *         otherwise
	 */
	public static boolean isWallFoward(VacuumWorldGridContent perception) {
		return checkWall(perception, perception.getSelf().getOrientation());
	}

	/**
	 * Check if there is a Wall immediately to the right of the perceiving
	 * {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a Wall immediately to the right, false otherwise
	 */
	public static boolean isWallRight(VacuumWorldGridContent perception) {
		return checkWall(perception, perception.getSelf().getOrientation()
				.getRight());
	}

	/**
	 * Check if there is a Wall immediately to the left of the perceiving
	 * {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a Wall immediately to the left, false otherwise
	 */
	public static boolean isWallLeft(VacuumWorldGridContent perception) {
		return checkWall(perception, perception.getSelf().getOrientation()
				.getLeft());
	}

	/**
	 * Checks if the perceiving {@link Agent} is currently on top of some
	 * {@link Dirt}.
	 * 
	 * @return true if this {@link Agent} is currently on top of some
	 *         {@link Dirt}, false otherwise
	 */
	public static boolean isDirtOn(VacuumWorldGridContent perception) {
		return check(perception.getDirtPositions(), perception.getSelf()
				.getPosition());
	}

	/**
	 * Check if the perceiving {@link Agent} is currently facing some
	 * {@link Dirt}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if the {@link Agent} is currently facing a {@link Dirt},
	 *         false otherwise
	 */
	public static boolean isDirtFoward(VacuumWorldGridContent perception) {
		return checkDirt(perception, perception.getSelf().getOrientation());
	}

	/**
	 * Check if there is a {@link Dirt} immediately to the right of the
	 * perceiving {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a {@link Dirt} immediately to the right, false
	 *         otherwise
	 */
	public static boolean isDirtRight(VacuumWorldGridContent perception) {
		return checkDirt(perception, perception.getSelf().getOrientation()
				.getRight());
	}

	/**
	 * Check if there is a {@link Dirt} immediately to the left of the
	 * perceiving {@link Agent}.
	 * 
	 * @param perception
	 *            to check
	 * @return true if there is a {@link Dirt} immediately to the left, false
	 *         otherwise
	 */
	public static boolean isDirtLeft(VacuumWorldGridContent perception) {
		return checkDirt(perception, perception.getSelf().getOrientation()
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
		return getUnsafeAppearance().getColor().canClean(dirt.getColor());
	}

	private final VacuumWorldAgentAppearance getUnsafeAppearance() {
		return (VacuumWorldAgentAppearance) super.getBody().getAppearance();
	}

	protected final VacuumWorldAgentAppearance getAppearance() {
		return ReadOnlyWrap.readOnlyCopy((VacuumWorldAgentAppearance) super
				.getBody().getAppearance());
	}

	@Override
	protected final VacuumWorldAgent getBody() {
		ReadOnlyWrap.nicetry();
		return null;
	}

	// **************************************************************** //
	// ***************** REAL PERCEIVE DECIDE EXECUTE ***************** //
	// **************************************************************** //
	@SuppressWarnings("unchecked")
	//TODO this is a bad practice and can lead to bugs. I.e., solve the "unchecked" part.
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
				vwpercepts = new VacuumWorldGridPerception(
						(VacuumWorldGridContent) ((ActivePerception) p)
								.get(VacuumWorldSensingAction.KEY));
			}
		}
		perceive(vwpercepts, messages);
		return null;
	}

	@Override
	public final Action decide(Perception<?> perception) {
		return decide();
	}

	@Override
	public final Action execute(Action action) {
		return execute((VacuumWorldAction) action);
	}

	private static boolean check(Collection<Position> positions,
			Position current) {
		if (!positions.isEmpty()) {
			return positions.contains(current);
		}
		return false;
	}

	private static boolean check(Collection<Position> positions,
			Orientation orientation, Position current) {
		if (!positions.isEmpty()) {
			Position search = new Position(current.getX() + orientation.getI(),
					current.getY() + orientation.getJ());
			return positions.contains(search);
		}
		return false;
	}

	private static boolean checkDirt(VacuumWorldGridContent perception,
			Orientation orientation) {
		return check(perception.getDirtPositions(), orientation, perception
				.getSelf().getPosition());
	}

	private static boolean checkWall(VacuumWorldGridContent perception,
			Orientation orientation) {
		return check(perception.getWallPositions(), orientation, perception
				.getSelf().getPosition());
	}

	private static boolean checkAgent(VacuumWorldGridContent perception,
			Orientation orientation) {
		return check(perception.getAgentPositions(), orientation, perception
				.getSelf().getPosition());
	}

    private static boolean checkFilled(VacuumWorldGridContent perception,
			Orientation orientation) {
		return check(perception.getFilledPositions(), orientation, perception
				.getSelf().getPosition());
	}
}
