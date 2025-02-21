package me.mcplugins.theframework;

import me.mcplugins.theframework.commands.CommandsManager;
import me.mcplugins.theframework.managers.FileReader;
import me.mcplugins.theframework.managers.Placeholders;
import me.mcplugins.theframework.managers.PlayerManager;
import me.mcplugins.theframework.managers.TextManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class TheFramework extends JavaPlugin {
	public static final Random RNG = new Random();

	public static TheFramework asPlugin() {
		return getPlugin(TheFramework.class);
	}

	@Override
	public void onEnable() {
		TextManager.console(TextManager.format(
			"&b\n\n" +
				"  &b████████&3╗&b██&3╗  &b██&3╗&b███████&3╗   &b███████&3╗&b██████&3╗  &b█████&3╗ &b███&3╗   &b███&3╗&b███████&3╗ &b██&3╗       &b██&3╗ &b█████&3╗ &b██████&3╗ &b██&3╗  &b██&3╗\n" +
				"  &3╚══&b██&3╔══╝&b██&3║  &b██&3║&b██&3╔════╝   &b██&3╔════╝&b██&3╔══&b██&3╗&b██&3╔══&b██&3╗&b████&3╗ &b████&3║&b██&3╔════╝ &b██&3║  &b██&3╗  &b██&3║&b██&3╔══&b██&3╗&b██&3╔══&b██&3╗&b██&3║ &b██&3╔╝\n" +
				"     &b██&3║   &b███████&3║&b█████&3╗     &b█████&3╗  &b██████&3╔╝&b███████&3║&b██&3╔&b████&3╔&b██&3║&b█████&3╗   &3╚&b██&3╗&b████&3╗&b██&3╔╝&b██&3║  &b██&3║&b██████&3╔╝&b█████&3═╝ \n" +
				"     &b██&3║   &b██&3╔══&b██&3║&b██&3╔══╝     &b██&3╔══╝  &b██&3╔══&b██&3╗&b██&3╔══&b██&3║&b██&3║╚&b██&3╔╝&b██&3║&b██&3╔══╝    &b████&3╔═&b████&3║ &b██&3║  &b██&3║&b██&3╔══&b██&3╗&b██&3╔═&b██&3╗ \n" +
				"     &b██&3║   &b██&3║  &b██&3║&b███████&3╗   &b██&3║     &b██&3║  &b██&3║&b██&3║  &b██&3║&b██&3║ ╚═╝ &b██&3║&b███████&3╗  &3╚&b██&3╔╝ ╚&b██&3╔╝ ╚&b█████&3╔╝&b██&3║  &b██&3║&b██&3║ ╚&b██&3╗\n" +
				"     &3╚═╝   ╚═╝  ╚═╝╚══════╝   ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝   ╚═╝   ╚═╝   ╚════╝ ╚═╝  ╚═╝╚═╝  ╚═╝\n" +
				"  &f&m                                                                                                             &r\n" +
				"  &3Developed by &fOrange                                &3v&f1.0.1                                    &3Running on &fPaper" +
				"\n"
		));

		FileReader.load();
		Placeholders.load();
		CommandsManager.load();

		registerEvent(CommandsManager.getInstance());
		registerEvent(new PlayerManager());
	}

	@Override
	public void onDisable() {
		TextManager.console(TextManager.format("&cThe Framework is offline."));
	}

	public static void registerEvent(Listener listener) {
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(listener, asPlugin());
	}

	public static NamespacedKey getNamespacedKey(String search) {
		return NamespacedKey.fromString("minecraft:" + search.toLowerCase());
	}

	public static boolean chance(double percentage) {
		return chance(percentage, 0.0);
	}
	public static boolean chance(double percentage, double luck) {
		if (percentage <= 0) return false;
		if (percentage >= 100) return true;

		double adjustedPercentage = Math.min(100, Math.max(0, percentage + luck));
		return RNG.nextDouble() * 100 < adjustedPercentage;
	}
}
