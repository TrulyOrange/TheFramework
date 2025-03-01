package com.github.theFramework;

import com.github.theFramework.managers.FileReader;
import com.github.theFramework.managers.SoundManager;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	public static final YamlConfiguration SELF = FileReader.getFile("config");

	public static class Players {
		public static class Messages {
			public static final String JOIN = SELF.getString("player-messages.join");
			public static final String WELCOME = SELF.getString("player-messages.welcome");
			public static final String LEAVE = SELF.getString("player-messages.leave");
		}
	}

	public static class Commands {
		public static class Messages {
			public static final String UNKNOWN = SELF.getString("command-messages.unknown");
			public static final String DENIED = SELF.getString("command-messages.denied");
			public static final String USAGE = SELF.getString("command-messages.usage");
		}

		public static class Sounds {
			public static final Sound UNKNOWN = SoundManager.get(SELF.getString("command-sounds.unknown"));
			public static final Sound DENIED = SoundManager.get(SELF.getString("command-sounds.denied"));
			public static final Sound USAGE = SoundManager.get(SELF.getString("command-sounds.usage"));
		}
	}
}
