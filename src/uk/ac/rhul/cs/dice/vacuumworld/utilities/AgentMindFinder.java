package uk.ac.rhul.cs.dice.vacuumworld.utilities;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldMind;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.UserMindAnnotation;

public class AgentMindFinder {

	public static Collection<Class<?>> getAgentMinds()
			throws ClassNotFoundException, IOException {
		Collection<Class<?>> mindclasses = ClassFinder.getSubClasses(
				VacuumWorldMind.class, ClassFinder.findAllClasses());
		mindclasses.remove(VacuumWorldMind.class);
		return mindclasses;
	}

	public static Collection<Class<?>> getNonUserAgentMinds()
			throws ClassNotFoundException, IOException {
		Collection<Class<?>> minds = getAgentMinds();
		minds.removeAll(ClassFinder.getAnnotatedClasses(UserMindAnnotation.class, minds));
		return minds;
	}

	public static Collection<Class<?>> getNonUserAgentMinds(
			Collection<Class<?>> mindclasses) throws ClassNotFoundException,
			IOException {
		Collection<Class<?>> minds = new HashSet<>(mindclasses);
		minds.removeAll(ClassFinder.getAnnotatedClasses(UserMindAnnotation.class, minds));
		return minds;
	}
}
