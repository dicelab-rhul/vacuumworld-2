package uk.ac.rhul.cs.dice.vacuumworld.MVC.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.VacuumWorldView.OnStartSelection;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons.Clickable;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons.CustomButton;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons.OnClick;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons.RotateButton;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class VacuumWorldMainPanel extends JLayeredPane implements KeyListener {

	static final Integer ROTATIONEXTRA = 100;
	private static final long serialVersionUID = -1288708599965111057L;

	protected OnStartSelection start;
	protected JPanel content;
	protected RotateButton rotate;
	protected VacuumWorldViewSettingsPanel settings;
	protected DragPanel drag;
	protected GridPanel grid;
	protected SidePanel side;
	private List<SelectorButton> agentbuttons;
	private List<SelectorButton> dirtbuttons;
	private List<SelectorButton> userbuttons;
	private Integer griddimension;

	private Map<Position, DirtAppearance> dirts;
	private Map<Position, VacuumWorldAgentAppearance> agents;

	public VacuumWorldMainPanel(OnStartSelection start) {
		this.start = start;
		this.griddimension = VacuumWorldView.DEFAULTGRIDDIMENSION;
		this.setBackground(Color.WHITE);
		this.addComponentListener(new ResizeListener());
		this.dirts = new HashMap<>();
		this.agents = new HashMap<>();
		agentbuttons = new ArrayList<>();
		dirtbuttons = new ArrayList<>();
		userbuttons = new ArrayList<>();
		VacuumWorldView.AGENTIMAGES.forEach((c, m) -> {
			BufferedImage i = m.get(Orientation.NORTH);
			SelectorButton b = new SelectorButton(i, c, null);
			if (c != BodyColor.USER) {
				agentbuttons.add(b);
			} else {
				userbuttons.add(b);
			}
		});
		VacuumWorldView.DIRTIMAGES.forEach((c, i) -> {
			SelectorButton b = new SelectorButton(i, c, null);
			dirtbuttons.add(b);
		});
	}

	public void initialise() {
		// default content
		content = new JPanel();
		content.setLayout(new BorderLayout());
		content.setBounds(0, 0, VacuumWorldView.MAINDIMENSION.width,
				VacuumWorldView.MAINDIMENSION.height);
		content.setOpaque(false);
		// grid panel
		grid = new SelectionGridPanel(VacuumWorldView.DEFAULTDIMENSION,
				griddimension);
		// side panel
		side = new SidePanel(VacuumWorldView.SIDEPANELDIMENSION,
				new VacuumWorldControlButtonPanel(VacuumWorldView
						.loadImage(VacuumWorldView.CONTROLPATH + "play_button"
								+ VacuumWorldView.EXTENSION),
						new PlayOnClick(), VacuumWorldView
								.loadImage(VacuumWorldView.CONTROLPATH
										+ "restart_button"
										+ VacuumWorldView.EXTENSION),
						new RestartOnClick(), VacuumWorldView
								.loadImage(VacuumWorldView.CONTROLPATH
										+ "pause_button"
										+ VacuumWorldView.EXTENSION),
						new PauseOnClick(), VacuumWorldView
								.loadImage(VacuumWorldView.CONTROLPATH
										+ "settings_button"
										+ VacuumWorldView.EXTENSION),
						new SettingsOnClick()));
		JPanel sbp = new JPanel();
		int gs = 4;
		sbp.setLayout(new GridLayout(0, gs));
		sbp.setBackground(Color.WHITE);
		for (int i = 0; i < gs; i++) {
			addFilledPanel(sbp);
		}
		agentbuttons.forEach((b) -> sbp.add(b));
		addFilledPanel(sbp);
		userbuttons.forEach((b) -> sbp.add(b));
		addFilledPanel(sbp);
		addFilledPanel(sbp);
		addFilledPanel(sbp);
		dirtbuttons.forEach((b) -> sbp.add(b));
		for (int i = 0; i < 13; i++) {
			addFilledPanel(sbp);
		}
		side.add(sbp, BorderLayout.CENTER);
		content.add(side, BorderLayout.EAST);
		content.add(grid, BorderLayout.CENTER);
		this.add(content, JLayeredPane.DEFAULT_LAYER);
		// rotate button
		this.rotate = new RotateButton(new RemoveOnClick());
		this.add(rotate, JLayeredPane.POPUP_LAYER);
		// drag content
		drag = new DragPanel();
		this.add(drag, JLayeredPane.DRAG_LAYER);
		// settings panel
		settings = new VacuumWorldViewSettingsPanel();
		this.add(settings, JLayeredPane.POPUP_LAYER);
	}

	private void addFilledPanel(JPanel panel) {
		JPanel p = new JPanel();
		// p.setOpaque(false);
		panel.add(p);
	}

	public void dragMove(SelectorButton button, MouseEvent e) {
		drag.updateImage(button.getImage());
		drag.setLocation(grid.snapSelectionBox(SwingUtilities.convertPoint(
				e.getComponent(), e.getPoint(), grid)));
	}

	public void place(SelectorButton button, MouseEvent e) {
		Position p = grid.getGridPosition(getGridPoint(e));
		if (agentbuttons.contains(button) || userbuttons.contains(button)) {
			VacuumWorldAgentAppearance app = new VacuumWorldAgentAppearance(
					null, null, null, null);
			app.setColor(button.color);
			app.setOrientation(Orientation.NORTH);
			agents.put(p, app);
			rotate.setLocation(getRotatePoint(e));
			rotate.setCurrent(app);
		} else if (dirtbuttons.contains(button)) {
			dirts.put(p, new DirtAppearance(button.color));
			rotate.hide();
			rotate.setCurrent(null);
		}
	}

	public void remove(Position position) {
		if (position != null) {
			rotate.setCurrent(null);
			rotate.hide();
			dirts.remove(position);
			agents.remove(position);
			VacuumWorldMainPanel.this.repaint();
		}
	}

	private Point getGridPoint(MouseEvent e) {
		return grid.snapSelectionBox(SwingUtilities.convertPoint(
				e.getComponent(), e.getPoint(), grid));
	}

	private Point getRotatePoint(MouseEvent e) {
		Point p = grid.snapSelectionBox(SwingUtilities.convertPoint(
				e.getComponent(), e.getPoint(), grid));
		p.x -= ROTATIONEXTRA / 2;
		p.y -= ROTATIONEXTRA / 2;
		return p;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		dirts.forEach((position, app) -> {
			grid.drawDirt(g2, position, app.getColor());
		});
		agents.forEach((position, app) -> {
			grid.drawAgent(g2, position, app.getColor(), app.getOrientation());
		});
	}

	private class RemoveOnClick implements OnClick {

		@Override
		public void onClick(Clickable arg, MouseEvent e) {
			VacuumWorldMainPanel.this.remove(grid
					.getGridPosition(getGridPoint(e)));
		}
	}

	public class SelectionGridPanel extends GridPanel implements MouseListener {

		private static final long serialVersionUID = 7999533531378880749L;

		public SelectionGridPanel(Dimension dimension, int griddimension) {
			super(griddimension);
			this.addMouseListener(this);
			this.setPreferredSize(dimension);
			this.setOpaque(false);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println(e.getClickCount());
			if (e.getClickCount() == 1) {
				Position p = this.getGridPosition(e.getPoint());
				if (agents.containsKey(p)) {
					rotate.setCurrent(agents.get(p));
					rotate.setLocation(getRotatePoint(e));
				}
			} else {
				VacuumWorldMainPanel.this.remove(this.getGridPosition(e
						.getPoint()));
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

	public class SettingsOnClick implements OnClick {

		@Override
		public void onClick(Clickable arg, MouseEvent e) {
			System.out.println("SETTINGS");
			int gw = VacuumWorldMainPanel.this.getWidth() / 20;
			settings.setBounds(gw, gw, VacuumWorldMainPanel.this.getWidth()
					- (gw * 2), VacuumWorldMainPanel.this.getHeight()
					- (gw * 2));
			settings.setOpaque(true);
			VacuumWorldMainPanel.this.repaint();
		}
	}

	public class RestartOnClick implements OnClick {

		@Override
		public void onClick(Clickable arg, MouseEvent e) {
			agents.clear();
			dirts.clear();
			rotate.setCurrent(null);
			rotate.hide();
			repaint();
		}
	}

	public class PauseOnClick implements OnClick {

		@Override
		public void onClick(Clickable arg, MouseEvent e) {
			System.out.println("PAUSE");
		}
	}

	public class PlayOnClick implements OnClick {

		@Override
		public void onClick(Clickable arg, MouseEvent e) {
			System.out.println("PLAY");
		}
	}

	public class SelectorButton extends CustomButton implements
			MouseMotionListener {

		private static final long serialVersionUID = 731055277706069478L;
		private BodyColor color;

		public SelectorButton(BufferedImage image, BodyColor color,
				OnClick onclick) {
			super(image, CustomButton.getOverlayedImage(image, Color.BLACK,
					0.2f), CustomButton.getOverlayedImage(image, Color.BLACK,
					0.1f), onclick);
			this.addMouseMotionListener(this);
			this.color = color;
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
			if (rotate != null) {
				rotate.setSize(grid.getWidth() / griddimension + ROTATIONEXTRA,
						grid.getHeight() / griddimension + ROTATIONEXTRA);
			}
		}

		@Override
		public void componentShown(ComponentEvent e) {

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			settings.setBounds(0, 0, 0, 0);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_A) {
			rotate.rotateLeft();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_D) {
			rotate.rotateRight();
		}
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
