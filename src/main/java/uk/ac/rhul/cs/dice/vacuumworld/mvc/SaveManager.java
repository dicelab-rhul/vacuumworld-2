package uk.ac.rhul.cs.dice.vacuumworld.mvc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorld;
import uk.ac.rhul.cs.dice.vacuumworld.agent.minds.VacuumWorldExampleMind;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.saveload.Loader;
import uk.ac.rhul.cs.dice.vacuumworld.saveload.Saver;

public class SaveManager {

	public static final String SAVEFOLDERNAME = "save";
	public static final String SAVEEXTENSION = ".vwc";
	public static final String DEFAULTSAVEFILE = "defaultsave";
	public static final File SAVEPATH;

	public static final Integer DEFAULTGRIDDIMENSION = 10;
	public static final Integer DEFAULTSIMULATIONRATE = 500;
	public static final Class<?> DEFAULTAGENTMIND = VacuumWorldExampleMind.class;

	private static final VWCFileFilter FILEFILTER = new VWCFileFilter();

	private SaveManager() {
	}

	static {
		File savepath = null;
		try {
			savepath = new File(new File("").getCanonicalPath(), SAVEFOLDERNAME);
		} catch (IOException e) {
			VacuumWorld.LOGGER.log(Level.SEVERE,
					"Failed to get valid save folder path", e);
		}
		SAVEPATH = savepath;
		if (!savepath.isDirectory() && !savepath.mkdir()) {
			VacuumWorld.LOGGER.log(Level.WARNING, "Could not make a save folder, please check system permissions.");
			//TODO disable saving on failure
		}
	}

	public static StartParameters loadDefault() throws IOException {
		File f = new File(SAVEPATH.getCanonicalPath(), DEFAULTSAVEFILE
				+ SAVEEXTENSION);
		if (f.isFile()) {
			return (StartParameters) Loader.load(f);
		}
		StartParameters dsp = getHardDefault();
		// save the default
		Saver.save(f, getHardDefault());
		return dsp;
	}

	public static StartParameters getHardDefault() {
		return new StartParameters(DEFAULTGRIDDIMENSION, DEFAULTSIMULATIONRATE,
				new HashMap<>(), new HashMap<>(), getDefaultAgentMinds());
	}

	private static Map<BodyColor, Class<?>> getDefaultAgentMinds() {
		EnumMap<BodyColor, Class<?>> result = new EnumMap<>(BodyColor.class);
		result.put(BodyColor.GREEN, DEFAULTAGENTMIND);
		result.put(BodyColor.ORANGE, DEFAULTAGENTMIND);
		result.put(BodyColor.WHITE, DEFAULTAGENTMIND);
		return result;
	}

	public static void saveDefault(StartParameters params) {
		URL ds = SaveManager.class.getClassLoader()
				.getResource(DEFAULTSAVEFILE);
		Saver.save(new File(ds.getPath()), params);
	}

	public static StartParameters jFileChooserLoad() {
		JFileChooser chooser = new JFileChooser(SAVEPATH);
		FILEFILTER.setMode("Load");
		chooser.addChoosableFileFilter(FILEFILTER);
		chooser.setFileFilter(FILEFILTER);
		StartParameters loaded = null;
		if (chooser.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (!file.getName().endsWith(SAVEEXTENSION)) {
				file = new File(file.getAbsolutePath() + SAVEEXTENSION);
			}
			loaded = (StartParameters) Loader.load(file);
			if (loaded == null) {
				System.err.println("Failed to load save file: "
						+ file.getAbsolutePath());
			}
		}
		return loaded;
	}

	public static void jFileChooserSave(StartParameters toSave) {
		JFileChooser chooser = new JFileChooser(SAVEPATH);
		FILEFILTER.setMode("Save");
		chooser.addChoosableFileFilter(FILEFILTER);
		chooser.setFileFilter(FILEFILTER);
		if (chooser.showSaveDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (!file.getName().endsWith(SAVEEXTENSION)) {
				file = new File(file.getAbsolutePath() + SAVEEXTENSION);
			}
			Saver.save(file, toSave);
		}
	}

	private static class VWCFileFilter extends FileFilter {

		private String mode;

		private void setMode(String mode) {
			this.mode = mode;
		}

		@Override
		public boolean accept(File file) {
			return file.isDirectory()
					|| file.getName().toLowerCase().endsWith(SAVEEXTENSION);
		}

		@Override
		public String getDescription() {
			return mode + " a " + SAVEEXTENSION + " file";
		}
	}
}
