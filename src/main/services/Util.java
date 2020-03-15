package main.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

	public static final DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static final DateTimeFormatter TIMEFORMATTER = DateTimeFormatter.ofPattern("HH:mm");
	public static final DateTimeFormatter DATETIMEFORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy  -  HH:mm");

	public static boolean isSameDay(LocalDateTime one, LocalDateTime two) {
		return (one.getDayOfYear() == two.getDayOfYear() && one.getYear() == two.getYear());
	}

	public static <T> T randomChoice(List<T> list) {
		return list.get(ThreadLocalRandom.current().nextInt(0, list.size()));
	}

	public static <T> T randomChoice(T[] arr) {
		return arr[ThreadLocalRandom.current().nextInt(0, arr.length)];
	}

}
