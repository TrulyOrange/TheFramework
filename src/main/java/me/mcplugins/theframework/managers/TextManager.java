package me.mcplugins.theframework.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class TextManager {
	public static final LegacyComponentSerializer FORMATTER = LegacyComponentSerializer.legacyAmpersand();
	public static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();

	public static Component format(String text, Map<String, String> placeholders) {
		if (text == null) return Component.text("");

		for (Map.Entry<String, String> entry : placeholders.entrySet())
			text = text.replace(entry.getKey(), entry.getValue());

		return deformat(Component.text("")).append(FORMATTER.deserialize(text));
	}
	public static Component format(String text, Player player) {
		Map<String, String> placeholders = new HashMap<>();

		placeholders.put("%player%", player.getName());
		placeholders.put("%world%", player.getWorld().getName());
		placeholders.put("%health%", String.valueOf(player.getHealth()));

		return format(text, placeholders);
	}
	public static Component format(String text, Command command) {
		Map<String, String> placeholders = new HashMap<>();

		placeholders.put("%command%", command.getLabel());
		placeholders.put("%usage%", command.getUsage());

		return format(text, placeholders);
	}
	public static Component format(String text, String misc) {
		Map<String, String> placeholders = new HashMap<>();

		placeholders.put("%input%", misc);
		placeholders.put("%time%", misc);
		placeholders.put("%cooldown%", misc);

		return format(text, placeholders);
	}

	public static Component format(String text) {
		return deformat(Component.text("")).append(FORMATTER.deserialize(text));
	}
	public static Component format(Component text) {
		String rawText = PLAIN.serialize(text);
		if (rawText.contains("&"))
			return deformat(Component.text("")).append(FORMATTER.deserialize(rawText));

		return text;
	}

	public static Component deformat(Component text) {
		return text.color(NamedTextColor.WHITE)
			.decoration(TextDecoration.ITALIC, false)
			.decoration(TextDecoration.BOLD, false)
			.decoration(TextDecoration.UNDERLINED, false)
			.decoration(TextDecoration.STRIKETHROUGH, false)
			.decoration(TextDecoration.OBFUSCATED, false);
	}

	public static void console(Component message) {
		Bukkit.getConsoleSender().sendMessage(message);
	}

	public static void sendTitle(Player player, Component title, Component subtitle) {
		player.showTitle(Title.title(title, subtitle));
	}
	public static void sendTitle(Player player, Component title, Component subtitle,
	                             double fadeIn, double stay, double fadeOut) {
		player.showTitle(Title.title(
			title, subtitle,
			Title.Times.times(
				Duration.ofMillis((long) (fadeIn * 1000)),
				Duration.ofMillis((long) (stay * 1000)),
				Duration.ofMillis((long) (fadeOut * 1000))
			)));
	}
	public static void sendActionBar(Player player, Component message) {
		player.sendActionBar(message);
	}
	public static void sendMessage(Player player, Component message) {
		player.sendMessage(message);
	}
}
