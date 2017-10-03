package uk.ac.rhul.cs.dice.vacuumworld.utilities;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldMind;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.UserMindAnnotation;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.ClassFinder.ClassFinderException;

public class AgentMindFinder {

    private static final AgentMindFinder INSTANCE = new AgentMindFinder();

    private AgentMindFinder() {
    }

    public static Collection<Class<?>> getAgentMinds() throws ClassFinderException {
	Collection<Class<?>> mindclasses = ClassFinder.getSubClasses(VacuumWorldMind.class, ClassFinder.findAllClasses());
	
	return mindclasses.stream().filter(elm -> !elm.equals(VacuumWorldMind.class)).collect(Collectors.toList());

    }

    public static Collection<Class<?>> getNonUserAgentMinds() throws ClassFinderException {
	Collection<Class<?>> minds = getAgentMinds();
	minds.removeAll(ClassFinder.getAnnotatedClasses(UserMindAnnotation.class, minds));
	return minds;
    }

    public static Collection<Class<?>> getNonUserAgentMinds(Collection<Class<?>> mindclasses) {
	Collection<Class<?>> minds = new HashSet<>(mindclasses);
	minds.removeAll(ClassFinder.getAnnotatedClasses(UserMindAnnotation.class, minds));
	return minds;
    }

    public static AgentMindFinder getInstance() {
	return INSTANCE;
    }
}