package uk.ac.rhul.cs.dice.vacuumworld.MVC.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldAmbient;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldUniverse;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class VacuumWorldViewContent extends GridPanel {

	private static final long serialVersionUID = -5008822076025428472L;
	private VacuumWorldAmbient model;

	public VacuumWorldViewContent(VacuumWorldUniverse model) {
		super(model.getState().getDimension());
		this.model = model.getState();
		this.setBackground(Color.WHITE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		drawDirt(g2, griddimension);
		drawAgents(g2, griddimension);
	}

	private void drawDirt(Graphics g, int dim) {
		for (PassiveBody b : model.getPassiveBodies()) {
			DirtAppearance appearance = (DirtAppearance) b.getAppearance();
			Position position = appearance.getPosition();
			BufferedImage img = VacuumWorldView.DIRTIMAGES.get(appearance
					.getColor());
			int incw = this.getWidth() / dim;
			int inch = this.getHeight() / dim;
			g.drawImage(img, position.getX() * incw, position.getY() * inch,
					((position.getX() + 1) * incw), (position.getY() + 1)
							* inch, 0, 0, img.getWidth() - 2,
					img.getHeight() - 2, null);
		}
	}

	private void drawAgents(Graphics g, int dim) {
		for (AbstractAgent a : model.getAgents()) {
			VacuumWorldAgentAppearance appearance = (VacuumWorldAgentAppearance) a
					.getAppearance();
			Position position = appearance.getPosition();
			BufferedImage img = VacuumWorldView.AGENTIMAGES.get(
					appearance.getColor()).get(appearance.getOrientation());
			int incw = this.getWidth() / dim;
			int inch = this.getHeight() / dim;
			g.drawImage(img, position.getX() * incw, position.getY() * inch,
					(position.getX() + 1) * incw, (position.getY() + 1) * inch,
					0, 0, img.getWidth() - 2, img.getHeight() - 2, null);
		}
	}
}
