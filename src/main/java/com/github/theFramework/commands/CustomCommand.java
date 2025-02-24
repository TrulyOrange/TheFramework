package com.github.theFramework.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class CustomCommand extends Command {
	public CustomCommand(String name) {
		super(name);
	}

	public abstract void run(CommandSender sender, String[] args);

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		run(sender, args);
		return true;
	}
}