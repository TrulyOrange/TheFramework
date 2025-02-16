package me.mcplugins.theframework.managers;

import me.mcplugins.theframework.TheFramework;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileReader {
	private static final Map<String, YamlConfiguration> FILES = new HashMap<>();
	private static final TheFramework PLUGIN = TheFramework.getInstance();

	public static void load() {
		File pluginFolder = PLUGIN.getDataFolder();
		if (!pluginFolder.exists() && !pluginFolder.mkdirs()) {
			TextManager.console(TextManager.format("&c[File Reader] Failed to read plugin folder!"));
			return;
		}

		FILES.clear();

		ensureDefault(pluginFolder, "config.yml");

		loadFilesRecursively(pluginFolder, "");
	}

	private static void loadFilesRecursively(File folder, String pathPrefix) {
		File[] files = folder.listFiles();
		if (files == null) return;

		for (File file : files) {
			if (file.isDirectory()) {
				loadFilesRecursively(file, pathPrefix + file.getName() + "/");
			} else if (file.getName().endsWith(".yml")) {
				String key = pathPrefix + file.getName().replace(".yml", "");
				FILES.put(key, YamlConfiguration.loadConfiguration(file));
			}
		}
	}

	private static void ensureDefault(File pluginFolder, String filePath) {
		File file = new File(PLUGIN.getDataFolder(), filePath);
		File parentFolder = file.getParentFile();

		if (!parentFolder.exists() && !parentFolder.mkdirs()) {
			TextManager.console(TextManager.format("&c[File Reader] Failed to create folder: " + parentFolder.getPath()));
			return;
		}

		if (!file.exists())
			PLUGIN.saveResource(filePath, false);
	}

	public static void saveFile(String fileName, FileConfiguration update) {
		try {
			File file = new File(TheFramework.getInstance().getDataFolder(), fileName + ".yml");
			YamlConfiguration updatedConfig = YamlConfiguration.loadConfiguration(file);

			for (String key : update.getKeys(true))
				updatedConfig.set(key, update.get(key));

			updatedConfig.save(file);
		} catch (IOException e) {
			TextManager.console(TextManager.format("&c[File Reader] Failed to save file: " + fileName));
		}
	}

	public static Set<String> getFileKeys() {
		return FILES.keySet();
	}
	public static YamlConfiguration getFile(String fileName) {
		return FILES.get(fileName.toLowerCase());
	}

	public static ConfigurationSection getSection(String fileName, String path) {
		YamlConfiguration file = getFile(fileName);
		return file.getConfigurationSection(path);
	}
	public static List<String> getStringList(String fileName, String path) {
		YamlConfiguration file = getFile(fileName);
		return file.getStringList(path);
	}
	public static String getString(String fileName, String path, String defaultValue) {
		YamlConfiguration file = getFile(fileName);
		return (file != null) ? file.getString(path, defaultValue) : defaultValue;
	}
	public static int getInt(String fileName, String path, int defaultValue) {
		YamlConfiguration file = getFile(fileName);
		return (file != null) ? file.getInt(path, defaultValue) : defaultValue;
	}
	public static boolean getBoolean(String fileName, String path, boolean defaultValue) {
		YamlConfiguration file = getFile(fileName);
		return (file != null) ? file.getBoolean(path, defaultValue) : defaultValue;
	}
}
