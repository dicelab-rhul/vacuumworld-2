package uk.ac.rhul.cs.dice.vacuumworld.misc;

public enum Orientation {

    NORTH, EAST, SOUTH, WEST;
    static {
	NORTH.setNeighbours(0, -1, WEST, EAST);
	EAST.setNeighbours(1, 0, NORTH, SOUTH);
	SOUTH.setNeighbours(0, 1, EAST, WEST);
	WEST.setNeighbours(-1, 0, SOUTH, NORTH);
    }

    private Orientation left;
    private Orientation right;
    private int i;
    private int j;

    private void setNeighbours(int i, int j, Orientation left, Orientation right) {
	this.left = left;
	this.right = right;
	this.i = i;
	this.j = j;
    }

    public Orientation getLeft() {
	return this.left;
    }

    public Orientation getRight() {
	return this.right;
    }

    public Integer getI() {
	return i;
    }

    public Integer getJ() {
	return j;
    }

    public static Orientation getLeft(Orientation orientation) {
	return orientation.left;
    }

    public static Orientation getRight(Orientation orientation) {
	return orientation.right;
    }
}
