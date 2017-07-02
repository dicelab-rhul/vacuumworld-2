package uk.ac.rhul.cs.dice.vacuumworld.MVC.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VacuumWorldViewSettingsPanel extends JPanel {

	private static final int TEXTFIELDSIZE = 50;
	private static final long serialVersionUID = 1252849880132919023L;

	private JPanel mindtextpanel;
	private JTextField greenmindtext;
	private JTextField orangemindtext;
	private JTextField whitemindtext;

	public VacuumWorldViewSettingsPanel() {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.setLayout(new GridBagLayout());
		mindtextpanel = new JPanel();
		mindtextpanel.setLayout(new GridLayout(3, 2));
		mindtextpanel.setBackground(Color.GRAY);
		mindtextpanel.setOpaque(true);
		mindtextpanel.setPreferredSize(new Dimension(100, 100));
		mindtextpanel.add(new JLabel("Green Mind "));
		mindtextpanel.add(greenmindtext = new JTextField(TEXTFIELDSIZE));
		mindtextpanel.add(new JLabel("Orange Mind "));
		mindtextpanel.add(orangemindtext = new JTextField(TEXTFIELDSIZE));
		mindtextpanel.add(new JLabel("White Mind "));
		mindtextpanel.add(whitemindtext = new JTextField(TEXTFIELDSIZE));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1;
		this.add(mindtextpanel, c);
		System.out.println();
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		this.mindtextpanel.setPreferredSize(new Dimension(width, height));
		this.revalidate();
		this.repaint();
	}
}
