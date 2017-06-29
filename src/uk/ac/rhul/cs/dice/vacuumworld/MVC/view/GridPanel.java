package uk.ac.rhul.cs.dice.vacuumworld.MVC.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class GridPanel extends JPanel {

	private static final long serialVersionUID = 7892181452844860444L;
	protected int griddimension;

	public GridPanel(int griddimension) {
		this.griddimension = griddimension;
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	public Dimension getTileDimension() {
		return new Dimension(this.getWidth() / griddimension, this.getHeight()
				/ griddimension);
	}

	public Point snapSelectionBox(Point position) {
		if (position.getY() >= this.getHeight() - 4
				|| position.getX() >= this.getWidth()) {
			return new Point(-Integer.MAX_VALUE, -Integer.MAX_VALUE);
		}
		int sdx = this.getWidth() / griddimension;
		int sdy = this.getHeight() / griddimension;
		return new Point(position.x - position.x % sdx, position.y - position.y
				% sdy);
	}

	public Position getGridPosition(Point position) {
		int sdx = this.getWidth() / griddimension;
		int sdy = this.getHeight() / griddimension;
		Position p = new Position((position.x - position.x % sdx) / sdx,
				(position.y - position.y % sdy) / sdy);
		return (positionInBounds(p)) ? p : null;
	}

	public boolean positionInBounds(Position position) {
		return !(position.getX() < 0 || position.getX() >= griddimension
				|| position.getY() < 0 || position.getY() >= griddimension);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		drawGrid(g2, griddimension);
	}

	protected void drawDirt(Graphics g, Position position, BodyColor color) {
		BufferedImage img = VacuumWorldView.DIRTIMAGES.get(color);
		drawBody(g, img, position);
	}

	protected void drawAgent(Graphics g, Position position, BodyColor color,
			Orientation orientation) {
		BufferedImage img = VacuumWorldView.AGENTIMAGES.get(color).get(
				orientation);
		drawBody(g, img, position);
	}

	protected void drawBody(Graphics g, BufferedImage img, Position position) {
		int incw = this.getWidth() / griddimension;
		int inch = this.getHeight() / griddimension;
		g.drawImage(img, position.getX() * incw, position.getY() * inch,
				(position.getX() + 1) * incw, (position.getY() + 1) * inch, 0,
				0, img.getWidth() - 2, img.getHeight() - 2, null);
	}

	protected void drawGrid(Graphics g, int dim) {
		g.setColor(Color.BLACK);
		int incw = this.getWidth() / dim;
		int inch = this.getHeight() / dim;
		for (int i = 0; i < dim; i++) {
			g.drawLine(i * incw, 0, i * incw, this.getHeight());
			g.drawLine(0, i * inch, this.getWidth(), i * inch);
		}
	}

}
