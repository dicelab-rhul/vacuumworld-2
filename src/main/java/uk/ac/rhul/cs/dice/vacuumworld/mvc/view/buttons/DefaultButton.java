package uk.ac.rhul.cs.dice.vacuumworld.mvc.view.buttons;

import java.awt.Color;
import java.awt.image.BufferedImage;

import uk.ac.rhul.cs.dice.vacuumworld.mvc.view.VacuumWorldView;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.ImageUtilities;

public class DefaultButton extends CustomButton {

	private static final long serialVersionUID = -4967158810170453134L;
	
	public static final String FILENAME_STARTBUTTON = "start_button";
	public static final String FILENAME_AVATARBUTTON = "avatar_button";
	
	public DefaultButton(String imgname, OnClick onclick) {
		super(loadButtonImage(imgname), onclick);
		this.setHover(ImageUtilities.getOverlayedImage(image, Color.BLACK, 0.2f));
		this.setPressed(ImageUtilities
				.getOverlayedImage(image, Color.BLACK, 0.4f));
	}

	private static BufferedImage loadButtonImage(String imgname) {
		return VacuumWorldView.IMGLOADER.loadImage(VacuumWorldView.IMGPATH + imgname
				+ VacuumWorldView.EXTENSION);
	}
}