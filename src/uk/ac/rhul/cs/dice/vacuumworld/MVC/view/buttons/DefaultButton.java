package uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons;

import java.awt.Color;
import java.awt.image.BufferedImage;

import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.VacuumWorldView;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.ImageUtilities;

public class DefaultButton extends CustomButton {

	private static final long serialVersionUID = -4967158810170453134L;

	public DefaultButton(String imgname, OnClick onclick) {
		super(loadButtonImage(imgname), onclick);
		this.setHover(ImageUtilities.getOverlayedImage(image, Color.BLACK, 0.2f));
		this.setPressed(ImageUtilities
				.getOverlayedImage(image, Color.BLACK, 0.4f));
	}

	private static BufferedImage loadButtonImage(String imgname) {
		return VacuumWorldView.loadImage(VacuumWorldView.PATH + imgname
				+ VacuumWorldView.EXTENSION);
	}
}