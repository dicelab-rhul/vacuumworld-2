package uk.ac.rhul.cs.dice.vacuumworld.MVC;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import uk.ac.rhul.cs.dice.vacuumworld.agent.minds.VacuumWorldExampleMind;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.saveload.Loader;
import uk.ac.rhul.cs.dice.vacuumworld.saveload.Saver;

public class SaveManager {

	public static final String SAVEEXTENSION = ".vwc";
	public static final String DEFAULTSAVEFILE = "defaultsave";
	public static final File SAVEPATH;

	public static final Integer DEFAULTGRIDDIMENSION = 10;
	public static final Integer DEFAULTSIMULATIONRATE = 500;
	public static final Class<?> DEFAULTAGENTMIND = VacuumWorldExampleMind.class;

	private static final VWCFileFilter FILEFILTER = new VWCFileFilter();

	static {
		File savepath = null;
		try {
			savepath = new File(new File("").getCanonicalPath() + "\\save\\");
		} catch (IOException e) {
			System.err.println("Something went wrong finding save path");
			e.printStackTrace();
		}
		SAVEPATH = savepath;
		if (!savepath.isDirectory()) {
			if (!savepath.mkdir()) {
				System.err
						.println("Could not make a save folder, please check system permissions.");
			}
		}
	}

	public static StartParameters loadDefault() throws ClassNotFoundException,
			IOException {
		File f = new File(SAVEPATH + "\\" + DEFAULTSAVEFILE + SAVEEXTENSION);
		if (f.isFile()) {
			System.out.println("Loading default file: " + f);
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
		Map<BodyColor, Class<?>> result = new HashMap<>();
		result.put(BodyColor.GREEN, DEFAULTAGENTMIND);
		result.put(BodyColor.ORANGE, DEFAULTAGENTMIND);
		result.put(BodyColor.WHITE, DEFAULTAGENTMIND);
		return result;
	}

	public static void saveDefault(StartParameters params) throws IOException {
		URL ds = SaveManager.class.getClassLoader()
				.getResource(DEFAULTSAVEFILE);
		Saver.save(new File(ds.getPath()), params);
	}

	public static StartParameters jFileChooserLoad() {
		JFileChooser chooser = new JFileChooser(SAVEPATH);
		FILEFILTER.setMode("Load");
		chooser.addChoosableFileFilter(FILEFILTER);
		chooser.setFileFilter(FILEFILTER);
		if (chooser.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (!file.getName().endsWith(SAVEEXTENSION)) {
				file = new File(file.getAbsolutePath() + SAVEEXTENSION);
			}
			try {
				return (StartParameters) Loader.load(file);
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Failed to load save file: "
						+ file.getAbsolutePath());
			}
		}
		return null;
	}

	public static void jFileChooserSave(StartParameters toSave)
			throws IOException {
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
