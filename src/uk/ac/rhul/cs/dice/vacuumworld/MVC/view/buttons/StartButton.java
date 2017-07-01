package uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons;

import java.awt.Color;
import java.awt.image.BufferedImage;

import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.VacuumWorldView;

public abstract class StartButton extends CustomButton {

	private static final long serialVersionUID = -4967158810170453134L;
	public static final String STARTBUTTONFILENAME = "start_button";

	public StartButton() {
		super(loadStartButtonImage(), null);
		this.setHover(CustomButton.getOverlayedImage(image, Color.BLACK, 0.2f));
		this.setPressed(CustomButton.getOverlayedImage(image, Color.RED, 0.2f));
	}

	public static BufferedImage loadStartButtonImage() {
		return VacuumWorldView.loadImage(VacuumWorldView.PATH
				+ STARTBUTTONFILENAME + VacuumWorldView.EXTENSION);
	}
}
