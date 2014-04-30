package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.interfaces.Pluggable;

public class PluginLoader {

	/**
	 * Laedt alle konfigurierten Plugins aus dem plugin-Verzeichnis
	 * 
	 * 
	 * @param pluginDir
	 *            Verzeichnis, in dem sich die Plugins befinden
	 * @param plugins
	 *            Map<String, String>, Key = Plugin-Klassenname, Value =
	 *            URL-Path
	 * @return Map<String, Pluggable> -> Map mit URL-Path als Key und das Plugin
	 *         als Value
	 * @throws IOException
	 */
	public static Map<String, Pluggable> loadPlugins(File pluginDir,
			Map<String, String> plugins) throws IOException {

		File[] plugJars = pluginDir.listFiles(new JARFileFilter());
		ClassLoader cl = new URLClassLoader(
				PluginLoader.fileArrayToURLArray(plugJars));
		List<Class<Pluggable>> plugClasses = PluginLoader
				.extractClassesFromJARs(plugJars, cl);
		return PluginLoader.createPluggableObjects(plugClasses, plugins);
	}

	private static URL[] fileArrayToURLArray(File[] files)
			throws MalformedURLException {

		if (files == null) {
			return new URL[0];
		}

		URL[] urls = new URL[files.length];
		for (int i = 0; i < files.length; i++) {
			urls[i] = files[i].toURI().toURL();
		}
		return urls;
	}

	private static List<Class<Pluggable>> extractClassesFromJARs(File[] jars,
			ClassLoader cl) throws IOException {

		if (jars == null) {
			return new ArrayList<Class<Pluggable>>();
		}

		List<Class<Pluggable>> classes = new ArrayList<Class<Pluggable>>();
		for (File jar : jars) {
			classes.addAll(PluginLoader.extractClassesFromJAR(jar, cl));
		}
		return classes;
	}

	@SuppressWarnings("unchecked")
	private static List<Class<Pluggable>> extractClassesFromJAR(File jar,
			ClassLoader cl) throws IOException {

		List<Class<Pluggable>> classes = new ArrayList<Class<Pluggable>>();
		JarInputStream jaris = new JarInputStream(new FileInputStream(jar));
		JarEntry ent = null;
		while ((ent = jaris.getNextJarEntry()) != null) {
			if (ent.getName().toLowerCase().endsWith(".class")) {
				try {
					Class<?> cls = cl.loadClass(ent.getName()
							.substring(0, ent.getName().length() - 6)
							.replace('/', '.'));
					if (PluginLoader.isPluggableClass(cls)) {
						classes.add((Class<Pluggable>) cls);
					}
				} catch (ClassNotFoundException e) {
					System.err.println("Can't load Class " + ent.getName());
					e.printStackTrace();
				}
			}
		}
		jaris.close();
		return classes;
	}

	private static boolean isPluggableClass(Class<?> cls) {

		for (Class<?> i : cls.getInterfaces()) {
			if (i.equals(Pluggable.class)) {
				return true;
			}
		}
		return false;
	}

	private static Map<String, Pluggable> createPluggableObjects(
			List<Class<Pluggable>> pluggables, Map<String, String> plugins) {
		Map<String, Pluggable> plugs = new HashMap<String, Pluggable>();
		for (Class<Pluggable> plug : pluggables) {
			try {
				if (plugins.containsKey(plug.getSimpleName())) {
					plugs.put(plugins.get(plug.getSimpleName()),
							plug.newInstance());
				}
			} catch (InstantiationException e) {
				System.err.println("Can't instantiate plugin: "
						+ plug.getName());
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.err.println("IllegalAccess for plugin: "
						+ plug.getName());
				e.printStackTrace();
			}
		}
		return plugs;
	}

}
