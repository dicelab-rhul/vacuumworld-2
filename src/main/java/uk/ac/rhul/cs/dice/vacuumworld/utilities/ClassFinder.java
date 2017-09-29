package uk.ac.rhul.cs.dice.vacuumworld.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ClassFinder {

	private ClassFinder() {
	}

	private static final String CLASSEXTENSION = ".class";
	private static final String JAR = ".jar";

	public static Collection<Class<?>> findAllClasses()
			throws ClassFinderException {
		Collection<Class<?>> classes = new HashSet<>();
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		URL[] urls = ((URLClassLoader) loader).getURLs();
		for (URL u : urls) {
			try {
				File root = new File(u.toURI());
				Collection<String> names;
				if (root.getName().endsWith(JAR)) {
					names = getClassNamesFromJar(root);
				} else {
					names = recurseDirectories(root, root);
				}
				for (String name : names) {
					// THIS STEP IS TO ENSURE THAT FROM ECLIPSE YOU WON'T GET A
					// CLASSNOTFOUNDEXCEPTION!!!
					if (name.startsWith(u.getPath())) {
						name = name.substring(u.getPath().length()).replace(
								"/", ".");
					}
					classes.add(loader.loadClass(name));
				}
			} catch (ClassNotFoundException | URISyntaxException e) {
				throw new ClassFinderException(e);
			}
		}
		return classes;
	}

	public static Collection<String> getClassNamesFromJar(File jar)
			throws ClassFinderException {
		Collection<String> names = new HashSet<>();
		ZipInputStream zip = null;
		try (FileInputStream fins = new FileInputStream(jar)) {
			zip = new ZipInputStream(fins);
			for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip
					.getNextEntry()) {
				if (!entry.isDirectory()
						&& entry.getName().endsWith(CLASSEXTENSION)) {
					String name = entry.getName();
					name = name.substring(0,
							name.length() - CLASSEXTENSION.length());
					names.add(name.replace('/', '.'));
				}
			}
			fins.close();
			zip.close();
		} catch (IOException e) {
			throw new ClassFinderException(e);
		}
		return names;
	}

	public static Collection<Class<?>> getAnnotatedClasses(
			Class<? extends Annotation> annotation, Collection<Class<?>> classes) {
		Collection<Class<?>> annotatedClasses = new HashSet<>();
		for (Class<?> c : classes) {
			if (c.getAnnotation(annotation) != null) {
				annotatedClasses.add(c);
			}
		}
		return annotatedClasses;
	}

	public static Collection<Class<?>> getSubClasses(Class<?> superclass,
			Collection<Class<?>> classes) {
		Collection<Class<?>> subclasses = new HashSet<>();
		for (Class<?> c : classes) {
			if (superclass.isAssignableFrom(c)) {
				subclasses.add(c);
			}
		}
		return subclasses;
	}

	private static Collection<String> recurseDirectories(File file, File root) {
		Collection<String> classnames = new HashSet<>();
		File[] dirs = getDirectories(file);
		if (dirs != null && dirs.length > 0) {
			for (File f : dirs) {
				classnames.addAll(recurseDirectories(f, root));
			}
		}
		File[] classfiles = getClassFiles(file);
		for (File f : classfiles) {
			classnames.add(f.getAbsolutePath()
					.replace(root.getAbsolutePath() + "\\", "")
					.replace(CLASSEXTENSION, "").replace("\\", "."));
		}
		return classnames;
	}

	private static File[] getClassFiles(File file) {
		return file.listFiles(new ClassFileFilter());
	}

	private static File[] getDirectories(File file) {
		return file.listFiles(new DirectoryFilter());
	}

	private static class ClassFileFilter implements FilenameFilter {
		@Override
		public boolean accept(File current, String name) {
			if (new File(current, name).isFile()) {
				return CLASSEXTENSION.equals(name.substring(name.indexOf('.')));
			}
			return false;
		}
	}

	private static class DirectoryFilter implements FilenameFilter {
		@Override
		public boolean accept(File current, String name) {
			return new File(current, name).isDirectory();
		}
	}

	/**
	 * The {@link Exception} that is thrown by methods in the
	 * {@link ClassFinder} class for any reason that results in the failure to
	 * produce desired output. i.e. to properly find any classes.
	 * 
	 * @author Ben Wilkins
	 *
	 */
	public static class ClassFinderException extends Exception {

		private static final long serialVersionUID = 5574666660440435064L;
		private static final String MESSAGE = "Failed to find class.";

		public ClassFinderException() {
			super(MESSAGE);
		}

		public ClassFinderException(Throwable cause) {
			super(MESSAGE, cause);
		}
	}
}
