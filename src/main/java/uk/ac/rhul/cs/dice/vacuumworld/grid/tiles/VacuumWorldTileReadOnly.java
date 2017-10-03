package uk.ac.rhul.cs.dice.vacuumworld.grid.tiles;

import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.readonly.ReadOnlyWrap;

public class VacuumWorldTileReadOnly extends VacuumWorldTile {

	private static final long serialVersionUID = -5962543062760277746L;

	public VacuumWorldTileReadOnly(VacuumWorldTile tile) {
		super(ReadOnlyWrap.readOnlyCopy(tile.getAgent()), ReadOnlyWrap
				.readOnlyCopy(tile.getDirt()));
	}

	@Override
	public void setAgent(VacuumWorldAgentAppearance agent) {
		ReadOnlyWrap.nicetry(this.getClass().getSimpleName() + " agent");
	}

	@Override
	public void setDirt(DirtAppearance dirt) {
		ReadOnlyWrap.nicetry(this.getClass().getSimpleName() + " dirt");
	}
}
