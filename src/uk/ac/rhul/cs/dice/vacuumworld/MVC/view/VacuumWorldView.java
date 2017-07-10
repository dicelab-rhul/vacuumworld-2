package uk.ac.rhul.cs.dice.vacuumworld.MVC.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
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

import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorld;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldAmbient;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.StartParameters;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.VacuumWorldController.UniversePause;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.VacuumWorldController.UniverseStart;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons.Clickable;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons.OnClick;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.ImageUtilities;

public class VacuumWorldView extends JFrame implements Observer {

	static Map<BodyColor, Map<Orientation, BufferedImage>> AGENTIMAGES;
	static Map<BodyColor, BufferedImage> DIRTIMAGES;
	public final static String EXTENSION = ".png";
	public final static String PATH = "res/imgs/";
	public final static String CONTROLPATH = "res/imgs/control/";
	private final static Color AVATARCOLOR = new Color(50, 100, 220);

	static {
		AGENTIMAGES = new HashMap<>();
		for (BodyColor c : BodyColor.getRealImageValues()) {
			String color = PATH + c.toString().toLowerCase() + "_";
			Map<Orientation, BufferedImage> map = new HashMap<>();
			AGENTIMAGES.put(c, map);
			for (Orientation o : Orientation.values()) {
				map.put(o, loadImage(color + o.toString().toLowerCase()
						+ EXTENSION));
			}
		}

		// do avatar image
		Map<Orientation, BufferedImage> usermap = AGENTIMAGES
				.get(BodyColor.USER);
		Map<Orientation, BufferedImage> map = new HashMap<>();
		AGENTIMAGES.put(BodyColor.AVATAR, map);
		usermap.forEach((o, b) -> {
			BufferedImage u = ImageUtilities.getOverlayedImage(b, AVATARCOLOR,
					0.5f);
			map.put(o, u);
		});

		DIRTIMAGES = new HashMap<>();
		DIRTIMAGES.put(BodyColor.GREEN, loadImage(PATH
				+ BodyColor.GREEN.toString().toLowerCase() + "_dirt"
				+ EXTENSION));
		DIRTIMAGES.put(BodyColor.ORANGE, loadImage(PATH
				+ BodyColor.ORANGE.toString().toLowerCase() + "_dirt"
				+ EXTENSION));
	}

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

	protected VacuumWorldAmbient model;
	protected JPanel contentpanel;
	protected VacuumWorldMainPanel mainpanel;
	protected VacuumWorldViewStartMenu startmenu;
	protected UniverseStart start;
	protected UniversePause pause;

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
				+ EXTENSION), new StartMenuOnClick());
		startmenu.setPreferredSize(DEFAULTDIMENSION);
		this.getContentPane().add(startmenu);
	}

	public void start(StartParameters params) {
		start.start(params);
	}

	private void loadMainPanel() {
		mainpanel = new VacuumWorldMainPanel(this);
		mainpanel.setOpaque(true);
		mainpanel.initialise();
		this.getContentPane().setPreferredSize(MAINDIMENSION);
		this.getContentPane().add(mainpanel, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
		this.pack();
		mainpanel.requestFocusInWindow();
	}

	public synchronized void addKeyListenerToMainPanel(KeyListener l) {
		mainpanel.addKeyListener(l);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.repaint();
	}

	public class StartMenuOnClick implements OnClick {
		@Override
		public void onClick(Clickable arg, MouseEvent e) {
			VacuumWorldView.this.start();
		}
	}

	private void start() {
		this.getContentPane().remove(startmenu);
		this.repaint();
		loadMainPanel();
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

	public void setModel(VacuumWorldAmbient model) {
		this.model = model;
	}

	public void setUniverseStart(UniverseStart start) {
		this.start = start;
	}

	public void setUniversePause(UniversePause pause) {
		this.pause = pause;
	}

	public void setUniverseRestart() {

	}
}
