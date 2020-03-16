package gui.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.domain.Member;
import main.domain.MemberStatus;
import main.domain.MemberType;
import main.domain.Session;
import main.domain.facades.MemberFacade;
import main.domain.facades.SessionCalendarFacade;
import main.exceptions.InvalidSessionException;
import main.services.DataValidation;
import main.services.GuiUtil;

public class NewUserController extends GuiController {

    @FXML private JFXTextField firstNameField;
    @FXML private JFXTextField lastNameField;
    @FXML private JFXTextField usernameField;
    @FXML private JFXComboBox<String> userTypeField;
    @FXML private JFXComboBox<String> userStatusField;
    @FXML private JFXButton photoUploadButton;
    @FXML private JFXButton addUserButton;
    @FXML private JFXButton cancelButton;
    @FXML private Label validationLabel;
    

	/*
	 * Init
	 */
	@FXML
	public void initialize() {

		// Event Listeners
		cancelButton.setOnAction(e -> goBack());
		addUserButton.setOnAction(e -> onNewSessionConfirm());
		
		// Set combobox options
		ObservableList<String> hobbies = FXCollections.observableArrayList("Tennis", "Football", "Cricket", "CoCO", "Rugby", "kabaddy");
		userTypeField.setItems(hobbies);
//		userTypeField.setValue(new ob);
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
		Stream.<TextField>of(firstNameField, lastNameField, usernameField)
				.forEach(tf -> tf.setText(""));
		userTypeField.getSelectionModel().selectFirst();
		userStatusField.getSelectionModel().selectFirst();
	}
	
	private boolean allFieldsOk() {
		boolean firstNameFilledIn = DataValidation.textFilledIn(firstNameField, validationLabel, "Voornaam is verplicht");
		boolean lastNameFilledIn = DataValidation.textFilledIn(lastNameField, validationLabel, "Achternaam is verplicht");
		boolean usernameFilledIn = DataValidation.textFilledIn(usernameField, validationLabel, "Gebruikersnaam is verplicht");
		
		return firstNameFilledIn && lastNameFilledIn && usernameFilledIn;
	}
	
	private void onNewSessionConfirm() {
		// Do validation
		validationLabel.setText("");
		if (!allFieldsOk())
			return;

		// Get fields
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String userName = usernameField.getText();

		// Create a new session via facades
		MemberFacade mf = (MemberFacade) getFacade();

		
		// Construct member
		Member m = new Member(userName, firstName, lastName, MemberType.USER, MemberStatus.ACTIVE);
		
		// Add session
		mf.addMember(m);

		getMainController().getUserSceneController().updateWithMember(m); // update tableview with new member
		goBack(); // clears fields and goes back to details view

		
	}



}