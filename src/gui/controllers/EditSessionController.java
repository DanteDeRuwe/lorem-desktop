package gui.controllers;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import main.domain.facades.LoggedInMemberManager;
import main.domain.facades.SessionCalendarFacade;
import main.exceptions.InvalidSessionException;
import main.exceptions.UserNotAuthorizedException;
import main.services.Alerts;
import main.services.DataValidation;
import main.services.GuiUtil;

public class EditSessionController extends GuiController {

	// Nodes
	@FXML
	private JFXTextArea descriptionArea;
	@FXML
	private JFXTextField titleField, speakerField, durationField, locationField, capacityField, externalLinkField,
			typeField;
	@FXML
	private JFXDatePicker startDateField;
	@FXML
	private JFXTimePicker startTimeField;
	@FXML
	private Label validationLabel, headerText;
	@FXML
	private JFXButton confirmButton, cancelButton;

	private Session sessionToEdit;
	private SessionSceneController ssc;
	private SessionCalendarFacade scf;

	@FXML
	public void initialize() {
		ssc = ((SessionSceneController) getParentController());
		scf = (SessionCalendarFacade) getFacade();

		// 24hour view on the time picker
		GuiUtil.fixTimePicker(startTimeField);

		// Date picker formats
		GuiUtil.fixDatePicker(startDateField);

		// limit char counts for all fields (for db)
		GuiUtil.limitCharacterCount(descriptionArea, 8000);
		GuiUtil.limitCharacterCount(new JFXTextField[] { titleField, speakerField, durationField, locationField,
				capacityField, externalLinkField, typeField }, 255);

		// get the session
		sessionToEdit = getMainController().getSessionSceneController().getInspectedSession();

		// we re-use the new session fxml... Change some text
		confirmButton.setText("Bevestig");
		headerText.setText("Wijzig sessie \"" + sessionToEdit.getTitle() + "\"");

		// Pre-fill the fields
		fillFields();

		// Event Listeners
		cancelButton.setOnAction(e -> goBack());
		confirmButton.setOnAction(e -> onSessionEdit());
	}

	/*
	 * Private helpers
	 */

	private void fillFields() {
		titleField.setText(sessionToEdit.getTitle());
		descriptionArea.setText(sessionToEdit.getDescription());
		speakerField.setText(sessionToEdit.getSpeakerName());
		locationField.setText(sessionToEdit.getLocation());
		capacityField.setText(Integer.toString(sessionToEdit.getCapacity()));
		externalLinkField.setText(sessionToEdit.getExternalLink());
		typeField.setText(sessionToEdit.getType());

		LocalDateTime start = sessionToEdit.getStart();
		startDateField.setValue(start.toLocalDate());
		startTimeField.setValue(start.toLocalTime());

		Duration duration = Duration.between(start, sessionToEdit.getEnd());
		long durationHours = duration.toDaysPart() * 24 + duration.toHoursPart();
		int durationMinutes = duration.toMinutesPart();
		durationField.setText(String.format("%d:%02d", durationHours, durationMinutes));
	}

	private void goBack() {
		// clears fields and goes back to details view
		resetView();
		ssc.displayOnRightPane("SessionTabs");
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

	private void onSessionEdit() {
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
		String externalLink = externalLinkField.getText();
		String type = typeField.getText();

		// Try to create a new session, all business logic is handled there already
		Member organizer = LoggedInMemberManager.getInstance().getLoggedInMember();

		try {
			// Construct session template with all updated fields
			Session template = scf.createSessionFromFields(organizer, title, description, speaker, startDate, startTime,
					duration, location, capacity, externalLink, type);

			// Edit the session
			scf.editSession(sessionToEdit, template);
			ssc.updateWithSession(sessionToEdit);
			goBack(); // clears fields and goes back to details view
		} catch (InvalidSessionException e) {
			if (e.getMessage() != null) {
				validationLabel.setText(e.getMessage());
			}
		} catch (UserNotAuthorizedException e) {
			Alerts.errorAlert("Sessie wijzigen", "Je hebt niet de juiste machtigingen om deze sessie te wijzigen.")
					.show();
		}
	}

}
