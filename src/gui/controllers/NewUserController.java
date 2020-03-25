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

public class NewUserController extends GuiController {

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
	private Label validationLabel;

	private UserSceneController usc;
	MemberFacade mf;

	@FXML
	public void initialize() {
		usc = ((UserSceneController) getParentController());
		mf = (MemberFacade) getFacade();

		// limit char counts for all fields (for db)
		GuiUtil.limitCharacterCount(
				new JFXTextField[] { firstNameField, lastNameField, usernameField, profilePicField }, 255);

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
		usc.displayOnRightPane("UserDetails");
	}

	private void resetView() {
		validationLabel.setText("");
		Stream.<TextField>of(firstNameField, lastNameField, usernameField, profilePicField, passwordField)
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
		boolean passwordFilledIn = DataValidation.textFilledIn(passwordField, validationLabel,
				"Wachtwoord is verplicht");
		boolean profilePicOk;
		if (profilePicField.getText() == null || profilePicField.getText().isBlank()) {
			profilePicOk = true;
		} else {
			profilePicOk = DataValidation.textImagePath(profilePicField, validationLabel,
					"URL voor profiel foto klopt niet");
		}

		return firstNameFilledIn && lastNameFilledIn && usernameFilledIn && passwordFilledIn && profilePicOk;
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
		String password = passwordField.getText();
		MemberType type = userTypeField.getValue();
		MemberStatus status = userStatusField.getValue();
		String profilePicPath = profilePicField.getText();

		if (profilePicPath == null || profilePicPath.isBlank())
			profilePicPath = "";

		try {
			// Construct user
			Member m = mf.createMemberFromFields(userName, firstName, lastName, type, status, profilePicPath);
			m.setPassword(password);

			// Add user
			try {
				mf.addMember(m);
			} catch (UserNotAuthorizedException e) {
				Alerts.errorAlert("Gebruiker toevoegen",
						"Je hebt niet de juiste machtigingen om een gebruiker toe te voegen.").showAndWait();
			}

			// if adding is successful
			usc.updateWithMember(m); // update tableview with new member
			goBack(); // clears fields and goes back to details view

		} catch (InvalidMemberException e) {
			if (e.getMessage() != null) {
				validationLabel.setText(e.getMessage());
			}
		}
	}
}