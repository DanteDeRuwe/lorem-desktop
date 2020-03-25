package gui.controllers;

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

public class EditCalendarController extends GuiController {

	@FXML
	private Label topLabel;
	@FXML
	private JFXDatePicker startDatePicker, endDatePicker;
	@FXML
	private Button saveButton, cancelButton;
	@FXML
	Label validationLabel;

	private SessionCalendarFacade scf = ((SessionCalendarFacade) getFacade());

	@FXML
	public void initialize() {
		topLabel.setText("Wijzig Kalender");

		// Date picker formats
		GuiUtil.fixDatePicker(startDatePicker);
		GuiUtil.fixDatePicker(endDatePicker);

		fillInFields();
		saveButton.setOnMouseClicked((e) -> handleSave());
		cancelButton.setOnMouseClicked((e) -> goBack());
	}

	private void fillInFields() {
		SessionCalendar calendar = getMainController().getCalendarSceneController().getInspectedCalendar();
		startDatePicker.setValue(calendar.getStartDate());
		endDatePicker.setValue(calendar.getEndDate());
	}

	private void handleSave() {
		validationLabel.setText("");
		if (!allFieldsOk())
			return;

		try {
			scf.editSessionCalendar(getMainController().getCalendarSceneController().getInspectedCalendar(),
					startDatePicker.getValue(), endDatePicker.getValue());
			fillInFields();
			getMainController().getCalendarSceneController().update();
			goBack();
		} catch (InvalidSessionCalendarException isce) {
			validationLabel.setText(validationLabel.getText() + "\n" + isce.getMessage());
		} catch (UserNotAuthorizedException e) {
			Alerts.errorAlert("Kalender wijzigen", "Je hebt niet de juiste machtigingen om deze kalender te wijzigen.")
					.show();
		} catch (Exception e) {
			Alerts.errorAlert("Kalender wijzigen", "Unexpected error: " + e.getMessage()).showAndWait();
		}
	}

	private boolean allFieldsOk() {
		boolean startFilledIn = DataValidation.dateFilledIn(startDatePicker, validationLabel,
				"Startdatum is verplicht");
		boolean endFilledIn = DataValidation.dateFilledIn(endDatePicker, validationLabel, "Einddatum is verplicht");
		return startFilledIn && endFilledIn;
	}

	private void goBack() {
		getMainController().getCalendarSceneController().displayCalendarList();
	}

}
