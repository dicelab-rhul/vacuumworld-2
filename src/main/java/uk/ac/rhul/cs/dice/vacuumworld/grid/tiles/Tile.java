package uk.ac.rhul.cs.dice.vacuumworld.grid.tiles;

import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;

public interface Tile {

    public boolean isWall();

    public boolean containsAgent();

    public boolean containsDirt();

    public VacuumWorldAgentAppearance getAgent();

    public void setAgent(VacuumWorldAgentAppearance agent);

    public DirtAppearance getDirt();

    public void setDirt(DirtAppearance dirt);

}
