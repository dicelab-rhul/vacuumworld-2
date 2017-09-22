package uk.ac.rhul.cs.dice.vacuumworld.agent.user;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.Mind;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldMind;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.AgentMindFinder;

/**
 * This class level {@link Annotation} indicates that a given
 * {@link VacuumWorldMind} is specifically an User {@link Mind}. That is, the
 * {@link Mind} will not appear as a possible {@link VacuumWorldMind}s at the
 * {@link Mind} selection screen. See
 * {@link AgentMindFinder#getNonUserAgentMinds()}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UserMindAnnotation {
}
