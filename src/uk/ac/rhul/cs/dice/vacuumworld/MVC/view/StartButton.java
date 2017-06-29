package uk.ac.rhul.cs.dice.vacuumworld.MVC.view;

import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class StartButton extends CustomButton {

	private static final long serialVersionUID = -4967158810170453134L;

	public StartButton() {
		super(loadStartButtonImage());
		this.setHover(CustomButton.getOverlayedImage(image, Color.BLACK, 0.2f));
		this.setPressed(CustomButton.getOverlayedImage(image, Color.RED, 0.2f));
	}

	public static BufferedImage loadStartButtonImage() {
		return VacuumWorldView.loadImage(VacuumWorldView.path + "start_button"
				+ VacuumWorldView.extension);
	}
}
