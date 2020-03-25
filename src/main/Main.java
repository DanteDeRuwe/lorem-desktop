package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import persistence.GenericDaoJpa;

public class Main extends Application {

	private Parent rootNode;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void init() throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/login/login.fxml"));
		rootNode = fxmlLoader.load();
	}

	@Override
	public void start(Stage stage) throws Exception {

		Scene scene = new Scene(rootNode);

		
		stage.setScene(scene);
		stage.setTitle("Login");

		stage.show();

	}

}
