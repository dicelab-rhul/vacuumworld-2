package uk.ac.rhul.cs.dice.vacuumworld.MVC.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uk.ac.rhul.cs.dice.starworlds.utils.Pair;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.VacuumWorldView.OnStartSelection;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class VacuumWorldContentSelection extends JLayeredPane {

	private static final long serialVersionUID = -1288708599965111057L;

	protected OnStartSelection start;
	protected JPanel content;
	protected DragPanel drag;
	protected GridPanel grid;
	private Map<BufferedImage, Pair<BodyColor, Orientation>> bodymap;
	private List<SelectorButton> buttons;
	private Integer griddimension;
	private Map<Position, BufferedImage> positionmap;

	public VacuumWorldContentSelection(Integer griddimension,
			OnStartSelection start) {
		this.start = start;
		this.setBackground(Color.WHITE);
		this.addComponentListener(new ResizeListener());
		this.griddimension = griddimension;
		this.positionmap = new HashMap<>();
		buttons = new ArrayList<>();
		bodymap = new HashMap<>();
		VacuumWorldView.AGENTIMAGES.forEach((c, m) -> {
			m.forEach((o, i) -> {
				SelectorButton b = new SelectorButton(i, CustomButton
						.getOverlayedImage(i, Color.BLACK, 0.1f));
				buttons.add(b);
				bodymap.put(i, new Pair<>(c, o));
			});
		});
		VacuumWorldView.DIRTIMAGES.forEach((c, i) -> {
			SelectorButton b = new SelectorButton(i, CustomButton
					.getOverlayedImage(i, Color.BLACK, 0.1f));
			buttons.add(b);
			bodymap.put(i, new Pair<>(c, null));
		});
	}

	public void initialise() {
		// default content
		content = new JPanel();
		content.setLayout(new BorderLayout());
		content.setBounds(0, 0, VacuumWorldView.SELECTORDIMENSION.width,
				VacuumWorldView.SELECTORDIMENSION.height);
		content.setOpaque(false);
		// grid panel
		grid = new SelectionGridPanel(griddimension);
		grid.setPreferredSize(VacuumWorldView.DEFAULTDIMENSION);
		content.add(grid, BorderLayout.CENTER);
		JPanel superbuttonpanel = new JPanel();
		superbuttonpanel.setOpaque(false);
		superbuttonpanel.setLayout(new GridLayout(2, 1));
		// button panel
		JPanel buttonpanel = new JPanel();
		buttonpanel.setOpaque(false);
		buttonpanel.setLayout(new GridLayout(0, 9));
		Dimension buttondim = new Dimension(this.getWidth() / buttons.size(),
				this.getHeight() - VacuumWorldView.DEFAULTHEIGHT);
		for (int i = 0; i < buttons.size(); i++) {
			SelectorButton button = buttons.get(i);
			button.setPreferredSize(buttondim);
			buttonpanel.add(button);
		}
		superbuttonpanel.setPreferredSize(new Dimension(
				VacuumWorldView.DEFAULTWIDTH, VacuumWorldView.SELECTORHEIGHT));
		superbuttonpanel.add(buttonpanel);
		superbuttonpanel.add(new SelectionStartButton());
		content.add(superbuttonpanel, BorderLayout.PAGE_END);
		this.add(content, JLayeredPane.DEFAULT_LAYER);
		// drag content
		drag = new DragPanel();
		this.add(drag, JLayeredPane.DRAG_LAYER);
	}

	public void dragMove(SelectorButton button, MouseEvent e) {
		drag.updateImage(button.getImage());
		drag.setLocation(grid.snapSelectionBox(SwingUtilities.convertPoint(
				e.getComponent(), e.getPoint(), grid)));
	}

	public void place(SelectorButton button, MouseEvent e) {
		Position p = grid.getGridPosition(SwingUtilities.convertPoint(
				e.getComponent(), e.getPoint(), grid));
		if (p != null) {
			positionmap.put(p, button.getImage());
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		positionmap.forEach((p, i) -> grid.drawBody(g2, i, p));
	}

	public class SelectionGridPanel extends GridPanel implements MouseListener {

		private static final long serialVersionUID = 7999533531378880749L;

		public SelectionGridPanel(int griddimension) {
			super(griddimension);
			this.addMouseListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				Position p = this.getGridPosition(e.getPoint());
				if (p != null) {
					positionmap.remove(p);
					VacuumWorldContentSelection.this.repaint();
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	public class SelectionStartButton extends StartButton {

		private static final long serialVersionUID = 6705279024891980026L;

		@Override
		public void mouseClicked(MouseEvent arg0) {
			System.out.println("Start!");
			Map<Position, Pair<BodyColor, Orientation>> entitymap = new HashMap<>();
			positionmap.forEach((p, i) -> entitymap.put(p, bodymap.get(i)));
			start.start(entitymap);
		}
	}

	public class SelectorButton extends CustomButton implements
			MouseMotionListener {

		private static final long serialVersionUID = 731055277706069478L;

		public SelectorButton(BufferedImage image, BufferedImage over) {
			super(image, over, over);
			this.addMouseMotionListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			place(this, e);
			drag.hide();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			this.isPressed = true;
			dragMove(this, e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}

	public class ResizeListener implements ComponentListener {
		@Override
		public void componentHidden(ComponentEvent e) {
		}

		@Override
		public void componentMoved(ComponentEvent e) {
		}

		@Override
		public void componentResized(ComponentEvent e) {
			if (content != null) {
				content.setBounds(0, 0, e.getComponent().getWidth(), e
						.getComponent().getHeight());
			}
			if (drag != null) {
				drag.setSize(grid.getWidth() / griddimension, grid.getHeight()
						/ griddimension);
			}
		}

		@Override
		public void componentShown(ComponentEvent e) {

		}
	}
}
