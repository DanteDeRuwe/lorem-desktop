package gui.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.domain.Member;
import main.domain.Session;
import main.domain.SessionStatus;
import main.domain.facades.SessionCalendarFacade;
import main.exceptions.InvalidSessionException;
import main.exceptions.UserNotAuthorizedException;
import main.services.Alerts;
import main.services.DataValidation;
import main.services.GuiUtil;

public class NewSessionController extends GuiController {

	// Nodes
	@FXML
	private JFXTextArea descriptionArea;
	@FXML
	private JFXTextField titleField, speakerField, durationField, locationField, capacityField;
	@FXML
	private JFXDatePicker startDateField;
	@FXML
	private JFXTimePicker startTimeField;
	@FXML
	private Label validationLabel;
	@FXML
	private JFXButton confirmButton, cancelButton;
	@FXML
	private JFXTextField externalLinkField;
	@FXML
	private JFXTextField typeField;

	/*
	 * Init
	 */
	@FXML
	public void initialize() {

		// 24hour view on the time picker
		GuiUtil.fixTimePicker(startTimeField);

		// Date picker formats
		GuiUtil.fixDatePicker(startDateField);

		// Event Listeners
		cancelButton.setOnAction(e -> goBack());
		confirmButton.setOnAction(e -> onNewSessionConfirm());
	}

	/*
	 * Private helpers
	 */

	private void goBack() {
		// clears fields and goes back to details view
		resetView();
		((SessionSceneController) getParentController()).displayOnRightPane("SessionTabs");
	}

	private void resetView() {
		validationLabel.setText("");
		Stream.<TextField>of(titleField, speakerField, durationField, locationField, capacityField, externalLinkField,
				typeField).forEach(tf -> tf.setText(""));

		descriptionArea.setText("");
		startDateField.setValue(null);
		startTimeField.setValue(null);
	}

	private boolean allFieldsOk() {
		boolean titleFilledIn = DataValidation.textFilledIn(titleField, validationLabel, "Titel is verplicht");
		boolean durationFilledIn = DataValidation.textFilledIn(durationField, validationLabel, "Duurtijd is verplicht");
		boolean durationIsDuration = DataValidation.isDuration(durationField, validationLabel,
				"Duurtijd moet van het formaat (u)u:mm zijn");

		boolean startDateFilledIn = DataValidation.dateFilledIn(startDateField, validationLabel,
				"Startdatum is verplicht");
		boolean startTimeFilledIn = DataValidation.timeFilledIn(startTimeField, validationLabel,
				"Starttijd is verplicht");

		boolean capacityNumeric = DataValidation.textNumeric(capacityField, validationLabel,
				"Capaciteit moet een getal zijn");
		boolean externalLinkOk;
		if (externalLinkField.getText() == null || externalLinkField.getText().isBlank()) {
			externalLinkOk = true;
		} else {
			externalLinkOk = DataValidation.textExternalLink(externalLinkField, validationLabel,
					"URL van de externe link is ongeldig");
		}

		return titleFilledIn && durationFilledIn && durationIsDuration && startDateFilledIn && startTimeFilledIn
				&& capacityNumeric && externalLinkOk;
	}

	private void onNewSessionConfirm() {
		// Do validation
		validationLabel.setText("");
		if (!allFieldsOk())
			return;

		// Get fields
		String title = titleField.getText();
		String description = descriptionArea.getText();
		String speaker = speakerField.getText();
		LocalDate startDate = startDateField.getValue();
		LocalTime startTime = startTimeField.getValue();
		String duration = durationField.getText();
		String location = locationField.getText();
		String capacity = capacityField.getText();
		String externalUrl = externalLinkField.getText();
		String type = typeField.getText();

		// Create a new session via facades
		SessionCalendarFacade scf = (SessionCalendarFacade) getFacade();

		Member organizer = getMainController().getLoggedInMemberManager().getLoggedInMember();

		try {
			// Construct session
			Session s = scf.createSessionFromFields(organizer, title, description, speaker, startDate, startTime,
					duration, location, capacity, externalUrl, type);

			// Set status as created
			s.setSessionStatus(SessionStatus.CREATED);

			// Add session
			scf.addSession(s);

			// if adding is succesful
			getMainController().getSessionSceneController().updateWithSession(s); // update tableview with new session
			goBack(); // clears fields and goes back to details view

		} catch (InvalidSessionException e) {
			if (e.getMessage() != null) {
				validationLabel.setText(e.getMessage());
			}
		} catch (UserNotAuthorizedException e) {
			Alerts.errorAlert("Sessie aanmaken", "Je hebt niet de juiste machtigingen om een sessie aan te maken.")
					.show();
		}
	}

}
