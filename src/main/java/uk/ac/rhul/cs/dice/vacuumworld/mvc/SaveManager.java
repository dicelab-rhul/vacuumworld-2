package uk.ac.rhul.cs.dice.vacuumworld.mvc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import uk.ac.rhul.cs.dice.vacuumworld.agent.minds.VacuumWorldExampleMind;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.saveload.Loader;
import uk.ac.rhul.cs.dice.vacuumworld.saveload.Saver;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.LogUtils;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.VacuumWorldSaveLoadException;

public class SaveManager {

    public static final String SAVEFOLDERNAME = "save";
    public static final String SAVEEXTENSION = ".vwc";
    public static final String DEFAULTSAVEFILE = "defaultsave";
    public static final File SAVEPATH;

    public static final Integer DEFAULTGRIDDIMENSION = 10;
    public static final Integer DEFAULTSIMULATIONRATE = 500;
    public static final Class<?> DEFAULTAGENTMIND = VacuumWorldExampleMind.class;

    private static final VWCFileFilter FILEFILTER = new VWCFileFilter();

    private static final String DISABLEMESSAGE = "Save/Load initialisation failed. Saving and loading have been disabled.";
    private static boolean disable = false;

    private SaveManager() {
    }

    static {
	File savepath = null;
	try {
	    savepath = new File(new File("").getCanonicalPath(), SAVEFOLDERNAME);
	} catch (IOException e) {
	    LogUtils.log(e);
	    disable = true;
	}
	SAVEPATH = savepath;
	if (savepath != null && !savepath.isDirectory() && !savepath.mkdir()) {
	    String message = "Could not make a save folder, please check system permissions." + System.lineSeparator()
		    + DISABLEMESSAGE;
	    LogUtils.log(message);
	    disable = true;
	}
    }

    public static StartParameters loadDefault() {
	if (disable) {
	    return getHardDefault();
	}
	File f;
	try {
	    f = new File(SAVEPATH.getCanonicalPath(), DEFAULTSAVEFILE + SAVEEXTENSION);
	    if (f.isFile()) {
		StartParameters startparams = load(f);
		if (startparams != null) {
		    return startparams;
		}
	    } else {
		// the file does exist, this may be because of first time set up
		save(f, getHardDefault());
	    }
	} catch (IOException ioe) {
	    LogUtils.log(ioe);
	}
	// failed to get he default file for some reason
	return getHardDefault();
    }

    private static StartParameters load(File file) {
	try {
	    return (StartParameters) Loader.load(file);
	} catch (RuntimeException | VacuumWorldSaveLoadException e) {
	    LogUtils.log(e);
	}
	return null;
    }

    private static void save(File file, StartParameters params) {
	try {
	    Saver.save(file, params);
	} catch (RuntimeException | VacuumWorldSaveLoadException e) {
	    LogUtils.log(e);
	}
    }

    public static StartParameters getHardDefault() {
	return new StartParameters(DEFAULTGRIDDIMENSION, DEFAULTSIMULATIONRATE, new HashMap<>(), new HashMap<>(),
		getDefaultAgentMinds());
    }

    private static Map<BodyColor, Class<?>> getDefaultAgentMinds() {
	EnumMap<BodyColor, Class<?>> result = new EnumMap<>(BodyColor.class);
	result.put(BodyColor.GREEN, DEFAULTAGENTMIND);
	result.put(BodyColor.ORANGE, DEFAULTAGENTMIND);
	result.put(BodyColor.WHITE, DEFAULTAGENTMIND);
	return result;
    }

    public static void saveDefault(StartParameters params) {
	URL ds = SaveManager.class.getClassLoader().getResource(DEFAULTSAVEFILE);
	save(new File(ds.getPath()), params);
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
	    try {
		loaded = (StartParameters) Loader.load(file);
	    } catch (VacuumWorldSaveLoadException e) {
		LogUtils.log(e);
		return getHardDefault();
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
	    save(file, toSave);
	}
    }

    private static class VWCFileFilter extends FileFilter {

	private String mode;

	private void setMode(String mode) {
	    this.mode = mode;
	}

	@Override
	public boolean accept(File file) {
	    return file.isDirectory() || file.getName().toLowerCase().endsWith(SAVEEXTENSION);
	}

	@Override
	public String getDescription() {
	    return mode + " a " + SAVEEXTENSION + " file";
	}
    }
}