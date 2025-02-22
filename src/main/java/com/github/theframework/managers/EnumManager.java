package com.github.theframework.managers;

import java.util.stream.Stream;

public class EnumManager {

	public static <E extends Enum<E>> E get(Class<E> enumClass, String valueToCheck) {
		return get(enumClass, valueToCheck, null);
	}

	public static <E extends Enum<E>> E get(Class<E> enumClass, String valueToCheck, E defaultVal) {
		return Stream.of(enumClass.getEnumConstants())
			.filter(e -> e.name().equalsIgnoreCase(valueToCheck))
			.findFirst()
			.orElse(defaultVal);
	}
}
