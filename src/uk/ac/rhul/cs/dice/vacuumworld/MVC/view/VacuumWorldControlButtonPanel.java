package uk.ac.rhul.cs.dice.vacuumworld.MVC.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons.CustomButton;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons.OnClick;

public class VacuumWorldControlButtonPanel extends JPanel {

	private static final long serialVersionUID = -1217821076040443501L;

	static final Integer SMALLBUTTONSIZE = VacuumWorldView.SIDEPANELWIDTH / 4;
	static final Dimension SMALLBUTTONDIMENSION = new Dimension(
			SMALLBUTTONSIZE, SMALLBUTTONSIZE);

	public VacuumWorldControlButtonPanel(BufferedImage play, OnClick playclick,
			BufferedImage restart, OnClick restartclick, BufferedImage pause,
			OnClick pauseclick, BufferedImage settings, OnClick settingsclick) {
		this.setLayout(new GridLayout(1, 4));
		this.setPreferredSize(new Dimension(SMALLBUTTONSIZE * 3,
				SMALLBUTTONSIZE));
		this.setOpaque(false);
		this.add(new ControlButton(play, playclick));
		this.add(new ControlButton(restart, restartclick));
		this.add(new ControlButton(pause, pauseclick));
		this.add(new ControlButton(settings, settingsclick));
	}

	private class ControlButton extends CustomButton {

		private static final long serialVersionUID = -5438643093370234321L;

		public ControlButton(BufferedImage image, OnClick onclick) {
			super(image, onclick);
			this.setPressed(CustomButton.getOverlayedImage(image, Color.WHITE,
					0.8f));
			this.setHover(CustomButton.getOverlayedImage(image, Color.WHITE,
					0.4f));
			this.setPreferredSize(SMALLBUTTONDIMENSION);
		}
	}
}
