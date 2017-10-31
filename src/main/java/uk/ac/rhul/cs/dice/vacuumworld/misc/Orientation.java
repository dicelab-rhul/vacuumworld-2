package uk.ac.rhul.cs.dice.vacuumworld.misc;

public enum Orientation {
    NORTH, EAST, SOUTH, WEST;
    
    //TODO remove this --> use the API in its place
    static {
	NORTH.setNeighbours(0, -1, WEST, EAST);
	EAST.setNeighbours(1, 0, NORTH, SOUTH);
	SOUTH.setNeighbours(0, 1, EAST, WEST);
	WEST.setNeighbours(-1, 0, SOUTH, NORTH);
    }

    private Orientation left;
    private Orientation right;
    private int i; //TODO remove this --> use the API in its place
    private int j; //TODO remove this --> use the API in its place

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
	return this.i;
    }

    public Integer getJ() {
	return this.j;
    }
}