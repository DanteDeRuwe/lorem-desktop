package gui.controllers;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.domain.Member;
import main.domain.facades.LoggedInMemberManager;
import main.domain.facades.MemberFacade;
import main.exceptions.UserNotAuthorizedException;
import main.services.Alerts;

public class UserDetailsController extends GuiController {

	private Member inspectedUser;

	@FXML
	private Label nameLabel, membertypeLabel, usernameLabel, statusLabel, totalRegistrations, totalAttendances, totalMissedSessions;
	@FXML
	private JFXButton editUserButton, deleteUserButton;
	@FXML
	private ImageView profilePicView;

	@FXML
	public void initialize() {
		UserSceneController usc = getMainController().getUserSceneController();

		// Hide buttons for manipulating users if not permitted
		boolean buttonsvisible = LoggedInMemberManager.getInstance().loggedInMemberCanManipulateUsers();

		if (!buttonsvisible) {
			deleteUserButton.setVisible(false);
			editUserButton.setVisible(false);
		} else {
			// Event handlers
			deleteUserButton.setOnAction((event) -> handleDeleteUser());
			editUserButton.setOnMouseClicked((e) -> {
				usc.displayOnRightPane("EditUser");
			});
		}

	}

	private void handleDeleteUser() {
		Alert alert = Alerts.confirmationAlert("Gebruiker verwijderen", String.format(
				"Ben je zeker dat je de gebruiker \"%s\" wilt verwijderen? Dit zal ook alle sessies georganiseerd door deze gebruiker verwijderen.",
				inspectedUser.getFullName()));
		if (alert.showAndWait().get() == ButtonType.OK) {
			try {
				((MemberFacade) getFacade()).deleteUser(inspectedUser);
			} catch (IllegalArgumentException e) {
				alert.close();
				Alerts.errorAlert("Gebruiker verwijderen", "Je kan de ingelogde gebruiker niet verwijderen.")
						.showAndWait();
			} catch (UserNotAuthorizedException e) {
				Alerts.errorAlert("Gebruiker verwijderen",
						"Je hebt niet de juiste machtigingen om een gebruiker te verwijderen.").showAndWait();
			}
			getMainController().getUserSceneController().update();

			// deleting a user also deletes their sessions, so sessions screen has to be
			// updated
			if (getMainController().getSessionSceneController() != null) {
				getMainController().getSessionSceneController().update();
			}	
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
		statusLabel.setText(inspectedUser.getMemberStatus().toString());
		totalRegistrations.setText(String.valueOf(inspectedUser.countRegistrations()));
		totalAttendances.setText(String.valueOf(inspectedUser.countAttendances()));
		totalMissedSessions.setText(String.valueOf(inspectedUser.countMissedSessions()));
		
		switch (inspectedUser.getMemberStatus()) {
		case ACTIVE:
			statusLabel.setStyle("-fx-text-fill: green;");
			break;
		case INACTIVE:
			statusLabel.setStyle("-fx-text-fill: orange;");
			break;
		case BLOCKED:
			statusLabel.setStyle("-fx-text-fill: red;");
		}

		// Set profile picture

		if (inspectedUser.getProfilePicPath() == null || inspectedUser.getProfilePicPath().isEmpty()) {
			profilePicView.setImage(new Image(
					"https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png"));
		} else {
			profilePicView.setImage(new Image(inspectedUser.getProfilePicPath()));
		}
	}

	public Member getInspectedUser() {
		return inspectedUser;
	}

}
