package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application {

	private Parent rootNode;

	@Override
	public void init() throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
		rootNode = fxmlLoader.load();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(rootNode));
		stage.show();
	}

}