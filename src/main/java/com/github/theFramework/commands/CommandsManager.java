package com.github.theFramework.commands;

import com.github.theFramework.Config;
import com.github.theFramework.TheFramework;
import com.github.theFramework.managers.SoundManager;
import com.github.theFramework.managers.TextManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CommandsManager implements CommandExecutor, TabCompleter, Listener {
	private static final CommandsManager INSTANCE = new CommandsManager();
	@Getter
	private static final Map<String, CustomCommand> COMMANDS = new HashMap<>();

	public static CommandsManager self() {
		return INSTANCE;
	}
	private CommandsManager() {
	}

	public static void load() {
		COMMANDS.clear();
		register(new RelCommand());
	}

	public static void register(CustomCommand command) {
		register(command, TheFramework.asPlugin());
	}
	public static void register(CustomCommand command, JavaPlugin plugin) {
		String name = command.getName().toLowerCase();
		COMMANDS.put(command.getName(), command);

		plugin.getCommand(name).setExecutor(INSTANCE);
		plugin.getCommand(name).setTabCompleter(INSTANCE);
	}

	public static long getRequiredArgs(String usage) {
		return Arrays.stream(usage.split(" "))
			.filter(arg -> arg.startsWith("<") && arg.endsWith(">"))
			.count();
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
		CustomCommand customCommand = COMMANDS.get(label.toLowerCase());
		if (customCommand != null)
			customCommand.run(sender, args);
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
		if (!(sender instanceof Player)) return Collections.emptyList();
		CustomCommand customCommand = COMMANDS.get(label.toLowerCase());

		return Collections.emptyList();
	}

	@EventHandler
	public void onPreCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String[] args = event.getMessage().substring(1).split(" ");
		String inputCommand = args[0].toLowerCase();
		args = Arrays.copyOfRange(args, 1, args.length);

		Command command = Bukkit.getCommandMap().getCommand(inputCommand);
		CustomCommand asCustom = CommandsManager.COMMANDS.get(inputCommand);

		if (command == null) {
			event.setCancelled(true);
			TextManager.sendActionBar(player, TextManager.format(Config.Commands.Messages.UNKNOWN, inputCommand));
			SoundManager.play(player, Config.Commands.Sounds.UNKNOWN);
			return;
		}

		String permission = command.getPermission();
		if (permission != null && !player.hasPermission(permission)) {
			event.setCancelled(true);
			TextManager.sendActionBar(player, TextManager.format(Config.Commands.Messages.DENIED, command));
			SoundManager.play(player, Config.Commands.Sounds.DENIED);
			return;
		}

		if (asCustom == null) return;

		if (args.length < getRequiredArgs(command.getUsage())) {
			event.setCancelled(true);
			TextManager.sendActionBar(player, TextManager.format(Config.Commands.Messages.USAGE, command));
			SoundManager.play(player, Config.Commands.Sounds.USAGE);
		}
	}
}

