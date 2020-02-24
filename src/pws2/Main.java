package pws2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pws2.controllers.MainController;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(new MainController()));
		stage.show();
	}

}
