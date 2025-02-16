package me.mcplugins.theframework.managers;

import me.mcplugins.theframework.TheFramework;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundManager {

	public static void play(Player player, String soundName) {
		play(player, soundName, 1, 1);
	}
	public static void play(Player player, String soundName, int volume, int pitch) {
		NamespacedKey key = TheFramework.getNamespacedKey(soundName);
		Sound sound = (key != null) ? Registry.SOUNDS.get(key) : null;

		if (sound == null) {
			TextManager.console(TextManager.format("&6[Sound Manager] Invalid sound: " + soundName));
			return;
		}

		player.playSound(player.getLocation(), sound, volume, pitch);
	}
}
