package uk.ac.rhul.cs.dice.vacuumworld.perceptions;

import uk.ac.rhul.cs.dice.starworlds.actions.speech.DefaultPayload;

public class VacuumWorldMessageContent extends DefaultPayload<String> {
    private static final long serialVersionUID = -6309901361814918823L;

    public VacuumWorldMessageContent(String message) {
	super(message);
    }
}