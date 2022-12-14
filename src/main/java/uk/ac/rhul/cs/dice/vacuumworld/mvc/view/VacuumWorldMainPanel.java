package uk.ac.rhul.cs.dice.vacuumworld.mvc.view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uk.ac.rhul.cs.dice.vacuumworld.appearances.DirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.SaveManager;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.StartParameters;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.VacuumWorldController;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.view.buttons.Clickable;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.view.buttons.CustomButton;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.view.buttons.OnClick;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.view.buttons.RotateButton;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.ImageUtilities;

public class VacuumWorldMainPanel extends JLayeredPane implements KeyListener {

    static final Integer ROTATIONEXTRA = 100;
    private static final long serialVersionUID = -1288708599965111057L;

    private VacuumWorldView view;
    protected boolean simulating = false;
    protected JPanel content;
    protected SidePanel side;

    protected RotateButton rotate;
    protected DragPanel drag;
    protected PausePanel pausepanel;
    protected VacuumWorldViewSettingsPanel settings;
    protected transient VacuumWorldSelectionGridPanel selectiongrid;
    protected VacuumWorldViewSimulationPanel simulationgrid;
    protected JPanel selectionButtonPanel;

    private transient List<SelectorButton> agentbuttons;
    private transient List<SelectorButton> dirtbuttons;
    private transient List<SelectorButton> userbuttons;

    private StartParameters startparams = new StartParameters();

    public VacuumWorldMainPanel(VacuumWorldView view) {
	this.view = view;
	this.setBackground(Color.WHITE);
	this.addComponentListener(new ResizeListener());
	agentbuttons = new ArrayList<>();
	dirtbuttons = new ArrayList<>();
	userbuttons = new ArrayList<>();
	VacuumWorldView.agentimages.forEach((c, m) -> {
	    BufferedImage i = m.get(Orientation.NORTH);
	    SelectorButton b = new SelectorButton(i, c, null);
	    if (c != BodyColor.USER && c != BodyColor.AVATAR) {
		agentbuttons.add(b);
	    } else {
		userbuttons.add(b);
	    }
	});
	VacuumWorldView.dirtimages.forEach((c, i) -> {
	    SelectorButton b = new SelectorButton(i, c, null);
	    dirtbuttons.add(b);
	});
	this.enableInputMethods(true);
	getDefaultStartParameters();
    }

    private void getDefaultStartParameters() {
	this.startparams = SaveManager.loadDefault();
    }

    public void initialise() {
	this.addKeyListener(this);
	this.setFocusable(true);
	// default content
	content = new JPanel();
	content.setLayout(new BorderLayout());
	content.setBounds(0, 0, VacuumWorldView.MAINDIMENSION.width, VacuumWorldView.MAINDIMENSION.height);
	content.setOpaque(false);
	// grid panel
	selectiongrid = new VacuumWorldSelectionGridPanel(VacuumWorldView.DEFAULTDIMENSION, startparams.getDimension());
	simulationgrid = new VacuumWorldViewSimulationPanel(view.model);
	// side panel
	side = new SidePanel(
		new VacuumWorldControlButtonPanel(
			VacuumWorldView.IMGLOADER.loadImage(VacuumWorldView.IMGPATH
				+ VacuumWorldView.CONTROLIMGDIR + "play_button" + VacuumWorldView.EXTENSION),
			new PlayOnClick(),
			VacuumWorldView.IMGLOADER.loadImage(VacuumWorldView.IMGPATH
				+ VacuumWorldView.CONTROLIMGDIR + "restart_button" + VacuumWorldView.EXTENSION),
			new RestartOnClick(),
			VacuumWorldView.IMGLOADER.loadImage(VacuumWorldView.IMGPATH
				+ VacuumWorldView.CONTROLIMGDIR + "pause_button" + VacuumWorldView.EXTENSION),
			new PauseOnClick(),
			VacuumWorldView.IMGLOADER.loadImage(VacuumWorldView.IMGPATH + VacuumWorldView.CONTROLIMGDIR
				+ "settings_button" + VacuumWorldView.EXTENSION),
			new SettingsOnClick(), VacuumWorldView.IMGLOADER.loadImage(VacuumWorldView.IMGPATH
				+ VacuumWorldView.CONTROLIMGDIR + "off_button" + VacuumWorldView.EXTENSION),
			new OffOnClick()));
	selectionButtonPanel = new JPanel();

	int gs = 4;
	selectionButtonPanel.setLayout(new GridLayout(0, gs));
	selectionButtonPanel.setOpaque(false);
	for (int i = 0; i < gs; i++) {
	    addFilledPanel(selectionButtonPanel);
	}
	agentbuttons.forEach(selectionButtonPanel::add);
	addFilledPanel(selectionButtonPanel);
	userbuttons.forEach(selectionButtonPanel::add);
	addFilledPanel(selectionButtonPanel);
	addFilledPanel(selectionButtonPanel);
	dirtbuttons.forEach(selectionButtonPanel::add);
	for (int i = 0; i < 13; i++) {
	    addFilledPanel(selectionButtonPanel);
	}
	side.add(selectionButtonPanel, BorderLayout.CENTER);
	content.add(side, BorderLayout.EAST);
	content.add(selectiongrid, BorderLayout.CENTER);
	this.add(content, JLayeredPane.DEFAULT_LAYER);
	// rotate button
	this.rotate = new RotateButton(new RemoveOnClick());
	this.add(rotate, JLayeredPane.POPUP_LAYER);
	// drag content
	drag = new DragPanel();
	this.add(drag, JLayeredPane.DRAG_LAYER);
	// settings panel
	settings = new VacuumWorldViewSettingsPanel(new SaveOnClick(), new LoadOnClick(), new DoneOnClick());
	setMindMap();
	settings.setGridDimension(startparams.getDimension());
	settings.setSimulationRate(startparams.getSimulationRate());
	this.add(settings, JLayeredPane.POPUP_LAYER);
	// pause panel
	pausepanel = new PausePanel();
	pausepanel.setVisible(false);
	this.add(pausepanel, JLayeredPane.POPUP_LAYER);
	this.setFocusable(true);
	this.requestFocusInWindow();
    }

    private void setMindMap() {
	settings.setGreenClass(startparams.getMindmap().get(BodyColor.GREEN));
	settings.setOrangeClass(startparams.getMindmap().get(BodyColor.ORANGE));
	settings.setWhiteClass(startparams.getMindmap().get(BodyColor.WHITE));
    }

    private void addFilledPanel(JPanel panel) {
	JPanel p = new JPanel();
	p.setOpaque(false);
	panel.add(p);
    }

    public void dragMove(SelectorButton button, MouseEvent e) {
	drag.updateImage(button.getImage());
	drag.setLocation(selectiongrid
		.snapSelectionBox(SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), selectiongrid)));
    }

    public void place(SelectorButton button, MouseEvent e) {
	Position p = selectiongrid.getGridPosition(getGridPoint(e));
	if (p != null) {
	    if (agentbuttons.contains(button) || userbuttons.contains(button)) {
		if (VacuumWorldController.SINGLEAVATAR && button.color == BodyColor.AVATAR) {
		    // remove the button
		    button.setVisible(false);
		}
		VacuumWorldAgentAppearance app = new VacuumWorldAgentAppearance(null, null, null, null);
		app.setColor(button.color);
		app.setOrientation(Orientation.NORTH);
		app.setPosition(p);
		startparams.getAgentapps().put(p, app);
		rotate.setLocation(getRotatePoint(e));
		rotate.setVisible(true);
		rotate.setCurrent(app);
	    } else if (dirtbuttons.contains(button)) {
		DirtAppearance app = new DirtAppearance(button.color);
		app.setPosition(p);
		startparams.getDirtapps().put(p, app);
		rotate.setVisible(false);
		rotate.clear();
	    }
	}
    }

    public void clear() {
	rotate.clear();
	rotate.setVisible(false);
	startparams.getAgentapps().clear();
	startparams.getDirtapps().clear();
	VacuumWorldMainPanel.this.repaint();
    }

    public void remove(Position position) {
	if (position != null) {
	    rotate.clear();
	    rotate.setVisible(false);
	    startparams.getDirtapps().remove(position);
	    startparams.getAgentapps().remove(position);
	    VacuumWorldMainPanel.this.repaint();
	}
    }

    private Point getGridPoint(MouseEvent e) {
	return SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), selectiongrid);
    }

    private Point getRotatePoint(MouseEvent e) {
	Point p = selectiongrid
		.snapSelectionBox(SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), selectiongrid));
	p.x -= ROTATIONEXTRA / 2;
	p.y -= ROTATIONEXTRA / 2;
	return p;
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;
	g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	startparams.getDirtapps().forEach((position, app) -> selectiongrid.drawDirt(g2, position, app.getColor()));
	startparams.getAgentapps().forEach(
		(position, app) -> selectiongrid.drawAgent(g2, position, app.getColor(), app.getOrientation()));
    }

    public class VacuumWorldSelectionGridPanel extends GridPanel implements MouseListener {

	private static final long serialVersionUID = 7999533531378880749L;

	public VacuumWorldSelectionGridPanel(Dimension dimension, int griddimension) {
	    super(griddimension);
	    this.addMouseListener(this);
	    this.setPreferredSize(dimension);
	    this.setOpaque(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	    if (e.getClickCount() == 1) {
		Position p = this.getGridPosition(e.getPoint());
		if (startparams.getAgentapps().containsKey(p)) {
		    rotate.setCurrent(startparams.getAgentapps().get(p));
		    rotate.setLocation(getRotatePoint(e));
		    rotate.setVisible(true);
		}
	    } else {
		VacuumWorldMainPanel.this.remove(this.getGridPosition(e.getPoint()));
	    }
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	    // unused
	}

	@Override
	public void mouseExited(MouseEvent e) {
	    // unused
	}

	@Override
	public void mousePressed(MouseEvent e) {
	    // unused
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	    // unused
	}
    }

    public class LoadOnClick implements OnClick {
	@Override
	public void onClick(Clickable arg, MouseEvent e) {
	    StartParameters params = SaveManager.jFileChooserLoad();
	    if (params != null) {
		startparams = params;
		selectiongrid.gridDimension = params.getDimension();
		settings.setGreenClass(params.getMindmap().get(BodyColor.GREEN));
		settings.setOrangeClass(params.getMindmap().get(BodyColor.ORANGE));
		settings.setWhiteClass(params.getMindmap().get(BodyColor.WHITE));
		settings.setGridDimension(params.getDimension());
		settings.setSimulationRate(params.getSimulationRate());
		settings.updateFields();
		settings.repaint();
	    }
	}
    }

    public class SaveOnClick implements OnClick {
	@Override
	public void onClick(Clickable arg, MouseEvent e) {
	    // update mind map
	    startparams.setMindmap(getMindMap());
	    startparams.setDimension(settings.getGridDimension());
	    startparams.setSimulationRate(settings.getSimulationRate());
	    SaveManager.jFileChooserSave(startparams);
	}
    }

    public class DoneOnClick implements OnClick {
	@Override
	public void onClick(Clickable arg, MouseEvent e) {
	    unpause();
	    if (selectiongrid.getGriddimension() != settings.getGridDimension()) {
		selectiongrid.setGridDimension(settings.getGridDimension());
		startparams.clearAgentsAndDirts();
	    }
	    startparams.setMindmap(getMindMap());
	    startparams.setDimension(settings.getGridDimension());
	    startparams.setSimulationRate(settings.getSimulationRate());
	    resizeComponents();
	    settings.setVisible(false);
	    VacuumWorldMainPanel.this.setFocusable(true);
	    VacuumWorldMainPanel.this.requestFocusInWindow();
	    revalidate();
	    repaint();
	}
    }

    private Map<BodyColor, Class<?>> getMindMap() {
	Map<BodyColor, Class<?>> result = new EnumMap<>(BodyColor.class);
	result.put(BodyColor.GREEN, settings.getGreenclass());
	result.put(BodyColor.ORANGE, settings.getOrangeclass());
	result.put(BodyColor.WHITE, settings.getWhiteclass());
	return result;
    }

    private class RemoveOnClick implements OnClick {
	@Override
	public void onClick(Clickable arg, MouseEvent e) {
	    VacuumWorldMainPanel.this.remove(selectiongrid.getGridPosition(getGridPoint(e)));
	}
    }

    public class SettingsOnClick implements OnClick {

	@Override
	public void onClick(Clickable arg, MouseEvent e) {
	    new PauseOnClick().onClick(null, null);
	    int gw = VacuumWorldMainPanel.this.getWidth() / 20;
	    settings.setBounds(gw, gw, VacuumWorldMainPanel.this.getWidth() - (gw * 2),
		    VacuumWorldMainPanel.this.getHeight() - (gw * 2));
	    settings.setOpaque(true);
	    settings.setVisible(true);
	    rotate.setVisible(false);
	    rotate.clear();
	    drag.setVisible(false);
	    VacuumWorldMainPanel.this.repaint();
	}
    }

    private void unpause() {
	if (view.pause.isPaused()) {
	    view.pause.unpause();
	    pausepanel.setVisible(false);
	}
    }

    public void hideSelectionButtons() {
	agentbuttons.forEach(b -> b.setVisible(false));
	userbuttons.forEach(b -> b.setVisible(false));
	dirtbuttons.forEach(b -> b.setVisible(false));
    }

    public class OffOnClick implements OnClick {
	@Override
	public void onClick(Clickable arg, MouseEvent e) {
	    System.exit(0);
	}
    }

    public class RestartOnClick implements OnClick {
	@Override
	public void onClick(Clickable arg, MouseEvent e) {
	    unpause();
	    addKeyListener(VacuumWorldMainPanel.this);
	    VacuumWorldMainPanel.this.requestFocus();
	    startparams.getAgentapps().clear();
	    startparams.getDirtapps().clear();
	    settings.showLoadButton();
	    settings.showSaveButton();
	    rotate.clear();
	    rotate.setVisible(false);
	    repaint();
	    showSelectionButtons();
	    if (simulating) {
		simulating = false;
		// stop the universe
		view.restart.restart();
		// clear the view
		switchToSelectionPanel();
	    }
	}

	private void showSelectionButtons() {
	    agentbuttons.forEach(b -> b.setVisible(true));
	    userbuttons.forEach(b -> b.setVisible(true));
	    dirtbuttons.forEach(b -> b.setVisible(true));
	}

	private void switchToSelectionPanel() {
	    content.remove(simulationgrid);
	    content.add(selectiongrid, BorderLayout.CENTER);
	    revalidate();
	    repaint();
	}
    }

    public class PlayOnClick implements OnClick {
	@Override
	public void onClick(Clickable arg, MouseEvent e) {	    
	    requestFocusInWindow(); // to get user input
	    if (!simulating) {
		simulating = true;
		view.start(startparams);
		hideSelectionButtons();
		settings.hideLoadButton();
		settings.hideSaveButton();
		clear();
		switchToSimulationPanel();
		removeKeyListener(VacuumWorldMainPanel.this);
	    } else {
		if (view.pause.isPaused()) {
		    view.pause.unpause();
		    pausepanel.setVisible(false);
		}
	    }
	}

	private void switchToSimulationPanel() {	    
	    simulationgrid.start();
	    content.remove(selectiongrid);
	    content.add(simulationgrid, BorderLayout.CENTER);
	    revalidate();
	    repaint();
	}
    }

    public class PauseOnClick implements OnClick {
	@Override
	public void onClick(Clickable arg, MouseEvent e) {
	    if (simulating) {
		pausepanel.setBounds(0, 0, getWidth() - side.getWidth(), getHeight());
		pausepanel.setVisible(true);
		view.pause.pause();
	    }
	}
    }

    public class SelectorButton extends CustomButton implements MouseMotionListener {

	private static final long serialVersionUID = 731055277706069478L;
	private BodyColor color;

	public SelectorButton(BufferedImage image, BodyColor color, OnClick onclick) {
	    super(image, ImageUtilities.getOverlayedImage(image, Color.BLACK, 0.2f),
		    ImageUtilities.getOverlayedImage(image, Color.BLACK, 0.1f), onclick);
	    this.addMouseMotionListener(this);
	    this.color = color;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	    // do nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	    super.mouseReleased(e);
	    place(this, e);
	    drag.setVisible(false);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	    this.isPressed = true;
	    if (!drag.isVisible()) {
		drag.setVisible(true);
	    }
	    dragMove(this, e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	    // do nothing
	}
    }

    private void resizeComponents() {
	if (drag != null) {
	    drag.setSize(selectiongrid.getWidth() / selectiongrid.gridDimension,
		    selectiongrid.getHeight() / selectiongrid.gridDimension);
	}
	if (rotate != null) {
	    rotate.setSize(selectiongrid.getWidth() / selectiongrid.gridDimension + ROTATIONEXTRA,
		    selectiongrid.getHeight() / selectiongrid.gridDimension + ROTATIONEXTRA);
	}
    }

    public class ResizeListener implements ComponentListener {

	@Override
	public void componentResized(ComponentEvent e) {
	    if (content != null) {
		content.setBounds(0, 0, e.getComponent().getWidth(), e.getComponent().getHeight());
	    }
	    drag.setVisible(false);
	    rotate.setVisible(false);
	    rotate.clear();
	    resizeComponents();
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	    return;
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	    return;
	}

	@Override
	public void componentShown(ComponentEvent e) {
	    return;
	}
    }

    @Override
    public void keyPressed(KeyEvent e) {
	if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	    settings.setVisible(false);
	}
	if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
	    rotate.rotateLeft();
	} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
	    rotate.rotateRight();
	}
	this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
	// unused
    }

    @Override
    public void keyTyped(KeyEvent e) {
	// unused
    }
}
