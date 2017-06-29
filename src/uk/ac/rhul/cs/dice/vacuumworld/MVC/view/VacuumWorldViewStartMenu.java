package uk.ac.rhul.cs.dice.vacuumworld.MVC.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.VacuumWorldView.OnStartGridSize;

public class VacuumWorldViewStartMenu extends JPanel implements ActionListener {

	private static final long serialVersionUID = 5094145897652688375L;
	private static Integer STARTBUTTONWIDTH = 220, STARTBUTTONHEIGHT = 60;
	private static Integer DEFAULTGRIDSIZE = 8, MAXGRIDSIZE = 100;
	private BufferedImage startmenu;
	private JTextField dimin;
	private StartButton start;
	private OnStartGridSize onStart;

	public VacuumWorldViewStartMenu(BufferedImage startmenu, OnStartGridSize onStart) {
		super();
		this.onStart = onStart;
		this.startmenu = startmenu;
		this.setLayout(new GridBagLayout());
		start = new StartMenuStartButton();
		start.setPreferredSize(new Dimension(STARTBUTTONWIDTH,
				STARTBUTTONHEIGHT));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(start, gbc);
		setUpDimIn();
		this.add(dimin, gbc);
	}

	private void setUpDimIn() {
		dimin = new JTextField(3);
		dimin.setEditable(true);
		Font font = dimin.getFont().deriveFont((float) 30);
		dimin.setFont(font);
		dimin.setHorizontalAlignment(JTextField.CENTER);
		dimin.setText(String.valueOf(DEFAULTGRIDSIZE));
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

	public class StartMenuStartButton extends StartButton {

		private static final long serialVersionUID = -4967158810170453134L;

		@Override
		public void mouseClicked(MouseEvent e) {
			Integer size;
			if ((size = validateDimensionInput(dimin.getText())) > 0) {
				onStart.start(size);
			}
		}
	}
}
