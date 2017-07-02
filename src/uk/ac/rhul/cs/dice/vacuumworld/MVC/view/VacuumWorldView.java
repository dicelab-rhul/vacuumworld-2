package uk.ac.rhul.cs.dice.vacuumworld.MVC.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.ac.rhul.cs.dice.starworlds.utils.Pair;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorld;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldUniverse;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.VacuumWorldController.UniverseOnStart;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class VacuumWorldView extends JFrame implements Observer {

	static Map<BodyColor, Map<Orientation, BufferedImage>> AGENTIMAGES;
	static Map<BodyColor, BufferedImage> DIRTIMAGES;
	public final static String EXTENSION = ".png";
	public final static String PATH = "res/imgs/";
	public final static String CONTROLPATH = "res/imgs/control/";

	static {
		AGENTIMAGES = new HashMap<>();
		for (BodyColor c : BodyColor.values()) {
			String color = PATH + c.toString().toLowerCase() + "_";
			Map<Orientation, BufferedImage> map = new HashMap<>();
			AGENTIMAGES.put(c, map);
			for (Orientation o : Orientation.values()) {
				map.put(o, loadImage(color + o.toString().toLowerCase()
						+ EXTENSION));
			}
		}
		DIRTIMAGES = new HashMap<>();
		DIRTIMAGES.put(BodyColor.GREEN, loadImage(PATH
				+ BodyColor.GREEN.toString().toLowerCase() + "_dirt"
				+ EXTENSION));
		DIRTIMAGES.put(BodyColor.ORANGE, loadImage(PATH
				+ BodyColor.ORANGE.toString().toLowerCase() + "_dirt"
				+ EXTENSION));
	}

	static final int DEFAULTGRIDDIMENSION = 8;

	static final int DEFAULTWIDTH = 500, DEFAULTHEIGHT = 500;
	static final int SIDEPANELWIDTH = 250;
	static final int MAINWIDTH = DEFAULTWIDTH + SIDEPANELWIDTH;

	static final Dimension DEFAULTDIMENSION = new Dimension(DEFAULTWIDTH,
			DEFAULTHEIGHT);
	static final Dimension MAINDIMENSION = new Dimension(MAINWIDTH,
			DEFAULTHEIGHT);
	static final Dimension SIDEPANELDIMENSION = new Dimension(SIDEPANELWIDTH,
			DEFAULTHEIGHT);

	static final Color DEFAULTCOLOUR = Color.WHITE;
	static final long serialVersionUID = 1L;

	protected JPanel contentpanel;
	protected VacuumWorldMainPanel mainpanel;
	protected VacuumWorldViewSimulationPanel content;
	protected VacuumWorldViewStartMenu startmenu;
	protected Integer griddimension;
	protected UniverseOnStart start;

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
		this.setTitle(VacuumWorld.class.getSimpleName() + " V"
				+ VacuumWorld.VERSION);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setPreferredSize(new Dimension(width, height));
		this.getContentPane().setBackground(background);
		this.setVisible(true);
		this.pack();
	}

	private void doStartMenu() {
		startmenu = new VacuumWorldViewStartMenu(loadImage(PATH + "start_menu"
				+ EXTENSION), new VacuumWorldStartMenuStart());
		startmenu.setPreferredSize(DEFAULTDIMENSION);
		this.getContentPane().add(startmenu);
	}

	public void start(
			Map<Position, List<Pair<BodyColor, Orientation>>> entitymap) {
		this.getContentPane().remove(mainpanel);
		this.repaint();
		start.start(griddimension, entitymap);
	}

	public void doContent(VacuumWorldUniverse model) {
		content = new VacuumWorldViewSimulationPanel(model);
		content.setOpaque(false);
		this.getContentPane().setPreferredSize(DEFAULTDIMENSION);
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.pack();
	}

	private void doAgentSelection() {
		mainpanel = new VacuumWorldMainPanel(new OnStartSelection());
		mainpanel.setOpaque(true);
		mainpanel.initialise();
		this.addKeyListener(mainpanel);
		this.getContentPane().setPreferredSize(MAINDIMENSION);
		this.getContentPane().add(mainpanel, BorderLayout.CENTER);
		this.pack();
	}

	@Override
	public void update(Observable o, Object arg) {
		this.repaint();
	}

	public class OnStartSelection {
		public void start(
				Map<Position, List<Pair<BodyColor, Orientation>>> entitymap) {
			VacuumWorldView.this.start(entitymap);
		}
	}

	public class VacuumWorldStartMenuStart implements OnStart<Object> {
		@Override
		public void start(Object... args) {
			VacuumWorldView.this.start();
		}
	}

	private void start() {
		this.getContentPane().remove(startmenu);
		this.repaint();
		doAgentSelection();
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

	public void setOnStart(UniverseOnStart start) {
		this.start = start;
	}
}
