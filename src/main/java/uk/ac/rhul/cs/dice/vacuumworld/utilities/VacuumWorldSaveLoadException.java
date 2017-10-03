package uk.ac.rhul.cs.dice.vacuumworld.utilities;

public class VacuumWorldSaveLoadException extends Exception {

    private static final long serialVersionUID = 8965609652831690115L;

    public VacuumWorldSaveLoadException(String message, Throwable cause) {
	super(message, cause);
    }

    public VacuumWorldSaveLoadException(String message) {
	super(message);
    }

    public VacuumWorldSaveLoadException(Throwable cause) {
	super(cause);
    }

}
