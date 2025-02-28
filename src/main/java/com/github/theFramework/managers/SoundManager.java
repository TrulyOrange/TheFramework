package com.github.theFramework.managers;

import com.github.theFramework.TheFramework;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundManager {

	public static void play(Player player, String soundName) {
		play(player, soundName, 1, 1);
	}
	public static void play(Player player, String soundName, int volume, int pitch) {
		Sound sound = get(soundName);
		play(player, sound, volume, pitch);
	}
	public static void play(Player player, Sound sound) {
		play(player, sound, 1, 1);
	}
	public static void play(Player player, Sound sound, int volume, int pitch) {
		if (sound != null)
			player.playSound(player.getLocation(), sound, volume, pitch);
	}

	public static Sound get(String soundName) {
		NamespacedKey key = TheFramework.getNamespacedKey(soundName);
		return (key != null) ? Registry.SOUNDS.get(key) : null;
	}
}
