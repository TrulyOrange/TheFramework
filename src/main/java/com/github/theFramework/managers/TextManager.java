package com.github.theFramework.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TextManager {
	public static final LegacyComponentSerializer FORMATTER = LegacyComponentSerializer.legacyAmpersand();
	public static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();

	public static String toString(Component component) {
		return PLAIN.serialize(component);
	}
	public static String toTitleCase(String input) {
		if (input == null || input.isEmpty()) return "";
		return Arrays.stream(input.toLowerCase().split("_"))
			.map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
			.collect(Collectors.joining(" "));
	}

	public static Component format(Component text) {
		return format(toString(text));
	}
	public static Component format(String text) {
		if (text.contains("&"))
			return deformat(Component.text("")).append(FORMATTER.deserialize(text));
		return Component.text(text);
	}
	public static Component format(String text, Object context) {
		if (text == null) return Component.text("");
		text = Placeholders.apply(text, context);
		return FORMATTER.deserialize(text);
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
