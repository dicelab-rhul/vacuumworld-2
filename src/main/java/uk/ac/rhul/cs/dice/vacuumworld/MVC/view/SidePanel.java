package uk.ac.rhul.cs.dice.vacuumworld.mvc.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class SidePanel extends JPanel {

	private static final long serialVersionUID = 1678278950729919216L;

	public SidePanel(VacuumWorldControlButtonPanel controlbuttonpanel) {
		this.setOpaque(true);
		this.setPreferredSize(VacuumWorldView.SIDEPANELDIMENSION);
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		this.add(controlbuttonpanel, BorderLayout.PAGE_START);
	}

}
