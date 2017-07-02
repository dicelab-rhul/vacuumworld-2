package uk.ac.rhul.cs.dice.vacuumworld.agent.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import uk.ac.rhul.cs.dice.starworlds.entities.agents.Mind;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgentMind;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.AgentMindFinder;

/**
 * This class level annotation indicates that a given
 * {@link VacuumWorldAgentMind} is specifically an User mind. That is, the
 * {@link Mind} will appear in the possible {@link VacuumWorldAgentMind}s at the
 * {@link Mind} selection screen. See
 * {@link AgentMindFinder#getNonUserAgentMinds()}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UserMind {
}
