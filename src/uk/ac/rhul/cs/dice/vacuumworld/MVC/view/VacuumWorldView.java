package uk.ac.rhul.cs.dice.vacuumworld.MVC.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.ac.rhul.cs.dice.starworlds.utils.Pair;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldUniverse;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.VacuumWorldController.OnStart;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class VacuumWorldView extends JFrame implements Observer {

	static Map<BodyColor, Map<Orientation, BufferedImage>> AGENTIMAGES;
	static Map<BodyColor, BufferedImage> DIRTIMAGES;
	static String extension = ".png";
	static String path = "res/imgs/";

	static {
		AGENTIMAGES = new HashMap<>();
		for (BodyColor c : BodyColor.values()) {
			String color = path + c.toString().toLowerCase() + "_";
			Map<Orientation, BufferedImage> map = new HashMap<>();
			AGENTIMAGES.put(c, map);
			for (Orientation o : Orientation.values()) {
				map.put(o, loadImage(color + o.toString().toLowerCase()
						+ extension));
			}
		}
		DIRTIMAGES = new HashMap<>();
		DIRTIMAGES.put(BodyColor.GREEN, loadImage(path
				+ BodyColor.GREEN.toString().toLowerCase() + "_dirt"
				+ extension));
		DIRTIMAGES.put(BodyColor.ORANGE, loadImage(path
				+ BodyColor.ORANGE.toString().toLowerCase() + "_dirt"
				+ extension));
	}

	static final int DEFAULTWIDTH = 500, DEFAULTHEIGHT = 500;
	static final Dimension DEFAULTDIMENSION = new Dimension(DEFAULTWIDTH,
			DEFAULTHEIGHT);
	static final int SELECTORHEIGHT = 150;
	static final Dimension SELECTORDIMENSION = new Dimension(DEFAULTWIDTH,
			DEFAULTHEIGHT + SELECTORHEIGHT);
	static final Color DEFAULTCOLOUR = Color.WHITE;
	static final long serialVersionUID = 1L;

	protected JPanel contentpanel;
	protected VacuumWorldContentSelection contentselector;
	protected VacuumWorldViewContent content;
	protected VacuumWorldViewStartMenu startmenu;
	protected Integer griddimension;
	protected OnStart start;

	public VacuumWorldView() {
		init(null, DEFAULTWIDTH, DEFAULTHEIGHT, DEFAULTCOLOUR);
		this.setLayout(new BorderLayout());
		doStartMenu();
		this.pack();
	}

	private void init(Container contentPane, int width, int height,
			Color background) {
		if (contentPane != null) {
			this.setContentPane(contentPane);
		}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setPreferredSize(new Dimension(width, height));
		this.getContentPane().setBackground(background);
		this.setVisible(true);
		this.pack();
	}

	public void start(int griddim) {
		this.getContentPane().remove(startmenu);
		this.repaint();
		this.griddimension = griddim;
		doAgentSelection();
	}

	private void doStartMenu() {
		startmenu = new VacuumWorldViewStartMenu(loadImage(path + "start_menu"
				+ extension), new OnStartGridSize());
		startmenu.setPreferredSize(DEFAULTDIMENSION);
		this.getContentPane().add(startmenu);
	}

	public void start(Map<Position, Pair<BodyColor, Orientation>> entitymap) {
		this.getContentPane().remove(contentselector);
		this.repaint();
		start.start(griddimension, entitymap);
	}

	public void doContent(VacuumWorldUniverse model) {
		content = new VacuumWorldViewContent(model);
		content.setOpaque(false);
		this.getContentPane().setPreferredSize(DEFAULTDIMENSION);
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.pack();
	}

	private void doAgentSelection() {
		contentselector = new VacuumWorldContentSelection(griddimension,
				new OnStartSelection());
		contentselector.setOpaque(true);
		contentselector.initialise();
		this.getContentPane().setPreferredSize(SELECTORDIMENSION);
		this.getContentPane().add(contentselector, BorderLayout.CENTER);
		this.pack();
	}

	@Override
	public void update(Observable o, Object arg) {
		this.repaint();
	}

	public class OnStartSelection {
		public void start(Map<Position, Pair<BodyColor, Orientation>> entitymap) {
			VacuumWorldView.this.start(entitymap);
		}
	}

	public class OnStartGridSize {
		public void start(int gridsize) {
			VacuumWorldView.this.start(gridsize);
		}
	}

	public static BufferedImage loadImage(String file) {
		System.out.println("LOADING IMAGE: " + file);
		File img = new File(file);
		if (img.exists()) {
			try {
				return ImageIO.read(new File(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("CANNOT FIND IMAGE FILE: " + file);
		}
		return null;
	}

	public void setOnStart(OnStart start) {
		this.start = start;
	}
}
