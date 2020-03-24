package gui.controllers;

import java.util.stream.Stream;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.domain.Member;
import main.domain.MemberStatus;
import main.domain.MemberType;
import main.domain.facades.MemberFacade;
import main.exceptions.InvalidMemberException;
import main.services.DataValidation;

public class NewUserController extends GuiController {

	@FXML
	private JFXTextField firstNameField;
	@FXML
	private JFXTextField lastNameField;
	@FXML
	private JFXTextField usernameField;
	@FXML
	private JFXComboBox<MemberType> userTypeField;
	@FXML
	private JFXComboBox<MemberStatus> userStatusField;
    @FXML
    private JFXTextField profilePicField;
	@FXML
	private JFXButton addUserButton;
	@FXML
	private JFXButton cancelButton;
	@FXML
	private Label validationLabel;

	/*
	 * Init
	 */
	@FXML
	public void initialize() {

		// Event Listeners
		cancelButton.setOnAction(e -> goBack());
		addUserButton.setOnAction(e -> onNewMemberConfirm());

		// Set combobox options
		userTypeField.getItems().setAll(MemberType.values());
		userTypeField.getSelectionModel().select(MemberType.USER);
		userStatusField.getItems().setAll(MemberStatus.values());
		userStatusField.getSelectionModel().select(MemberStatus.ACTIVE);
	}

	/*
	 * Private helpers
	 */

	private void goBack() {
		// clears fields and goes back to details view
		resetView();
		((UserSceneController) getParentController()).displayOnRightPane("UserDetails");
	}

	private void resetView() {
		validationLabel.setText("");
		Stream.<TextField>of(firstNameField, lastNameField, usernameField, profilePicField).forEach(tf -> tf.setText(""));
		userTypeField.getSelectionModel().selectFirst();
		userStatusField.getSelectionModel().selectFirst();
	}

	private boolean allFieldsOk() {
		boolean firstNameFilledIn = DataValidation.textFilledIn(firstNameField, validationLabel,
				"Voornaam is verplicht");
		boolean lastNameFilledIn = DataValidation.textFilledIn(lastNameField, validationLabel,
				"Achternaam is verplicht");
		boolean usernameFilledIn = DataValidation.textFilledIn(usernameField, validationLabel,
				"Gebruikersnaam is verplicht");
		boolean profilePicOk;
		if (profilePicField.getText() == null || profilePicField.getText().isBlank()) {
			profilePicOk = true;
		} else {
			profilePicOk = DataValidation.textImagePath(profilePicField, validationLabel, "URL voor profiel foto klopt niet");
		}

		return firstNameFilledIn && lastNameFilledIn && usernameFilledIn && profilePicOk;
	}

	private void onNewMemberConfirm() {
		// Do validation
		validationLabel.setText("");
		if (!allFieldsOk())
			return;

		// Get fields
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String userName = usernameField.getText();
		MemberType type = userTypeField.getValue();
		MemberStatus status = userStatusField.getValue();
		String profilePicPath = profilePicField.getText();
		
		if (profilePicPath == null || profilePicPath.isBlank()) {
			profilePicPath = "";
		}

		MemberFacade mf = (MemberFacade) getFacade();
		
		try {
			// Construct user
			Member m = mf.createMemberFromFields(userName, firstName, lastName, type, status, profilePicPath);
			
			// Add user
			mf.addMember(m);
			
			// if adding is successful
			getMainController().getUserSceneController().updateWithMember(m); // update tableview with new member
			goBack(); // clears fields and goes back to details view
			
		} catch (InvalidMemberException e) {
			if (e.getMessage() != null) {
				validationLabel.setText(e.getMessage());
			}
		}
	}
}