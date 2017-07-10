package uk.ac.rhul.cs.dice.vacuumworld.MVC.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.rhul.cs.dice.vacuumworld.MVC.VacuumWorldController;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons.DefaultButton;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons.DoneButton;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons.OnClick;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.buttons.SaveButton;

public class VacuumWorldViewSettingsPanel extends JPanel implements
		MouseListener {

	private static final long serialVersionUID = 1252849880132919023L;
	private static final String AVATARBUTTONFILENAME = "avatar_button";

	private Class<?>[] possibleminds = VacuumWorldController.POSSIBLEMINDS
			.toArray(new Class<?>[0]);
	private JPanel combopanel;
	private JComboBox<Class<?>> greencombo = new JComboBox<>();
	private JComboBox<Class<?>> orangecombo = new JComboBox<>();
	private JComboBox<Class<?>> whitecombo = new JComboBox<>();
	private JFormattedTextField dimensiontf = new JFormattedTextField(
			NumberFormat.getIntegerInstance());
	private JFormattedTextField simulationRatetf = new JFormattedTextField(
			NumberFormat.getIntegerInstance());
	private DefaultButton avatarbtn;
	private SaveButton savebtn;
	private DoneButton donebtn;
	private Integer gridDimension = VacuumWorldController.DEFAULTGRIDDIMENSION;
	private Integer simulationRate = VacuumWorldController.DEFAULTSIMULATIONRATE;

	public VacuumWorldViewSettingsPanel(OnClick save, OnClick done) {
		this.addMouseListener(this);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.setLayout(new GridLayout(2, 1));
		avatarbtn = new DefaultButton(AVATARBUTTONFILENAME, null);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.NORTH;
		this.add(initSaveDoneButtonPanel(save, done));

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTH;
		this.add(initMindComboPanel());

	}

	private JPanel initSaveDoneButtonPanel(OnClick save, OnClick done) {
		GridBagConstraints c = new GridBagConstraints();
		JPanel buttonpanel = new JPanel();
		buttonpanel.setLayout(new GridBagLayout());
		buttonpanel.setBackground(Color.WHITE);
		buttonpanel.setOpaque(true);
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.SOUTHEAST;
		savebtn = new SaveButton(save);
		savebtn.setPreferredSize(new Dimension(110, 30));
		buttonpanel.add(savebtn, c);
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.SOUTHEAST;
		donebtn = new DoneButton(done);
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
		JLabel gl = new JLabel("Green Mind");
		JLabel ol = new JLabel("Orange Mind");
		JLabel wl = new JLabel("White Mind");
		JLabel dl = new JLabel("Dimension");
		JLabel rl = new JLabel("Simulation Rate");
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

	public Integer getSimulationRate() {
		return simulationRate;
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

}
