package me.mcplugins.theframework.commands;

import me.mcplugins.theframework.managers.TextManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class RelCommand extends CustomCommand {
	public RelCommand() {
		super("rel");
	}

	@Override
	public void run(CommandSender sender, String[] args) {
		sender.sendMessage(TextManager.format("&bReloading..."));
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reload confirm");
	}
}
