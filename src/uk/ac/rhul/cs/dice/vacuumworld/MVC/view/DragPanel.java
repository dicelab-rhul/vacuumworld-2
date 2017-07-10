package uk.ac.rhul.cs.dice.vacuumworld.MVC.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DragPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	protected BufferedImage image;

	public DragPanel() {
		this.setOpaque(false);
	}

	public void updateImage(BufferedImage image) {
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			drawImage(g2, image);
		}
	}

	private void drawImage(Graphics2D g2, BufferedImage image) {
		g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), 0, 0,
				image.getWidth() - 2, image.getHeight() - 2, null);
	}
}
