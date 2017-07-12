package uk.ac.rhul.cs.dice.vacuumworld.MVC;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import uk.ac.rhul.cs.dice.vacuumworld.saveload.Loader;
import uk.ac.rhul.cs.dice.vacuumworld.saveload.Saver;

public class SaveManager {

	public static final String SAVEEXTENSION = ".vwc";
	public static final String DEFAULTSAVEFILE = "save\\defaultsave.txt";
	public static final File SAVEPATH;

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
