package uk.ac.rhul.cs.dice.vacuumworld.MVC.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

import uk.ac.rhul.cs.dice.vacuumworld.MVC.VacuumWorldController;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons.DefaultButton;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons.OnClick;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;

public class VacuumWorldViewSettingsPanel extends JPanel implements
		MouseListener {

	private static final long serialVersionUID = 1252849880132919023L;
	private static final String AVATARBUTTONFILENAME = "avatar_button";
	private static final String DONEBUTTONFILENAME = "done_button";
	private static final String SAVEBUTTONFILENAME = "save_button";
	private static final String LOADBUTTONFILENAME = "load_button";

	private Class<?>[] possibleminds = VacuumWorldController.POSSIBLEAGENTMINDS
			.toArray(new Class<?>[0]);
	private JPanel combopanel;
	private JComboBox<Class<?>> greencombo = new JComboBox<>();
	private JComboBox<Class<?>> orangecombo = new JComboBox<>();
	private JComboBox<Class<?>> whitecombo = new JComboBox<>();
	private JFormattedTextField dimensiontf = new JFormattedTextField(
			NumberFormat.getIntegerInstance());
	private JFormattedTextField simulationRatetf = new JFormattedTextField(
			NumberFormat.getIntegerInstance());
	// private DefaultButton avatarbtn;
	private SettingsButton savebtn;
	private SettingsButton donebtn;
	private SettingsButton loadbtn;

	private Integer gridDimension = VacuumWorldController.DEFAULTGRIDDIMENSION;
	private Integer simulationRate = VacuumWorldController.DEFAULTSIMULATIONRATE;

	public VacuumWorldViewSettingsPanel(OnClick save, OnClick load, OnClick done) {
		this.addMouseListener(this);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.setLayout(new GridBagLayout());
		// avatarbtn = new DefaultButton(AVATARBUTTONFILENAME, null);
		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(10, 10, 10, 10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		this.add(initMindComboPanel(), c);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.5;
		c.anchor = GridBagConstraints.SOUTHEAST;
		this.add(initSaveDoneLoadButtonPanel(save, load, done), c);
	}

	private JPanel initSaveDoneLoadButtonPanel(OnClick save, OnClick load,
			OnClick done) {
		GridBagConstraints c = new GridBagConstraints();
		JPanel buttonpanel = new JPanel();
		buttonpanel.setLayout(new GridBagLayout());
		buttonpanel.setPreferredSize(new Dimension(3 * 110, 3 * 30));
		buttonpanel.setOpaque(false);
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.SOUTHEAST;
		savebtn = new SettingsButton(SAVEBUTTONFILENAME, save);
		savebtn.setPreferredSize(new Dimension(110, 30));
		buttonpanel.add(savebtn, c);
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.SOUTHEAST;
		loadbtn = new SettingsButton(LOADBUTTONFILENAME, load);
		loadbtn.setPreferredSize(new Dimension(110, 30));
		buttonpanel.add(loadbtn, c);
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.SOUTHEAST;
		donebtn = new SettingsButton(DONEBUTTONFILENAME, done);
		donebtn.setPreferredSize(new Dimension(110, 30));
		buttonpanel.add(donebtn, c);
		return buttonpanel;
	}

	private JPanel initMindComboPanel() {
		combopanel = new JPanel();
		GroupLayout layout = new GroupLayout(combopanel);
		combopanel.setLayout(layout);
		combopanel.setOpaque(false);
		combopanel.setPreferredSize(new Dimension(100, 100));
		dimensiontf.addPropertyChangeListener("value",
				new DimensionPropertyListener());
		dimensiontf.setText(String
				.valueOf(VacuumWorldController.DEFAULTGRIDDIMENSION));
		simulationRatetf.setText(String
				.valueOf(VacuumWorldController.DEFAULTSIMULATIONRATE));
		simulationRatetf.addPropertyChangeListener("value",
				new SimulationRatePropertyListener());
		this.gridDimension = VacuumWorldController.DEFAULTGRIDDIMENSION;

		Font labelFont = new Font("Arial", Font.PLAIN, 14);
		JLabel gl = new JLabel("Green Mind: ");
		gl.setFont(labelFont);
		JLabel ol = new JLabel("Orange Mind: ");
		ol.setFont(labelFont);
		JLabel wl = new JLabel("White Mind: ");
		wl.setFont(labelFont);
		JLabel dl = new JLabel("Dimension: ");
		dl.setFont(labelFont);
		JLabel rl = new JLabel("Simulation Rate: ");
		rl.setFont(labelFont);

		layout.setAutoCreateGaps(true);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		horizontal.addGroup(layout.createParallelGroup().addComponent(gl)
				.addComponent(ol).addComponent(wl).addComponent(dl)
				.addComponent(rl));
		horizontal.addGroup(layout.createParallelGroup()
				.addComponent(simulationRatetf).addComponent(dimensiontf)
				.addComponent(initComboBox(greencombo))
				.addComponent(initComboBox(orangecombo))
				.addComponent(initComboBox(whitecombo)));
		horizontal.addGroup(layout.createParallelGroup());
		layout.setHorizontalGroup(horizontal);
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		vertical.addGroup(layout.createParallelGroup(Alignment.LEADING)
				.addComponent(rl).addComponent(simulationRatetf));
		vertical.addGroup(layout.createParallelGroup(Alignment.LEADING)
				.addComponent(dl).addComponent(dimensiontf));
		vertical.addGroup(layout.createParallelGroup(Alignment.LEADING)
				.addComponent(gl).addComponent(greencombo));
		vertical.addGroup(layout.createParallelGroup(Alignment.LEADING)
				.addComponent(ol).addComponent(orangecombo));
		vertical.addGroup(layout.createParallelGroup(Alignment.LEADING)
				.addComponent(wl).addComponent(whitecombo));
		layout.setVerticalGroup(vertical);
		return combopanel;
	}

	private JComboBox<Class<?>> initComboBox(JComboBox<Class<?>> box) {
		for (Class<?> c : possibleminds) {
			box.addItem(c);
		}
		return box;
	}

	public void updateFields() {
		this.dimensiontf.setValue(this.gridDimension);
		this.simulationRatetf.setValue(this.simulationRate);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		this.combopanel.setPreferredSize(new Dimension(width, height));
		this.revalidate();
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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

	public Integer getGridDimension() {
		return gridDimension;
	}

	public void setGridDimension(Integer gridDimension) {
		this.gridDimension = gridDimension;
	}

	public Integer getSimulationRate() {
		return simulationRate;
	}

	public void setSimulationRate(Integer simulationRate) {
		this.simulationRate = simulationRate;
	}

	public void setGreenClass(Class<?> c) {
		greencombo.setSelectedItem(c);
	}

	public void setOrangeClass(Class<?> c) {
		orangecombo.setSelectedItem(c);
	}

	public void setWhiteClass(Class<?> c) {
		whitecombo.setSelectedItem(c);
	}

	public Class<?> getGreenclass() {
		return (Class<?>) greencombo.getSelectedItem();
	}

	public Class<?> getOrangeclass() {
		return (Class<?>) orangecombo.getSelectedItem();
	}

	public Class<?> getWhiteclass() {
		return (Class<?>) whitecombo.getSelectedItem();
	}

	private class SimulationRatePropertyListener implements
			PropertyChangeListener {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getNewValue() != null) {
				simulationRate = Integer.valueOf(evt.getNewValue().toString());
			} else {
				simulationRate = VacuumWorldController.DEFAULTSIMULATIONRATE;
				simulationRatetf
						.setValue(VacuumWorldController.DEFAULTSIMULATIONRATE);
			}
		}
	}

	private class DimensionPropertyListener implements PropertyChangeListener {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getNewValue() != null) {
				gridDimension = Integer.valueOf(evt.getNewValue().toString());
			} else {
				gridDimension = VacuumWorldController.DEFAULTGRIDDIMENSION;
				dimensiontf
						.setValue(VacuumWorldController.DEFAULTGRIDDIMENSION);
			}
		}
	}

	private class SettingsButton extends DefaultButton {

		private static final long serialVersionUID = 5209655783650341511L;

		public SettingsButton(String imgname, OnClick onclick) {
			super(imgname, onclick);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			this.setFocusable(true);
			this.requestFocusInWindow();
			super.mousePressed(e);
		}
	}

	public void hideSaveButton() {
		savebtn.setVisible(false);
	}

	public void showSaveButton() {
		savebtn.setVisible(true);
	}

	public void hideLoadButton() {
		loadbtn.setVisible(false);
	}

	public void showLoadButton() {
		loadbtn.setVisible(true);
	}
}
