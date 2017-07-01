package uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons;

import java.awt.Font;

import javax.swing.JTextField;

public class SettingsPanel {

	private JTextField dimin;
	private static Integer DEFAULTGRIDSIZE = 8, MAXGRIDSIZE = 100;

	public SettingsPanel() {
		// setUpDimIn();
		// this.add(dimin, gbc);
	}

	
	
	
	
	private void setUpDimIn() {
		dimin = new JTextField(3);
		dimin.setEditable(true);
		Font font = dimin.getFont().deriveFont((float) 30);
		dimin.setFont(font);
		dimin.setHorizontalAlignment(JTextField.CENTER);
		dimin.setText(String.valueOf(DEFAULTGRIDSIZE));

	}

	private Integer validateDimensionInput(String text) {
		if (text != null) {
			if (!text.isEmpty()) {
				try {
					Integer i = Integer.parseInt(text);
					if (i <= MAXGRIDSIZE) {
						return i;
					}
				} catch (NumberFormatException e) {
				}
			}
		}
		return -1;
	}
}
