package uk.ac.rhul.cs.dice.vacuumworld.utilities;

import java.io.IOException;
import java.util.Collection;

import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgentMind;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.UserMind;

public class AgentMindFinder {

	public static Collection<Class<?>> getAgentMinds()
			throws ClassNotFoundException, IOException {
		Collection<Class<?>> mindclasses = ClassFinder.getSubClasses(
				VacuumWorldAgentMind.class, ClassFinder.findAllClasses());
		mindclasses.remove(VacuumWorldAgentMind.class);
		return mindclasses;
	}

	public static Collection<Class<?>> getNonUserAgentMinds()
			throws ClassNotFoundException, IOException {
		Collection<Class<?>> mindclasses = ClassFinder.getAnnotatedClasses(
				UserMind.class, getAgentMinds());
		return mindclasses;
	}
}
