package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.readonly.ReadOnlyWrap;

public class DirtAppearanceReadOnly extends DirtAppearance {

	private static final long serialVersionUID = 1878549218843678480L;

	public DirtAppearanceReadOnly(DirtAppearance appearance) {
		super(appearance.getColor());
		super.setId(appearance.getId());
		super.setPosition(ReadOnlyWrap.readOnlyCopy(appearance.getPosition()));
	}

	@Override
	public void setColor(BodyColor color) {
		ReadOnlyWrap.nicetry();
	}

	@Override
	public void setId(String id) {
		ReadOnlyWrap.nicetry();
	}

	@Override
	public void setPosition(Position position) {
		ReadOnlyWrap.nicetry();
	}
}
