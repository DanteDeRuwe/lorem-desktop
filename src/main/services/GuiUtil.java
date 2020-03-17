package main.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.FormatStyle;
import java.util.Locale;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalTimeStringConverter;

public class GuiUtil {

	public static void setAnchorsZero(Pane pane) {
		AnchorPane.setTopAnchor(pane, 0.0);
		AnchorPane.setRightAnchor(pane, 0.0);
		AnchorPane.setBottomAnchor(pane, 0.0);
		AnchorPane.setLeftAnchor(pane, 0.0);
	}

	public static void bindAnchorPane(AnchorPane child, AnchorPane parent) {
		setAnchorsZero(child);
		parent.getChildren().clear();
		parent.getChildren().add(child);
	}

	public static void fixDatePicker(JFXDatePicker picker) {
		picker.setConverter(new LocalDateStringConverter(FormatStyle.SHORT, Locale.FRANCE, null));
	}

	public static void fixTimePicker(JFXTimePicker picker) {
		picker.set24HourView(true);
		picker.setConverter(new LocalTimeStringConverter(FormatStyle.SHORT, Locale.FRANCE));
	}

	public static <T> void fillColumn(TableColumn<T, String> col, String propname, int minWidth, int maxWidth) {
		col.setCellValueFactory(new PropertyValueFactory<>(propname));
		col.setMinWidth(minWidth);
		col.setMaxWidth(maxWidth);
		col.setResizable(true);
	}

	public static <T> void fillColumnWithDateTime(TableColumn<T, LocalDateTime> col, String propname, int minWidth,
			int maxWidth) {
		col.setCellValueFactory(new PropertyValueFactory<>(propname));
		col.setMinWidth(minWidth);
		col.setMaxWidth(maxWidth);
		col.setResizable(true);
		col.setCellFactory(tc -> new TableCell<T, LocalDateTime>() {
			@Override
			protected void updateItem(LocalDateTime dateTime, boolean empty) {
				super.updateItem(dateTime, empty);
				if (empty) {
					setText(null);
				} else {
					setText(Util.DATETIMEFORMATTER.format(dateTime));
				}
			}
		});
	}

	public static <T> void fillColumnWithDuration(TableColumn<T, Duration> col, String propname, int minWidth,
			int maxWidth) {
		col.setCellValueFactory(new PropertyValueFactory<>(propname));
		col.setMinWidth(minWidth);
		col.setMaxWidth(maxWidth);
		col.setResizable(true);
		col.setCellFactory(tc -> new TableCell<T, Duration>() {
			@Override
			protected void updateItem(Duration duration, boolean empty) {
				super.updateItem(duration, empty);
				if (empty) {
					setText(null);
				} else {

					int durationHours = duration.toHoursPart();
					int durationMinutes = duration.toMinutesPart();
					setText(durationHours > 0 ? String.format("%du %dm", durationHours, durationMinutes)
							: String.format("%dm", durationMinutes));

				}
			}
		});
	}

	public static <T> void fillColumnWithObjectToString(TableColumn<T, Object> col, String propname, int minWidth,
			int maxWidth) {
		col.setCellValueFactory(new PropertyValueFactory<>(propname));
		col.setMinWidth(minWidth);
		col.setMaxWidth(maxWidth);
		col.setResizable(true);

		col.setCellFactory(tc -> new TableCell<T, Object>() {
			@Override
			protected void updateItem(Object object, boolean empty) {
				super.updateItem(object, empty);
				if (empty) {
					setText(null);
				} else {
					setText(object.toString());
				}
			}
		});
	}

}
