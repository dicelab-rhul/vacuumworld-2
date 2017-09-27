package uk.ac.rhul.cs.dice.vacuumworld.utilities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageUtilities {

	private ImageUtilities() {
		super();
	}

	public static BufferedImage getOverlayedImage(BufferedImage image,
			Color overlay, float strength) {
		BufferedImage result = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D gbi = result.createGraphics();
		gbi.drawImage(image, 0, 0, null);
		gbi.setColor(overlay);
		gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				strength));
		gbi.fillRect(0, 0, image.getWidth(), image.getHeight());
		return result;
	}

}
