package me.mcplugins.theframework;

import me.mcplugins.theframework.commands.CommandsManager;
import me.mcplugins.theframework.managers.FileReader;
import me.mcplugins.theframework.managers.PlayerManager;
import me.mcplugins.theframework.managers.TextManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TheFramework extends JavaPlugin {
	public static TheFramework getInstance() {
		return getPlugin(TheFramework.class);
	}

	@Override
	public void onEnable() {
		TextManager.console(TextManager.format("&aThe Framework is online!"));

		FileReader.load();
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
		pluginManager.registerEvents(listener, getInstance());
	}

	public static NamespacedKey getNamespacedKey(String search) {
		return NamespacedKey.fromString("minecraft:" + search.toLowerCase());
	}
}
