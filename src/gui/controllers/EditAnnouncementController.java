package gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.domain.Announcement;
import main.domain.Session;
import main.domain.facades.SessionFacade;
import main.exceptions.UserNotAuthorizedException;
import main.services.Alerts;
import main.services.DataValidation;

public class EditAnnouncementController extends GuiController {

	@FXML
	private JFXTextField titleField;
	@FXML
	private JFXTextArea textArea;
	@FXML
	private JFXButton confirmButton, cancelButton;
	@FXML
	private Label validationLabel, headerText;

	private AnnouncementTabController atc;
	private Announcement inspectedAnnouncement;
	private Session inspectedSession;
	private SessionFacade sf;

	@FXML
	public void initialize() {
		sf = (SessionFacade) getFacade();

		atc = (AnnouncementTabController) getParentController();
		inspectedSession = atc.getInspectedSession();
		inspectedAnnouncement = atc.getInspectedAnnouncement();

		headerText.setText(String.format("Wijzig aankondiging \"%s\"", inspectedAnnouncement.getTitle()));
		fillFields();

		// Event Listeners
		cancelButton.setOnAction(e -> goBack());
		confirmButton.setOnAction(e -> handleEdit());
	}

	private void fillFields() {
		titleField.setText(inspectedAnnouncement.getTitle());
		textArea.setText(inspectedAnnouncement.getText());
	}

	private void handleEdit() {

		// title is mandatory, exit the function if it's not filled in
		if (!DataValidation.textFilledIn(titleField, validationLabel, "Titel is verplicht"))
			return;

		// Get fields
		String title = titleField.getText();
		String text = textArea.getText();

		// Construct announcement template with all updated fields
		Announcement template = sf.createAnnouncementFromFields(
				getMainController().getLoggedInMemberManager().getLoggedInMember(), text, title);

		// Edit the session
		try {
			sf.editAnnouncement(inspectedAnnouncement, template, inspectedSession);
		} catch (UserNotAuthorizedException e) {
			Alerts.errorAlert("Aankondiging bewerken",
					"Je hebt niet de juiste machtigingen om deze aankondiging te bewerken.");
		}

		goBack();
	}

	private void goBack() {
		// clears fields and goes back to announcement overview
		resetView();
		atc.update();
		atc.showOverview();
	}

	private void resetView() {
		validationLabel.setText("");
		titleField.setText("");
		textArea.setText("");
	}

	public Announcement getInspectedAnnouncement() {
		return inspectedAnnouncement;
	}

	public void setInspectedAnnouncement(Announcement inspectedAnnouncement) {
		this.inspectedAnnouncement = inspectedAnnouncement;
	}

}