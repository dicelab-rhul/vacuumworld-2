package uk.ac.rhul.cs.dice.vacuumworld.misc;

public enum Orientation {

	NORTH, EAST, SOUTH, WEST;
	static {
		NORTH.setNeighbours(WEST, EAST);
		EAST.setNeighbours(NORTH, SOUTH);
		SOUTH.setNeighbours(EAST, WEST);
		WEST.setNeighbours(SOUTH, NORTH);
	}

	private Orientation left;
	private Orientation right;

	private void setNeighbours(Orientation left, Orientation right) {
		this.left = left;
		this.right = right;
	}

	public Orientation getLeft() {
		return this.left;
	}

	public Orientation getRight() {
		return this.right;
	}
}
