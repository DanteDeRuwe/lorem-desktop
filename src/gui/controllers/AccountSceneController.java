package gui.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.domain.Member;
import main.domain.facades.LoggedInMemberManager;

public class AccountSceneController extends GuiController {

	private LoggedInMemberManager loggedInMemberManager;

	@FXML
	private Label usernameLabel, firstNameLabel, lastNameLabel, typeLabel, statusLabel;
	@FXML
	private ImageView profilePicture;
	@FXML
	private Button logOutButton;

	@FXML
	public void initialize() {
		loggedInMemberManager = LoggedInMemberManager.getInstance();
		logOutButton.setOnMouseClicked((event) -> handleLogout());
		update();
	}

	private void handleLogout() {
		getMainController().closeMainWindow();
		loggedInMemberManager.setLoggedInMember(null);

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/login/login.fxml"));
		Parent rootNode;
		try {
			rootNode = fxmlLoader.load();
			Scene scene = new Scene(rootNode);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Login");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		Member member = loggedInMemberManager.getLoggedInMember();
		usernameLabel.setText(member.getUsername());
		firstNameLabel.setText(member.getFirstName());
		lastNameLabel.setText(member.getLastName());
		typeLabel.setText(member.getMemberType().toString());
		statusLabel.setText(member.getMemberStatus().toString());
		if (member.getProfilePicPath().isBlank() || member.getProfilePicPath() == null)
			profilePicture.setImage(new Image(
					"https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png"));
		else
			profilePicture.setImage(new Image(member.getProfilePicPath()));
	}

}
