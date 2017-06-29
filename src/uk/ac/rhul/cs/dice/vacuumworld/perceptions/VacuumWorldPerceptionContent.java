package uk.ac.rhul.cs.dice.vacuumworld.perceptions;

import java.util.Map;

import uk.ac.rhul.cs.dice.vacuumworld.grid.tiles.Tile;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class VacuumWorldPerceptionContent {

	private Map<Position, Tile> view;

	public VacuumWorldPerceptionContent(Map<Position, Tile> view) {
		this.view = view;
	}

	@Override
	public String toString() {
		return view.toString();
	}
}
