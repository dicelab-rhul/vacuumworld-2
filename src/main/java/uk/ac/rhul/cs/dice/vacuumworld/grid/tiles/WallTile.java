package uk.ac.rhul.cs.dice.vacuumworld.grid.tiles;

import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;

public class WallTile implements Tile {

    @Override
    public boolean isWall() {
	return true;
    }

    @Override
    public boolean containsAgent() {
	return false;
    }

    @Override
    public boolean containsDirt() {
	return false;
    }

    @Override
    public VacuumWorldAgentAppearance getAgent() {
	return null;
    }

    @Override
    public void setAgent(VacuumWorldAgentAppearance agent) {
	// unused, a wall tile cannot contain an agent
    }

    @Override
    public DirtAppearance getDirt() {
	return null;
    }

    @Override
    public void setDirt(DirtAppearance dirt) {
	// unused, a wall tile cannot contain dirt
    }

    @Override
    public String toString() {
	return this.getClass().getSimpleName();
    }
}
