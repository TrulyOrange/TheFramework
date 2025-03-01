package com.github.theFramework.managers;

import com.github.theFramework.Config;
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
			event.joinMessage(TextManager.format(Config.Players.Messages.JOIN, player));
		else
			event.joinMessage(TextManager.format(Config.Players.Messages.WELCOME, player));
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.quitMessage(TextManager.format(Config.Players.Messages.LEAVE, player));
	}
}
