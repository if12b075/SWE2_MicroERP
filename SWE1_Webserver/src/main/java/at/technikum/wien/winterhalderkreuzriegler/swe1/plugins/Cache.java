package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.WebserverConstants;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.interfaces.Pluggable;

public class Cache {

	public static final Map<String, Pluggable> plugins;

	public static final Properties properties;

	static {
		plugins = new HashMap<String, Pluggable>();
		properties = new Properties();
	}

	private static void refreshPluginCache() {
		// Plugins beenden
		for (Entry<String, Pluggable> entry : Cache.plugins.entrySet()) {
			entry.getValue().stop();
		}
		String pluginDirPath = properties.getProperty(
				WebserverConstants.PLUGIN_DIR_KEY,
				WebserverConstants.DEFAULT_PLUGIN_DIR_PATH);
		Map<String, String> plugins = new HashMap<String, String>();
		for (Entry<Object, Object> prop : properties.entrySet()) {
			if (!WebserverConstants.PLUGIN_DIR_KEY.equals(prop.getKey()
					.toString())) {
				plugins.put(prop.getKey().toString(), prop.getValue()
						.toString());
			}
		}
		Map<String, Pluggable> loadedPlugins;
		try {
			loadedPlugins = PluginLoader.loadPlugins(new File(pluginDirPath),
					plugins);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Plugins konnten nicht geladen werden!!!", e);
		}
		Cache.plugins.clear();
		Cache.plugins.putAll(loadedPlugins);
		// Plugins starten
		for (Entry<String, Pluggable> entry : Cache.plugins.entrySet()) {
			entry.getValue().start();
		}
	}

	private static void refreshProperties() {
		try {
			properties.clear();
			properties.load(new FileReader(new File(
					WebserverConstants.RELATIVE_CONFIG_FILE_PATH)));
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Config-File konnte nicht geladen werden!!!", e);
		}
	}

	public static void refreshCache() {
		refreshProperties();
		refreshPluginCache();
	}

}
