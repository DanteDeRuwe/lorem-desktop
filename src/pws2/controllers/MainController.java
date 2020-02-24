package pws2.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class MainController extends VBox {

	@FXML
	private MenuItem quitMenuItem;

	public MainController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../fxml/Main.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		quitMenuItem.setText("test");
	}

}
