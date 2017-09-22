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
	protected Integer gridDimension;

	public GridPanel(Integer gridDimension) {
		this.gridDimension = gridDimension;
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	public Dimension getTileDimension() {
		return new Dimension(this.getWidth() / gridDimension, this.getHeight()
				/ gridDimension);
	}

	public Point snapSelectionBox(Point position) {
		if (position.getY() >= this.getHeight() - 4
				|| position.getX() >= this.getWidth()) {
			return new Point(-Integer.MAX_VALUE, -Integer.MAX_VALUE);
		}
		double sdx = (double) this.getWidth() / gridDimension;
		double sdy = (double) this.getHeight() / gridDimension;

		Point p = new Point((int) (position.x - position.x % sdx),
				(int) (position.y - position.y % sdy));
		return p;
	}

	public Position getGridPosition(Point position) {
		double sdx = (double) this.getWidth() / gridDimension;
		double sdy = (double) this.getHeight() / gridDimension;
		double x = position.x - position.x % sdx;
		double y = position.y - position.y % sdy;
		Position p = new Position((int) (x / sdx), (int) (y / sdy));

		return (positionInBounds(p)) ? p : null;
	}

	public boolean positionInBounds(Position position) {
		return !(position.getX() < 0 || position.getX() >= gridDimension
				|| position.getY() < 0 || position.getY() >= gridDimension);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		drawGrid(g2, gridDimension);
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
		double incw = (double) this.getWidth() / gridDimension;
		double inch = (double) this.getHeight() / gridDimension;
		g.drawImage(img, (int) (position.getX() * incw),
				(int) (position.getY() * inch),
				(int) ((position.getX() + 1) * incw),
				(int) ((position.getY() + 1) * inch), 0, 0, img.getWidth() - 2,
				img.getHeight() - 2, null);
	}

	protected void drawGrid(Graphics g, int dim) {
		g.setColor(Color.BLACK);
		double incw = (double) this.getWidth() / dim;
		double inch = (double) this.getHeight() / dim;
		for (int i = 0; i < dim; i++) {
			g.drawLine((int) (i * incw), 0, (int) (i * incw), this.getHeight());
			g.drawLine(0, (int) (i * inch), this.getWidth(), (int) (i * inch));
		}
	}

	public Integer getGriddimension() {
		return gridDimension;
	}

	public void setGridDimension(Integer gridDimension) {
		this.gridDimension = gridDimension;
	}
}
