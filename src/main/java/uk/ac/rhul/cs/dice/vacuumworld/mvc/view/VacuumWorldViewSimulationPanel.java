package uk.ac.rhul.cs.dice.vacuumworld.mvc.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarAgent;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldAmbient;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;

public class VacuumWorldViewSimulationPanel extends GridPanel {

    private static final long serialVersionUID = -5008822076025428472L;
    /* This class should never be serialised */
    private transient VacuumWorldAmbient model;

    public VacuumWorldViewSimulationPanel(VacuumWorldAmbient model) {
	super(null);
	this.model = model;
	this.setBackground(Color.WHITE);
    }

    public void start() {
	this.setGridDimension(model.getDimension());
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;
	g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	for (PassiveBody b : model.getPassiveBodies()) {
	    drawDirt(g2, b);
	}
	for (AbstractAutonomousAgent a : model.getAgents()) {
	    drawAgent(g2, a);
	}
	for (AbstractAvatarAgent<?> a : model.getAvatars()) {
	    drawAgent(g2, a);
	}
    }

    private void drawDirt(Graphics g, PassiveBody body) {
	DirtAppearance appearance = (DirtAppearance) body.getAppearance();
	drawBody(g, VacuumWorldView.dirtimages.get(appearance.getColor()), appearance.getPosition());
    }

    private void drawAgent(Graphics g, ActiveBody body) {
	VacuumWorldAgentAppearance appearance = (VacuumWorldAgentAppearance) body.getAppearance();
	drawBody(g, VacuumWorldView.agentimages.get(appearance.getColor()).get(appearance.getOrientation()),
		appearance.getPosition());
    }
}
