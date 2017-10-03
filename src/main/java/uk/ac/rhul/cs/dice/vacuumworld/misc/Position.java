package uk.ac.rhul.cs.dice.vacuumworld.misc;

import java.io.Serializable;

import uk.ac.rhul.cs.dice.starworlds.utils.Utils;
import uk.ac.rhul.cs.dice.vacuumworld.readonly.ReadOnlyInterface;

public class Position implements ReadOnlyInterface<PositionReadOnly>, Serializable {
    private static final long serialVersionUID = -5137194020124679746L;
    private Integer x;
    private Integer y;

    public Position() {
	super();
    }

    public Position(Integer x, Integer y) {
	super();
	this.x = x;
	this.y = y;
    }

    public Integer getX() {
	return x;
    }

    public void setX(Integer x) {
	this.x = x;
    }

    public Integer getY() {
	return y;
    }

    public void setY(Integer y) {
	this.y = y;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((x == null) ? 0 : x.hashCode());
	result = prime * result + ((y == null) ? 0 : y.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (Utils.equalsHelper(this, obj)) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}

	// Do not change the type check on this.
	if (!Position.class.isAssignableFrom(obj.getClass())) {
	    return false;
	}
	Position other = (Position) obj;
	if (x == null) {
	    if (other.x != null)
		return false;
	} else if (!x.equals(other.x))
	    return false;
	if (y == null) {
	    if (other.y != null)
		return false;
	} else if (!y.equals(other.y))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "(" + x + "," + y + ")";
    }

    @Override
    public Class<PositionReadOnly> getReadOnlyClass() {
	return PositionReadOnly.class;
    }
}