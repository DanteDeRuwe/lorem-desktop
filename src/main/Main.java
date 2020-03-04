package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistence.SessionDao;

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
		stage.setScene(new Scene(rootNode));
		stage.setMaximized(true);
		stage.setTitle("Lorem");
		stage.show();
	}

}
