package uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.VacuumWorldView;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.ImageUtilities;

public class RotateButton extends CustomButton {

	private static final long serialVersionUID = 8896726750341550347L;
	private static BufferedImage IMG = ImageUtilities.getOverlayedImage(
			VacuumWorldView.IMGLOADER.loadImage(VacuumWorldView.IMGPATH + "rotate_button"
					+ VacuumWorldView.EXTENSION), Color.BLACK, 1f);
	private static BufferedImage HOVER = ImageUtilities.getOverlayedImage(IMG,
			Color.WHITE, 0.3f);

	private VacuumWorldAgentAppearance current;
	private boolean left = true;

	public RotateButton(OnClick onclick) {
		super(IMG, HOVER, HOVER, onclick);
		this.setLocation(Integer.MAX_VALUE, Integer.MAX_VALUE);
		this.setFocusable(true);
	}

	@Override
	protected void drawImage(Graphics2D g2, BufferedImage image) {
		if (left) {
			super.drawImage(g2, image);
		} else {
			g2.drawImage(image, 0 + this.getWidth(), 0, -this.getWidth(),
					this.getHeight(), null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 1) {
			this.setVisible(false);
			this.current = null;
		} else {
			super.mouseClicked(e);
		}
	}

	public void rotateLeft() {
		if (this.current != null) {
			this.current
					.setOrientation(this.current.getOrientation().getLeft());
			this.left = true;
		}
	}

	public void rotateRight() {
		if (this.current != null) {
			this.current.setOrientation(this.current.getOrientation()
					.getRight());
			this.left = false;
		}
	}

	public VacuumWorldAgentAppearance getCurrent() {
		return current;
	}

	public void setCurrent(VacuumWorldAgentAppearance current) {
		this.current = current;
	}

	public void clear() {
		this.current = null;
	}

}
