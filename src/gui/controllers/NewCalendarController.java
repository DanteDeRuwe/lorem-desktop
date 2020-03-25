package gui.controllers;

import java.time.LocalDate;

import com.jfoenix.controls.JFXDatePicker;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.domain.SessionCalendar;
import main.domain.facades.SessionCalendarFacade;
import main.exceptions.UserNotAuthorizedException;
import main.services.Alerts;
import main.services.GuiUtil;

public class NewCalendarController extends GuiController {

	@FXML
	private Label topLabel;
	@FXML
	private JFXDatePicker startDatePicker, endDatePicker;
	@FXML
	private Button saveButton, cancelButton;

	@FXML
	public void initialize() {
		topLabel.setText("Nieuwe Kalender");

		// Date picker format
		GuiUtil.fixDatePicker(startDatePicker);
		GuiUtil.fixDatePicker(endDatePicker);

		// Event listeners
		saveButton.setOnMouseClicked((event) -> handleCreate());
		cancelButton.setOnMouseClicked((event) -> goBack());
	}

	private void handleCreate() {
		LocalDate start = startDatePicker.getValue();
		LocalDate end = endDatePicker.getValue();

		SessionCalendarFacade scf = ((SessionCalendarFacade) getFacade());
		try {
			SessionCalendar sc = scf.createSessionCalendar(start, end);
			scf.addSessionCalendar(sc);
			getMainController().getCalendarSceneController().update();
			goBack();
		} catch (IllegalArgumentException e) {
			Alert alert = Alerts.errorAlert("Nieuwe kalender", "");
			if (e.getMessage().equals("Academic years must start and end in consecutive years")) {
				alert.setContentText("Een academisch jaar moet starten en eindigen in opeenvolgende jaren.");
			} else if (e.getMessage().equals("Cannot create calendar that far in the past")) {
				alert.setContentText("Je mag geen kalender zo ver in het verleden aanmaken.");
			}
			alert.showAndWait();
		} catch (UserNotAuthorizedException e) {
			Alerts.errorAlert("Kalender aanmaken", "Je hebt niet de juiste machtigingen om een kalender aan te maken.")
					.show();
		}
	}

	private void goBack() {
		getMainController().getCalendarSceneController().displayCalendarList();
	}

}
