package gui.controllers;

import java.time.LocalDate;
import java.time.LocalTime;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.domain.Member;
import main.domain.Session;
import main.domain.facades.MemberFacade;
import main.domain.facades.SessionCalendarFacade;
import main.services.DataValidation;

public class NewSessionController extends GuiController {

	// Nodes
	@FXML private JFXTextField titleField, speakerField, durationField, locationField, capacityField;
	@FXML private JFXDatePicker startDateField;
	@FXML private JFXTimePicker startTimeField;
	@FXML private Label validationLabel;
	@FXML private JFXButton confirmButton, cancelButton;

	/*
	 * -----------------------------------------------------------------------------
	 * Init
	 */
	@FXML
	public void initialize() {

		// Cancel
		cancelButton.setOnAction((e) -> {
			((SessionSceneController) getParentController()).displayOnRightPane("SessionTabs");
		});

		// Add
		confirmButton.setOnAction(e -> handleConfirm());

	}

	/*
	 * -----------------------------------------------------------------------------
	 * Private helpers
	 */

	private boolean allFieldsOk() {
		boolean titleFilledIn = DataValidation.textFilledIn(titleField, validationLabel, "Titel is verplicht");
		boolean durationFilledIn = DataValidation.textFilledIn(durationField, validationLabel, "Duurtijd is verplicht");
		boolean durationNumeric = DataValidation.textNumeric(durationField, validationLabel,
				"Duurtijd moet een getal zijn");

		boolean startDateFilledIn = DataValidation.dateFilledIn(startDateField, validationLabel,
				"Startdatum is verplicht");
		boolean startTimeFilledIn = DataValidation.timeFilledIn(startTimeField, validationLabel,
				"Starttijd is verplicht");

		boolean capacityNumeric = DataValidation.textNumeric(capacityField, validationLabel,
				"Capaciteit moet een getal zijn");

		return titleFilledIn && durationFilledIn && durationNumeric && startDateFilledIn && startTimeFilledIn
				&& capacityNumeric;
	}

	private void handleConfirm() {
		// Do validation
		validationLabel.setText("");
		if (!allFieldsOk())
			return;

		// Get fields
		String title = titleField.getText();
		String speaker = speakerField.getText();
		LocalDate startDate = startDateField.getValue();
		LocalTime startTime = startTimeField.getValue();
		String duration = durationField.getText();
		String location = locationField.getText();
		String capacity = capacityField.getText();

		// Create a new session via facades
		// TODO deal with business logic exceptions
		SessionCalendarFacade scf = (SessionCalendarFacade) getFacade();
		MemberFacade mf = (MemberFacade) getMainController().getMemberFacade();

		Member organizer = mf.getLoggedInMember();

		Session s = scf.createSessionFromFields(
				organizer, title, speaker, startDate, startTime, duration, location, capacity);

		scf.addSession(s);

		// In the end, go back to the details view
		((SessionSceneController) getParentController()).displayOnRightPane("SessionTabs");
	}

}
