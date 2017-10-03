package uk.ac.rhul.cs.dice.vacuumworld.misc;

public enum BodyColor {

    WHITE, GREEN, ORANGE, USER, AVATAR;

    @Override
    public String toString() {
	return super.toString();
    }

    public boolean canClean(BodyColor dirtcolor) {
	return this == dirtcolor || this == WHITE;
    }

    public static BodyColor[] getRealImageValues() {
	return new BodyColor[] { WHITE, GREEN, ORANGE, USER };
    }

    public static BodyColor getUserColor() {
	return USER;
    }

    public static BodyColor getAvatarColor() {
	return AVATAR;
    }

    public static BodyColor[] getAgentColors() {
	return BodyColor.values();
    }

    public static BodyColor[] getDirtColors() {
	return new BodyColor[] { BodyColor.GREEN, BodyColor.ORANGE };
    }
}
