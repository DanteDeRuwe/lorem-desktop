package gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.domain.Announcement;
import main.domain.facades.MemberFacade;
import main.domain.facades.SessionFacade;
import main.services.DataValidation;

public class NewAnnouncementController extends GuiController {

	@FXML
	private JFXTextField titleField;
	@FXML
	private JFXTextArea textArea;
	@FXML
	private JFXButton confirmButton, cancelButton;
	@FXML
	private Label validationLabel;

	private AnnouncementTabController atc;

	@FXML
	public void initialize() {

		atc = (AnnouncementTabController) getParentController();

		// Event Listeners
		cancelButton.setOnAction(e -> goBack());
		confirmButton.setOnAction(e -> handleCreate());
	}

	private void handleCreate() {
		// title is mandatory, exit the function if it's not filled in
		if (!DataValidation.textFilledIn(titleField, validationLabel, "Titel is verplicht"))
			return;

		// Get fields
		String title = titleField.getText();
		String text = textArea.getText();

		MemberFacade mf = (MemberFacade) getMainController().getMemberFacade();
		SessionFacade sf = (SessionFacade) getFacade();
		Announcement a = sf.createAnnouncementFromFields(mf.getLoggedInMember(), text, title);

		sf.addAnnouncement(a, ((AnnouncementTabController) getParentController()).getInspectedSession());

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

}