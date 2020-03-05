package main.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import gui.controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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

	public static void bindAnchorPane(String fxmlFile, Pane parent) {
		try {
			AnchorPane child = FXMLLoader
					.<AnchorPane>load(MainController.class.getResource("/resources/fxml/" + fxmlFile));

			parent.getChildren().setAll(child);
			AnchorPane.setBottomAnchor(child, 0.0d);
			AnchorPane.setTopAnchor(child, 0.0d);
			AnchorPane.setLeftAnchor(child, 0.0d);
			AnchorPane.setRightAnchor(child, 0.0d);

		} catch (IOException e) {
			e.printStackTrace();
		}
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
