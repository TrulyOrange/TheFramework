package com.github.theFramework.commands;

import com.github.theFramework.managers.TextManager;
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
