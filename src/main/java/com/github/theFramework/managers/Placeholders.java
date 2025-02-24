package com.github.theFramework.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Placeholders {
	private static final Map<String, Function<Object, String>> PLACEHOLDERS = new HashMap<>();

	public static void load() {
		register("%date%", context -> LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		register("%time%", context -> LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		register("%time_of_day%", context -> getTimeOfDay(Bukkit.getWorlds().getFirst().getTime()));
		register("%weather%", context -> Bukkit.getWorlds().getFirst().hasStorm() ? "Raining" : "Clear");

		register("%server_version%", context -> Bukkit.getBukkitVersion());
		register("%world_seed%", context -> String.valueOf(Bukkit.getWorlds().getFirst().getSeed()));

		register("%players_online%", context -> String.valueOf(Bukkit.getOnlinePlayers().size()));
		register("%max_players%", context -> String.valueOf(Bukkit.getMaxPlayers()));

		register("%player%", context -> (context instanceof Player) ? TextManager.toString(((Player) context).displayName()) : "");
		register("%player_uuid%", context -> (context instanceof Player) ? ((Player) context).getUniqueId().toString() : "");
		register("%player_name%", context -> (context instanceof Player) ? ((Player) context).getName() : "");
		register("%player_displayname%", context -> (context instanceof Player) ? TextManager.toString(((Player) context).displayName()) : "");
		register("%player_ping%", context -> (context instanceof Player) ? String.valueOf(((Player) context).getPing()) : "");
		register("%player_health%", context -> (context instanceof Player) ? String.valueOf(((Player) context).getHealth()) : "");
		register("%player_food%", context -> (context instanceof Player) ? String.valueOf(((Player) context).getFoodLevel()) : "");
		register("%player_level%", context -> (context instanceof Player) ? String.valueOf(((Player) context).getLevel()) : "");
		register("%player_exp%", context -> (context instanceof Player) ? String.valueOf(((Player) context).getExp()) : "");
		register("%player_op%", context -> (context instanceof Player) ? (((Player) context).isOp() ? "Yes" : "No") : "");
		register("%player_gamemode%", context -> (context instanceof Player) ? ((Player) context).getGameMode().name() : "");
		register("%player_flying%", context -> (context instanceof Player) ? (((Player) context).isFlying() ? "Yes" : "No") : "");
		register("%player_world%", context -> (context instanceof Player) ? ((Player) context).getWorld().getName() : "");
		register("%player_biome%", context -> (context instanceof Player) ? ((Player) context).getLocation().getBlock().getBiome().toString() : "");
		register("%player_location%", context -> (context instanceof Player) ? formatLocation(((Player) context).getLocation()) : "");
		register("%player_chunk%", context -> (context instanceof Player) ? ((Player) context).getLocation().getChunk().toString() : "");
		register("%player_x%", context -> (context instanceof Player) ? String.valueOf(((Player) context).getLocation().getBlockX()) : "");
		register("%player_y%", context -> (context instanceof Player) ? String.valueOf(((Player) context).getLocation().getBlockY()) : "");
		register("%player_z%", context -> (context instanceof Player) ? String.valueOf(((Player) context).getLocation().getBlockZ()) : "");
		register("%player_facing%", context -> (context instanceof Player) ? ((Player) context).getFacing().name() : "");

		register("%command%", context -> (context instanceof Command) ? ((Command) context).getLabel() : "");
		register("%command_usage%", context -> (context instanceof Command) ? ((Command) context).getUsage() : "");
		register("%command_aliases%", context -> (context instanceof Command) ? String.join(", ", ((Command) context).getAliases()) : "");
		register("%command_description%", context -> (context instanceof Command) ? ((Command) context).getDescription() : "");

	}

	public static void register(String key, Function<Object, String> replacer) {
		PLACEHOLDERS.put(key, replacer);
	}

	public static String apply(String text, Object context) {
		if (text == null) return "";

		for (Map.Entry<String, Function<Object, String>> entry : PLACEHOLDERS.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue().apply(context);

			if (value != null)
				text = text.replace(key, value);
		}

		return text;
	}

	public static String apply(String text, Object context, Map<String, String> customPlaceholders) {
		if (text == null) return "";

		text = apply(text, context);

		for (Map.Entry<String, String> entry : customPlaceholders.entrySet()) {
			if (entry.getValue() != null)
				text = text.replace(entry.getKey(), entry.getValue());
		}

		return text;
	}

	private static String formatLocation(Location location) {
		return String.format("X: %d, Y: %d, Z: %d", location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}

	private static String getTimeOfDay(long time) {
		if (time < 6000) return "Morning";
		else if (time < 12000) return "Noon";
		else if (time < 18000) return "Evening";
		else return "Night";
	}
}
