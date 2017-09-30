package uk.ac.rhul.cs.dice.vacuumworld.mvc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorld;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldAmbient;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.StartParameters;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.VacuumWorldController.UniversePause;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.VacuumWorldController.UniverseRestart;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.VacuumWorldController.UniverseStart;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.view.buttons.Clickable;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.view.buttons.OnClick;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.ImageUtilities;

public class VacuumWorldView extends JFrame implements Observer {

	static Map<BodyColor, Map<Orientation, BufferedImage>> agentimages;
	static Map<BodyColor, BufferedImage> dirtimages;
	private static final Color AVATARCOLOR = new Color(50, 100, 220);
	public static final String EXTENSION = ".png";
	public static final String IMGPATH = "res/imgs/";
	public static final String CONTROLIMGDIR = "control/";
	public static final ImageLoader IMGLOADER = new ImageLoader();

	static {
		agentimages = new EnumMap<>(BodyColor.class);
		for (BodyColor c : BodyColor.getRealImageValues()) {
			String color = IMGPATH + c.toString().toLowerCase() + "_";
			Map<Orientation, BufferedImage> map = new EnumMap<>(
					Orientation.class);
			agentimages.put(c, map);
			for (Orientation o : Orientation.values()) {
				map.put(o,
						IMGLOADER.loadImage(color + o.toString().toLowerCase()
								+ EXTENSION));
			}
		}
		// do avatar image
		Map<Orientation, BufferedImage> usermap = agentimages
				.get(BodyColor.USER);
		Map<Orientation, BufferedImage> map = new EnumMap<>(Orientation.class);
		agentimages.put(BodyColor.AVATAR, map);
		usermap.forEach((o, b) -> {
			BufferedImage u = ImageUtilities.getOverlayedImage(b, AVATARCOLOR,
					0.5f);
			map.put(o, u);
		});

		dirtimages = new EnumMap<>(BodyColor.class);
		dirtimages.put(
				BodyColor.GREEN,
				IMGLOADER.loadImage(IMGPATH
						+ BodyColor.GREEN.toString().toLowerCase() + "_dirt"
						+ EXTENSION));
		dirtimages.put(
				BodyColor.ORANGE,
				IMGLOADER.loadImage(IMGPATH
						+ BodyColor.ORANGE.toString().toLowerCase() + "_dirt"
						+ EXTENSION));
	}

	static final int DEFAULTWIDTH = 500;
	static final int DEFAULTHEIGHT = 500;
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

	/*
	 * These should not be transient, however this class will not be serialised,
	 * it will not be an issue until (if ever) the view needs to be serialised.
	 */
	protected transient VacuumWorldAmbient model;
	protected transient JPanel contentpanel;
	protected transient VacuumWorldMainPanel mainpanel;
	protected transient VacuumWorldViewStartMenu startmenu;
	protected transient UniverseStart start;
	protected transient UniversePause pause;
	protected transient UniverseRestart restart;

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
		this.setTitle(VacuumWorld.class.getSimpleName());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setPreferredSize(new Dimension(width, height));
		this.getContentPane().setBackground(background);
		this.setVisible(true);
		this.pack();
	}

	private void doStartMenu() {
		startmenu = new VacuumWorldViewStartMenu(IMGLOADER.loadImage(IMGPATH
				+ "start_menu" + EXTENSION), new StartMenuOnClick());
		startmenu.setPreferredSize(DEFAULTDIMENSION);
		this.getContentPane().add(startmenu);
	}

	public void start(StartParameters params) {
		start.start(params);

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
			start();
		}

		private void start() {
			getContentPane().remove(startmenu);
			repaint();
			loadMainPanel();
		}

		private void loadMainPanel() {
			mainpanel = new VacuumWorldMainPanel(VacuumWorldView.this);
			mainpanel.setOpaque(true);
			mainpanel.initialise();
			VacuumWorldView.this.getContentPane().setPreferredSize(
					MAINDIMENSION);
			VacuumWorldView.this.getContentPane().add(mainpanel,
					BorderLayout.CENTER);
			VacuumWorldView.this.revalidate();
			VacuumWorldView.this.repaint();
			VacuumWorldView.this.pack();
			mainpanel.requestFocusInWindow();
		}

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

	public void setUniverseRestart(UniverseRestart restart) {
		this.restart = restart;
	}

	public static class ImageLoader {

		private static final BufferedImage DEFAULTIMG;
		static {
			DEFAULTIMG = new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = DEFAULTIMG.createGraphics();
			g.setPaint(new Color(0xFF1493));
			g.fillRect(0, 0, DEFAULTIMG.getWidth(), DEFAULTIMG.getHeight());
		}

		public BufferedImage loadImage(String file) {
			try {
				// for some reason this is not working inside eclipse, however
				// it works inside a jar
				InputStream s = this.getClass().getClassLoader()
						.getResourceAsStream(file.replace("res/", ""));
				if (s == null) {
					return ImageIO.read(this.getClass().getClassLoader()
							.getResourceAsStream(file));
				}
				return ImageIO.read(s);
			} catch (IOException e) {
				VacuumWorld.LOGGER.log(Level.SEVERE,
						"Failed to load image from file: " + file, e);
				return DEFAULTIMG;
			}
		}
	}
}
