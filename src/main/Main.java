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
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/Main.fxml"));
		rootNode = fxmlLoader.load();
	}

	@Override
	public void start(Stage stage) throws Exception {

		Scene scene = new Scene(rootNode);

		scene.getStylesheets().add(getClass().getResource("/resources/css/style.css").toExternalForm());

		stage.setScene(scene);
		stage.setMaximized(true);
		stage.setTitle("Lorem");
		stage.show();

		stage.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if (KeyCode.F11.equals(e.getCode()))
				stage.setFullScreen(!stage.isFullScreen());
		});

		stage.setOnCloseRequest(e -> handleExit());

	}

	private void handleExit() {
		GenericDaoJpa.closePersistency();
		Platform.exit();
		System.exit(0);
	}

}
