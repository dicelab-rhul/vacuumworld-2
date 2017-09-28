package uk.ac.rhul.cs.dice.vacuumworld.mvc.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class PausePanel extends JPanel {

	private static final long serialVersionUID = 4281540120540038027L;
	private static final String PAUSEMESSAGE = "PAUSED";

	public PausePanel() {
		this.setOpaque(true);
		this.setBackground(new Color(100, 100, 100, 100));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setFont(new Font("SansSerif", Font.BOLD, 30));

		FontMetrics fm = g2.getFontMetrics();
		int x = (getWidth() - fm.stringWidth(PAUSEMESSAGE)) / 2;
		int y = (getHeight() - fm.getHeight()) / 2;
		g2.setColor(Color.BLACK);
		g2.drawString(PAUSEMESSAGE, x, y
				+ ((fm.getDescent() + fm.getAscent()) / 2));
	}

}
