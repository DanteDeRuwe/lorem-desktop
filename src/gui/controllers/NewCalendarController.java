package gui.controllers;

import java.time.LocalDate;

import com.jfoenix.controls.JFXDatePicker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.domain.SessionCalendar;
import main.domain.facades.SessionCalendarFacade;
import main.exceptions.InvalidSessionCalendarException;
import main.exceptions.UserNotAuthorizedException;
import main.services.Alerts;
import main.services.DataValidation;
import main.services.GuiUtil;

public class NewCalendarController extends GuiController {

	@FXML
	private Label topLabel, validationLabel;
	@FXML
	private JFXDatePicker startDatePicker, endDatePicker;
	@FXML
	private Button saveButton, cancelButton;

	SessionCalendarFacade scf;
	CalendarSceneController csc;

	@FXML
	public void initialize() {
		scf = ((SessionCalendarFacade) getFacade());
		csc = getMainController().getCalendarSceneController();

		// set the right header
		topLabel.setText("Nieuwe Kalender");

		// Date picker format
		GuiUtil.fixDatePicker(startDatePicker);
		GuiUtil.fixDatePicker(endDatePicker);

		// Event listeners
		saveButton.setOnMouseClicked((event) -> handleCreate());
		cancelButton.setOnMouseClicked((event) -> goBack());
	}

	private void handleCreate() {
		validationLabel.setText("");
		if (!allFieldsOk())
			return;

		LocalDate start = startDatePicker.getValue();
		LocalDate end = endDatePicker.getValue();

		try {
			SessionCalendar sc = scf.createSessionCalendar(start, end);
			scf.addSessionCalendar(sc);
			csc.update();
			goBack();
		} catch (InvalidSessionCalendarException isce) {
			validationLabel.setText(validationLabel.getText() + "\n" + isce.getMessage());
		} catch (UserNotAuthorizedException e) {
			Alerts.errorAlert("Kalender aanmaken", "Je hebt niet de juiste machtigingen om een kalender aan te maken.")
					.show();
		}
	}

	private boolean allFieldsOk() {
		boolean startFilledIn = DataValidation.dateFilledIn(startDatePicker, validationLabel,
				"Startdatum is verplicht");
		boolean endFilledIn = DataValidation.dateFilledIn(startDatePicker, validationLabel, "Einddatum is verplicht");
		return startFilledIn && endFilledIn;
	}

	private void goBack() {
		csc.displayCalendarList();
	}

}
