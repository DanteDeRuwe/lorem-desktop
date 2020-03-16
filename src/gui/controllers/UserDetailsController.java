package gui.controllers;

import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import main.domain.Member;
import main.domain.facades.MemberFacade;

public class UserDetailsController extends GuiController {

	private Member inspectedUser;

	@FXML
	private Label nameLabel;
	@FXML
	private Label membertypeLabel;
	@FXML
	private Label usernameLabel;
	@FXML
	private JFXButton editUserButton;
	@FXML
	private JFXButton deleteUserButton;

	@FXML
	public void initialize() {
		deleteUserButton.setOnAction((event) -> handleDeleteUser());
	}

	private void handleDeleteUser() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Gebruiker verwijderen");
		alert.setHeaderText("Waarschuwing");
		alert.setContentText(String.format("Ben je zeker dat je de gebruiker \"%s\" wilt verwijderen?",
				inspectedUser.getFullName()));

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			try {
				((MemberFacade) getFacade()).deleteUser(inspectedUser);
			} catch (IllegalArgumentException e) {
				alert.close();
				Alert exceptionAlert = new Alert(AlertType.ERROR);
				exceptionAlert.setTitle("Gebruiker verwijderen");
				exceptionAlert.setHeaderText("Fout");
				exceptionAlert.setContentText("Je kan de ingelogde gebruiker niet verwijderen.");
				exceptionAlert.show();
			}
			getMainController().getUserSceneController().update();
		}
	}

	public void setInspectedUser(Member member) {
		inspectedUser = member;
		updateLabels();
	}

	private void updateLabels() {
		if (inspectedUser == null)
			return;

		nameLabel.setText(inspectedUser.getFullName());
		membertypeLabel.setText(inspectedUser.getMemberType().toString());
		usernameLabel.setText(inspectedUser.getUsername());
	}
}
