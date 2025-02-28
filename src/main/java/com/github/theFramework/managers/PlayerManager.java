package com.github.theFramework.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerManager implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if (player.hasPlayedBefore())
			event.joinMessage(TextManager.format(Config.Messages.JOIN, player));
		else
			event.joinMessage(TextManager.format(Config.Messages.WELCOME, player));
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.quitMessage(TextManager.format(Config.Messages.LEAVE, player));
	}

	private static class Config {
		static final YamlConfiguration CONFIG = FileReader.getFile("config");

		static class Messages {
			static final String JOIN = CONFIG.getString("player-messages.join");
			static final String WELCOME = CONFIG.getString("player-messages.welcome");
			static final String LEAVE = CONFIG.getString("player-messages.leave");
		}
	}
}
