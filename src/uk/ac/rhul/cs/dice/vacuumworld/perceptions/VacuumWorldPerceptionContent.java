package uk.ac.rhul.cs.dice.vacuumworld.perceptions;

import java.util.Map;

import uk.ac.rhul.cs.dice.vacuumworld.misc.EntityContainer;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class VacuumWorldPerceptionContent {

	private Map<Position, EntityContainer> view;
	private Orientation orientation;

	public VacuumWorldPerceptionContent(Map<Position, EntityContainer> view,
			Orientation orientation) {
		this.view = view;
		this.orientation = orientation;
	}
}
