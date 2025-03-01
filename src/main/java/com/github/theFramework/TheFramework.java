package com.github.theFramework;

import com.github.theFramework.commands.CommandsManager;
import com.github.theFramework.managers.FileReader;
import com.github.theFramework.managers.Placeholders;
import com.github.theFramework.managers.PlayerManager;
import com.github.theFramework.managers.TextManager;
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

	public static boolean isEnabled(String pluginName) {
		return Bukkit.getPluginManager().getPlugin(pluginName) != null;
	}

	@Override
	public void onEnable() {
		FileReader.load();
		Placeholders.load();
		CommandsManager.load();

		registerEvent(CommandsManager.self());
		registerEvent(new PlayerManager());

		TextManager.console(TextManager.format(
			"&b\n\n" +
				"  &b████████&3╗&b██&3╗  &b██&3╗&b███████&3╗   &b███████&3╗&b██████&3╗  &b█████&3╗ &b███&3╗   &b███&3╗&b███████&3╗ &b██&3╗       &b██&3╗ &b█████&3╗ &b██████&3╗ &b██&3╗  &b██&3╗\n" +
				"  &3╚══&b██&3╔══╝&b██&3║  &b██&3║&b██&3╔════╝   &b██&3╔════╝&b██&3╔══&b██&3╗&b██&3╔══&b██&3╗&b████&3╗ &b████&3║&b██&3╔════╝ &b██&3║  &b██&3╗  &b██&3║&b██&3╔══&b██&3╗&b██&3╔══&b██&3╗&b██&3║ &b██&3╔╝\n" +
				"     &b██&3║   &b███████&3║&b█████&3╗     &b█████&3╗  &b██████&3╔╝&b███████&3║&b██&3╔&b████&3╔&b██&3║&b█████&3╗   &3╚&b██&3╗&b████&3╗&b██&3╔╝&b██&3║  &b██&3║&b██████&3╔╝&b█████&3═╝ \n" +
				"     &b██&3║   &b██&3╔══&b██&3║&b██&3╔══╝     &b██&3╔══╝  &b██&3╔══&b██&3╗&b██&3╔══&b██&3║&b██&3║╚&b██&3╔╝&b██&3║&b██&3╔══╝    &b████&3╔═&b████&3║ &b██&3║  &b██&3║&b██&3╔══&b██&3╗&b██&3╔═&b██&3╗ \n" +
				"     &b██&3║   &b██&3║  &b██&3║&b███████&3╗   &b██&3║     &b██&3║  &b██&3║&b██&3║  &b██&3║&b██&3║ ╚═╝ &b██&3║&b███████&3╗  &3╚&b██&3╔╝ ╚&b██&3╔╝ ╚&b█████&3╔╝&b██&3║  &b██&3║&b██&3║ ╚&b██&3╗\n" +
				"     &3╚═╝   ╚═╝  ╚═╝╚══════╝   ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝   ╚═╝   ╚═╝   ╚════╝ ╚═╝  ╚═╝╚═╝  ╚═╝\n" +
				"  &f&m                                                                                                             &r\n" +
				"  &3Developed by &fOrange                                &3v&f1.0.13                                   &3Running on &fPaper" +
				"\n"
		));
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
