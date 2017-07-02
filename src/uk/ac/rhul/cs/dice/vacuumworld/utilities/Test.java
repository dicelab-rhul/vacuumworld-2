package uk.ac.rhul.cs.dice.vacuumworld.utilities;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException,
			IOException {
		AgentMindFinder.getNonUserAgentMinds().forEach(
				(c) -> System.out.println(c));
	}
}
