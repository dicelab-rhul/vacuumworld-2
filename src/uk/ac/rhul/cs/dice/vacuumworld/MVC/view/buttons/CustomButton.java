package uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public abstract class CustomButton extends JComponent implements MouseListener,
		Clickable {

	private static final long serialVersionUID = -4788283083451918681L;

	protected OnClick onclick;
	protected BufferedImage image;
	protected BufferedImage hover;
	protected BufferedImage pressed;

	protected Boolean isHovering = false;
	protected Boolean isPressed = false;

	public CustomButton(BufferedImage image, OnClick onclick) {
		super();
		this.onclick = onclick;
		this.image = image;
		this.hover = image;
		this.pressed = image;
		enableInputMethods(true);
		addMouseListener(this);
	}

	public CustomButton(BufferedImage image, BufferedImage hover,
			BufferedImage pressed, OnClick onclick) {
		super();
		this.onclick = onclick;
		this.image = image;
		this.hover = hover;
		this.pressed = pressed;
		enableInputMethods(true);
		addMouseListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		this.drawImage(g2, (isHovering || isPressed) ? ((isPressed) ? pressed
				: hover) : image);
	}

	protected void drawImage(Graphics2D g2, BufferedImage image) {
		g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.onclick.onClick(this, e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.isHovering = true;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.isHovering = false;
		this.isPressed = false;
		repaint();
	}

	private boolean pressOnce = true;

	@Override
	public void mousePressed(MouseEvent e) {
		this.isPressed = true;
		if (pressOnce) {
			this.repaint();
			pressOnce = false;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.isPressed = false;
		this.pressOnce = true;
		this.repaint();
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getPressed() {
		return pressed;
	}

	public void setPressed(BufferedImage pressed) {
		this.pressed = pressed;
	}

	public BufferedImage getHover() {
		return hover;
	}

	public void setHover(BufferedImage hover) {
		this.hover = hover;
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
