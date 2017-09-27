package uk.ac.rhul.cs.dice.vacuumworld.mvc.view.buttons;


public class StartButton extends DefaultButton {

	private static final long serialVersionUID = -4967158810170453134L;
	public static final String BUTTONFILENAME = "start_button";

	public StartButton(OnClick onclick) {
		super(BUTTONFILENAME, onclick);
	}
}
