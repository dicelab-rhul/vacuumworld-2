package uk.ac.rhul.cs.dice.vacuumworld.mvc.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import uk.ac.rhul.cs.dice.vacuumworld.mvc.view.VacuumWorldView.StartMenuOnClick;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.view.buttons.StartButton;

public class VacuumWorldViewStartMenu extends JPanel implements ActionListener {

	private static final long serialVersionUID = 5094145897652688375L;
	private static Integer STARTBUTTONWIDTH = 165, STARTBUTTONHEIGHT = 45;

	private BufferedImage startmenu;
	private StartButton start;

	public VacuumWorldViewStartMenu(BufferedImage startmenu,
			StartMenuOnClick onClickStart) {
		super();
		this.startmenu = startmenu;
		this.setLayout(new GridBagLayout());
		start = new StartButton(onClickStart);
		start.setPreferredSize(new Dimension(STARTBUTTONWIDTH,
				STARTBUTTONHEIGHT));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(start, gbc);
	}

	@Override
	public Dimension getPreferredSize() {
		return super.getPreferredSize();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		this.drawImage(g2, startmenu);
	}

	private void drawImage(Graphics2D g2, BufferedImage image) {
		g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), 0, 0,
				image.getWidth(), image.getHeight(), null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
