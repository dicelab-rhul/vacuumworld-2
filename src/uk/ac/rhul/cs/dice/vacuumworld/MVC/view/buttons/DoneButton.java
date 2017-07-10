package uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons;

import java.awt.event.MouseEvent;

public class DoneButton extends DefaultButton {

	private static final long serialVersionUID = -4967158810170453134L;
	public static final String BUTTONFILENAME = "done_button";

	public DoneButton(OnClick onclick) {
		super(BUTTONFILENAME, onclick);

	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.setFocusable(true);
		this.requestFocusInWindow();
		super.mousePressed(e);
	}
}