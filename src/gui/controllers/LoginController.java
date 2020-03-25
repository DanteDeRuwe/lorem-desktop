package gui.controllers;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.domain.Member;
import main.domain.MemberType;
import main.domain.facades.LoggedInMemberManager;
import main.domain.facades.MemberFacade;

public class LoginController {

	@FXML
	private JFXTextField usernameField;

	@FXML
	private JFXPasswordField passwordField;

	@FXML
	private JFXButton loginButton;

	@FXML
	private Label validationLabel;

	// facades
	LoggedInMemberManager loggedInMemberManager;
	MemberFacade memberFacade;

	@FXML
	public void initialize() {

		// initialize facades
		loggedInMemberManager = LoggedInMemberManager.getInstance();
		memberFacade = new MemberFacade();

		// Event handlers
		loginButton.setOnAction(e -> {
			try {
				tryLogin();
			} catch (IOException e1) {
			}
		});
		passwordField.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if (e.getCode() == KeyCode.ENTER) {
				loginButton.fire();
				e.consume();
			}
		});
		usernameField.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if (e.getCode() == KeyCode.ENTER) {
				loginButton.fire();
				e.consume();
			}
		});
	}

	private void tryLogin() throws IOException {
		String username = usernameField.getText();
		String password = passwordField.getText();
		try {
			Member member = memberFacade.getMemberByUsername(username);
			if (member.getMemberType() == MemberType.USER)
				validationLabel.setText("Gewone gebruikers hebben geen toegang");
			else if (!member.passwordCorrect(password))
				validationLabel.setText("Fout wachtwoord");
			else {
				loggedInMemberManager.setLoggedInMember(member);
				loadMain();
				((Stage) this.loginButton.getScene().getWindow()).close();
			}
		} catch (EntityNotFoundException e) {
			validationLabel.setText("Er bestaat geen gebruiker met de opgegeven gebruikersnaam");
		}
	}

	private void loadMain() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/Main.fxml"));
		Parent rootNode = fxmlLoader.load();

		Scene scene = new Scene(rootNode);

		Stage stage = new Stage();

		scene.getStylesheets().add(getClass().getResource("/resources/css/style.css").toExternalForm());

		stage.setScene(scene);
		stage.setMaximized(true);
		stage.setTitle("Lorem");

		stage.setMinHeight(800);
		stage.setMinWidth(900);
		stage.show();

		stage.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if (KeyCode.F11.equals(e.getCode()))
				stage.setFullScreen(!stage.isFullScreen());
		});

	}

}
