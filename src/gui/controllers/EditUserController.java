package gui.controllers;

import java.util.stream.Stream;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.domain.Member;
import main.domain.MemberStatus;
import main.domain.MemberType;
import main.domain.facades.MemberFacade;
import main.exceptions.InvalidMemberException;
import main.exceptions.UserNotAuthorizedException;
import main.services.Alerts;
import main.services.DataValidation;
import main.services.GuiUtil;

public class EditUserController extends GuiController {
	@FXML
	private JFXTextField firstNameField, lastNameField, usernameField, profilePicField;
	@FXML
	private JFXPasswordField passwordField;
	@FXML
	private JFXComboBox<MemberType> userTypeField;
	@FXML
	private JFXComboBox<MemberStatus> userStatusField;
	@FXML
	private JFXButton addUserButton, cancelButton;
	@FXML
	private Label validationLabel, headerText;

	// Fields
	Member userToEdit;

	/*
	 * Init
	 */
	@FXML
	public void initialize() {

		// limit char counts for all fields (for db)
		GuiUtil.limitCharacterCount(
				new JFXTextField[] { firstNameField, lastNameField, usernameField, profilePicField }, 255);

		// get the session
		userToEdit = getMainController().getUserSceneController().getInspectedUser();

		// we re-use the new user fxml... Change some text
		addUserButton.setText("Bevestig");
		headerText.setText("Wijzig gebruiker \"" + userToEdit.getFullName() + "\"");

		// Pre-fill the fields
		fillFields();

		// Event Listeners
		cancelButton.setOnAction(e -> goBack());
		addUserButton.setOnAction(e -> onMemberEditConfirm());

		// Set combobox options
		userTypeField.getItems().setAll(MemberType.values());
		userTypeField.getSelectionModel().select(MemberType.USER);
		userStatusField.getItems().setAll(MemberStatus.values());
		userStatusField.getSelectionModel().select(MemberStatus.ACTIVE);
	}

	/*
	 * Private helpers
	 */

	private void fillFields() {
		firstNameField.setText(userToEdit.getFirstName());
		lastNameField.setText(userToEdit.getLastName());
		usernameField.setText(userToEdit.getUsername());
		userTypeField.setValue(userToEdit.getMemberType());
		userStatusField.setValue(userToEdit.getMemberStatus());
		profilePicField.setText(userToEdit.getProfilePicPath());
	}

	private void goBack() {
		// clears fields and goes back to details view
		resetView();
		((UserSceneController) getParentController()).displayOnRightPane("UserDetails");
	}

	private void resetView() {
		validationLabel.setText("");
		Stream.<TextField>of(firstNameField, lastNameField, usernameField, profilePicField)
				.forEach(tf -> tf.setText(""));

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
			profilePicOk = DataValidation.textImagePath(profilePicField, validationLabel,
					"URL voor profiel foto klopt niet");
		}

		return firstNameFilledIn && lastNameFilledIn && usernameFilledIn && profilePicOk;
	}

	private void onMemberEditConfirm() {
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
			Member template = mf.createMemberFromFields(userName, firstName, lastName, type, status, profilePicPath,
					userToEdit);

			// Edit user
			try {
				mf.editMember(userToEdit, template, passwordField.getText());
			} catch (UserNotAuthorizedException e) {
				Alerts.errorAlert("Gebruiker wijzigen",
						"Je hebt niet de juiste machtigingen om een gebruiker te wijzigen.").showAndWait();
			}

			// if adding is successful
			getMainController().getUserSceneController().updateWithMember(userToEdit); // update tableview with new
																						// member
			goBack(); // clears fields and goes back to details view

		} catch (InvalidMemberException e) {
			if (e.getMessage() != null) {
				validationLabel.setText(e.getMessage());
			}
		}
	}
}
