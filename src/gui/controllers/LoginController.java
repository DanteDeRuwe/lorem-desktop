package gui.controllers;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.domain.Member;
import main.domain.facades.LoggedInMemberManager;
import main.domain.facades.MemberFacade;
import main.domain.facades.SessionCalendarFacade;
import main.exceptions.UserNotAuthorizedException;
import main.services.GuiUtil;
import persistence.GenericDaoJpa;

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
		memberFacade = new MemberFacade(loggedInMemberManager);

		// Event handlers
		loginButton.setOnAction(e -> {
			try {
				tryLogin();
			} catch (IOException e1) {}
		});

	}

	private void tryLogin() throws IOException {
		if (passwordOk()) {
			loadMain();
			((Stage) this.loginButton.getScene().getWindow()).close();
		} else {
			validationLabel.setText("Gebruikersnaam of wachtwoord verkeerd");
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
		
		stage.setOnCloseRequest(e -> {
			GenericDaoJpa.closePersistency();
			Platform.exit();
			System.exit(0);
		});
		
	}

	private boolean passwordOk() {
		String username = usernameField.getText();
		String password = passwordField.getText();
		
		Member member;
		try {
			member = memberFacade.getMemberByUsername(username);
		} catch (Exception e) {
			return false;
		}
		
		
		loggedInMemberManager.setLoggedInMember(member);
		
		return member.passwordCorrect(password);
	}

}
