package com.github.theFramework.managers;

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
			event.joinMessage(TextManager.format(
				FileReader.getString("config", "player-messages.join", ""),
				player));
		else
			event.joinMessage(TextManager.format(
				FileReader.getString("config", "player-messages.welcome", ""),
				player));
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.quitMessage(TextManager.format(FileReader.getString("config", "player-messages.leave", ""), player));
	}
}
