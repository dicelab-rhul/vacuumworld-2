package uk.ac.rhul.cs.dice.vacuumworld.perceptions;

import uk.ac.rhul.cs.dice.starworlds.actions.speech.Payload;

public class VacuumWorldMessageContent implements Payload<String> {

	private static final long serialVersionUID = -6309901361814918823L;

	private String message;

	public VacuumWorldMessageContent(String message) {
		this.message = message;
	}

	@Override
	public void setPayload(String message) {
		this.message = message;
	}

	@Override
	public String getPayload() {
		return message;
	}

}
