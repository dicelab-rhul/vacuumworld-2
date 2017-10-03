package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import uk.ac.rhul.cs.dice.starworlds.appearances.PhysicalBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.utils.Utils;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class DirtAppearance extends PhysicalBodyAppearance
	implements VacuumWorldBodyAppearance<DirtAppearanceReadOnly> {

    private static final long serialVersionUID = 3721595240698765337L;

    private BodyColor color;
    private Position position;

    public DirtAppearance(BodyColor color) {
	super(Dirt.class);
	this.color = color;
    }

    public BodyColor getColor() {
	return color;
    }

    public void setColor(BodyColor color) {
	this.color = color;
    }

    @Override
    public Position getPosition() {
	return this.position;
    }

    @Override
    public void setPosition(Position position) {
	this.position = position;
    }

    @Override
    public Class<DirtAppearanceReadOnly> getReadOnlyClass() {
	return DirtAppearanceReadOnly.class;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((color == null) ? 0 : color.hashCode());
	result = prime * result + ((position == null) ? 0 : position.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (!Utils.equalsHelper(this, obj)) {
	    return false;
	}
	DirtAppearance other = (DirtAppearance) obj;
	
	if(other == null)
	    return false;
	
	if (color != other.color)
	    return false;
	if (position == null) {
	    if (other.position != null)
		return false;
	} else if (!position.equals(other.position))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return this.getBody().getSimpleName() + " : " + this.position + ", " + this.color;
    }
}