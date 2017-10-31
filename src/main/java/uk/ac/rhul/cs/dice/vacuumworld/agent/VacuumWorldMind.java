package uk.ac.rhul.cs.dice.vacuumworld.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.perception.ActivePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.vacuumworld.actions.MoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.TurnAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldCommunicationAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.grid.Grid;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.TurnDirection;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridContent;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldGridPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perceptions.VacuumWorldMessageContent;
import uk.ac.rhul.cs.dice.vacuumworld.readonly.ReadOnlyWrap;

public abstract class VacuumWorldMind extends AbstractAgentMind {
    private Random rng;
    private static final int BASIC_ACTIONS_NUMBER = 5; //sense, turnL, turnR, move, speak. Clean is not included. This is for acting randomly.
    
    // **************************************************************** //
    // ************** DELEGATED PERCEIVE DECIDE EXECUTE *************** //
    // **************************************************************** //
    /*
     * The following methods make the perceive, decide, execute methods simpler,
     * these are the methods that you should override.
     */

    public VacuumWorldMind() {
	this.rng = new Random();
    }
    
    /**
     * The perceive method.
     * 
     * @param perception
     *            : a {@link VacuumWorldGridPerception} that is what this
     *            {@link Agent} perceived in the most recent cycle. To access its
     *            content call {@link VacuumWorldGridPerception#getPerception()}.
     *            The {@link VacuumWorldGridContent} is a 3x2 or 2x3 view of the
     *            {@link Grid}. See {@link VacuumWorldSensingAction} for details.
     * @param messages
     *            : a {@link Collection} of messages that this {@link Agent} has
     *            received in the most recent cycle.
     */
    public abstract void perceive(VacuumWorldGridPerception perception, Collection<CommunicationPerception<VacuumWorldMessageContent>> messages);

    /**
     * The decide method. This method should use any relevant {@link Perception} s
     * to decide which {@link Action} to take. An {@link Agent} may only decide on
     * one {@link Action} per cycle. That {@link Action} should be returned by this
     * method.
     * 
     * @return the action to attempt
     */
    public abstract VacuumWorldAction decide();

    /**
     * The execute method. This method receives the {@link Action} that was decided
     * upon in the {@link VacuumWorldMind#decide() decide()} method. The execute
     * method may be used to record which {@link Action}s have been attempted. To
     * execute the {@link Action} in the next cycle, return the {@link Action}. A
     * successfully executed {@link Action} will give a {@link Perception} which
     * will be received by the {@link VacuumWorldMind#perceive(Collection)
     * perceive(Collection)} method in the next cycle.
     * 
     * @param action
     *            : from {@link VacuumWorldMind#decide() decide()}
     * @return the {@link Action} to be executed in the next cycle.
     */
    public abstract VacuumWorldAction execute(VacuumWorldAction action);

    public VacuumWorldAction decideRandomAction() {
	switch(this.rng.nextInt(VacuumWorldMind.BASIC_ACTIONS_NUMBER)) {
	case 0:
	    return new VacuumWorldSensingAction();
	case 1:
	    return new MoveAction();
	case 2:
	    return new TurnAction(TurnDirection.LEFT);
	case 3:
	    return new TurnAction(TurnDirection.RIGHT);
	case 4:
	    return new VacuumWorldCommunicationAction("Hello, my id is: " + getId());
	default:
	    throw new UnsupportedOperationException();
	}
    }

    /**
     * Checks if this {@link Agent} can clean the given {@link Dirt}. </br>
     * {@link BodyColor#ORANGE Orange} {@link Agent}s can clean
     * {@link BodyColor#ORANGE Orange} {@link Dirt}, </br>
     * {@link BodyColor#GREEN Green} {@link Agent}s can clean {@link BodyColor#GREEN
     * Green} {@link Dirt}, </br>
     * {@link BodyColor#WHITE White} {@link Agent}s can clean
     * {@link BodyColor#ORANGE Orange} {@link Dirt} and {@link BodyColor#GREEN
     * Green} {@link Dirt}
     * 
     * @param dirt
     *            to check
     * @return <code>true</code> if this {@link Agent} is able to clean the given
     *         {@link Dirt}, <code>false</code> otherwise.
     */
    public boolean canCleanDirt(DirtAppearance dirt) {
	return getUnsafeAppearance().getColor().canClean(dirt.getColor());
    }

    private final VacuumWorldAgentAppearance getUnsafeAppearance() {
	return (VacuumWorldAgentAppearance) super.getBody().getAppearance();
    }

    protected final VacuumWorldAgentAppearance getAppearance() {
	return ReadOnlyWrap.readOnlyCopy((VacuumWorldAgentAppearance) super.getBody().getAppearance());
    }

    @Override
    protected final VacuumWorldAgent getBody() {
	ReadOnlyWrap.nicetry();
	
	return null;
    }

    // **************************************************************** //
    // ***************** REAL PERCEIVE DECIDE EXECUTE ***************** //
    // **************************************************************** //

    public final Perception<?> perceive(Collection<Perception<?>> perceptions) {
	VacuumWorldGridPerception vwpercepts = null;
	List<CommunicationPerception<VacuumWorldMessageContent>> messages = new ArrayList<>();
	
	for (Perception<?> p : perceptions) {
	    if (VacuumWorldGridPerception.class.isAssignableFrom(p.getClass())) {
		vwpercepts = (VacuumWorldGridPerception) p;
	    }
	    else if (CommunicationPerception.class.isAssignableFrom(p.getClass())) {
		// this will always be the case
		messages.add(validate((CommunicationPerception<?>) p));
	    }
	    else {
		vwpercepts = new VacuumWorldGridPerception((VacuumWorldGridContent) ((ActivePerception) p).get(VacuumWorldSensingAction.KEY));
	    }
	}
	
	perceive(vwpercepts, messages);
	
	return null;
    }

    private CommunicationPerception<VacuumWorldMessageContent> validate(CommunicationPerception<?> p) {
	if(p.getPerception() instanceof VacuumWorldMessageContent) {
	    return new CommunicationPerception<>((VacuumWorldMessageContent) p.getPerception());
	}
	else {
	    throw new IllegalArgumentException();
	}
    }

    @Override
    public final Action decide(Perception<?> perception) {
	return decide();
    }

    @Override
    public final Action execute(Action action) {
	return execute((VacuumWorldAction) action);
    }
}