package main.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

	public static final DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static final DateTimeFormatter TIMEFORMATTER = DateTimeFormatter.ofPattern("HH:mm");

	public static boolean isSameDay(LocalDateTime one, LocalDateTime two) {
		return (one.getDayOfYear() == two.getDayOfYear() && one.getYear() == two.getYear());
	}

	public static LocalDateTime[] randomStartStop() {
		LocalDateTime start = randomLocalDateTimeThisYearFromTomorrow();
		LocalDateTime stop = start.plusHours(ThreadLocalRandom.current().nextInt(1, 8));
		return new LocalDateTime[] { start, stop };
	}

	public static <T> T randomChoice(List<T> list) {
		return list.get(ThreadLocalRandom.current().nextInt(0, list.size()));
	}

	public static <T> T randomChoice(T[] arr) {
		return arr[ThreadLocalRandom.current().nextInt(0, arr.length)];
	}

	// helpers

	private static LocalDateTime randomLocalDateTimeThisYearFromTomorrow() {
		long minDay = LocalDate.now().plusDays(1).toEpochDay();
		long maxDay = LocalDate.of(2020, 12, 31).toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

		int randomHour = ThreadLocalRandom.current().nextInt(7, 20);
		int randomMin = ThreadLocalRandom.current().nextInt(0, 59);

		LocalTime randomTime = LocalTime.of(randomHour, randomMin);

		return LocalDateTime.of(randomDate, randomTime);
	}
}
