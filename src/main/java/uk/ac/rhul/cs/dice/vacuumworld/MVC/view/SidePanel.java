package uk.ac.rhul.cs.dice.vacuumworld.MVC.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class SidePanel extends JPanel {

	private static final long serialVersionUID = 1678278950729919216L;

	public SidePanel(Dimension dimension,
			VacuumWorldControlButtonPanel controlbuttonpanel) {
		this.setOpaque(true);
		this.setPreferredSize(VacuumWorldView.SIDEPANELDIMENSION);
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		this.add(controlbuttonpanel, BorderLayout.PAGE_START);
	}

}
