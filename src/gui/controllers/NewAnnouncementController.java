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
import main.services.GuiUtil;

public class NewAnnouncementController extends GuiController {

	@FXML
	private JFXTextField titleField;
	@FXML
	private JFXTextArea textArea;
	@FXML
	private JFXButton confirmButton, cancelButton;
	@FXML
	private Label validationLabel, headerText;

	private AnnouncementTabController atc;
	private Session inspectedSession;

	@FXML
	public void initialize() {

		atc = (AnnouncementTabController) getParentController();
		setInspectedSession(atc.getInspectedSession());

		updateHeader();

		// limit char counts for all fields (for db)
		GuiUtil.limitCharacterCount(textArea, 8000);
		GuiUtil.limitCharacterCount(titleField, 255);

		// Event Listeners
		cancelButton.setOnAction(e -> goBack());
		confirmButton.setOnAction(e -> handleCreate());
	}

	public void updateHeader() {
		headerText.setText(String.format("Nieuwe aankondiging voor \"%s\"", inspectedSession.getTitle()));
	}

	private void handleCreate() {

		// title is mandatory, exit the function if it's not filled in
		if (!DataValidation.textFilledIn(titleField, validationLabel, "Titel is verplicht"))
			return;

		// Get fields
		String title = titleField.getText();
		String text = textArea.getText();

		SessionFacade sf = (SessionFacade) getFacade();

		Announcement a = sf.createAnnouncementFromFields(
				getMainController().getLoggedInMemberManager().getLoggedInMember(), text.trim(), title.trim());

		try {
			sf.addAnnouncement(a, inspectedSession);
		} catch (UserNotAuthorizedException e) {
			Alerts.errorAlert("Aankondiging verwijderen",
					"Je hebt niet de juiste machtigingen om deze aankondiging te verwijderen.");
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

	public Session getInspectedSession() {
		return inspectedSession;
	}

	public void setInspectedSession(Session inspectedSession) {
		this.inspectedSession = inspectedSession;
	}

}