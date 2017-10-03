package uk.ac.rhul.cs.dice.vacuumworld.utilities;

/**
 * An exception that will be thrown if there is any failure in the
 * initialisation of VacuumWorld.
 * 
 * @author Ben Wilkins
 *
 */
public class VacuumWorldInitialisationException extends RuntimeException {

    private static final String DEFAULTMESSAGE = "Failed to initialise VacuumWorld";

    public VacuumWorldInitialisationException(String message, Throwable cause) {
	super(message, cause);
    }

    public VacuumWorldInitialisationException(String message) {
	super(message);
    }

    public VacuumWorldInitialisationException(Throwable cause) {
	super(DEFAULTMESSAGE, cause);
    }

    private static final long serialVersionUID = 2866986549324463147L;

}
