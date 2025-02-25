package com.github.theFramework.managers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeManager {
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

	/**
	 * Converts a time string (e.g., "14:30") to Minecraft ticks.
	 * Minecraft time runs from 0 to 24000, where:
	 * - 00:00 -> 18000 ticks (midnight)
	 * - 06:00 -> 0 ticks (sunrise)
	 * - 12:00 -> 6000 ticks (noon)
	 * - 18:00 -> 12000 ticks (sunset)
	 * - 24:00 -> 18000 ticks (midnight again)
	 *
	 * @param timeString The time in HH:mm format.
	 * @return The corresponding Minecraft tick value.
	 */
	public static int timeToTicks(String timeString) {
		try {
			LocalTime time = LocalTime.parse(timeString, FORMATTER);
			int hours = time.getHour();
			int minutes = time.getMinute();

			int totalMinutes = (hours * 60) + minutes;
			return ((totalMinutes * 1000) / 60 + 18000) % 24000;
		} catch (Exception e) {
			TextManager.console(TextManager.format("[Time Manager] Invalid time format: " + timeString));
			return -1;
		}
	}

	/**
	 * Converts Minecraft ticks to a readable HH:mm time string.
	 *
	 * @param ticks The Minecraft time in ticks (0 - 24000).
	 * @return The time in "HH:mm" format.
	 */
	public static String ticksToTime(int ticks) {
		ticks = (ticks + 6000) % 24000;
		int totalMinutes = (ticks * 60) / 1000;

		int hours = totalMinutes / 60;
		int minutes = totalMinutes % 60;

		return String.format("%02d:%02d", hours, minutes);
	}

	/**
	 * Checks if the current in-game time is within a specific range.
	 *
	 * @param worldTime The current world time in ticks.
	 * @param startTime The start time in HH:mm format.
	 * @param endTime   The end time in HH:mm format.
	 * @return True if the current time is within the range.
	 */
	public static boolean isTimeWithinRange(int worldTime, String startTime, String endTime) {
		int startTicks = timeToTicks(startTime);
		int endTicks = timeToTicks(endTime);

		if (startTicks == -1 || endTicks == -1) return false;

		if (startTicks < endTicks) {
			return worldTime >= startTicks && worldTime <= endTicks;
		} else {
			return worldTime >= startTicks || worldTime <= endTicks;
		}
	}

	/**
	 * Compares two times and determines which is earlier.
	 *
	 * @param time1 The first time in HH:mm format.
	 * @param time2 The second time in HH:mm format.
	 * @return -1 if time1 is earlier, 1 if time2 is earlier, 0 if equal.
	 */
	public static int compareTimes(String time1, String time2) {
		int ticks1 = timeToTicks(time1);
		int ticks2 = timeToTicks(time2);

		return Integer.compare(ticks1, ticks2);
	}
}
