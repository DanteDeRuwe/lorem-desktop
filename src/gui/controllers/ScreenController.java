package gui.controllers;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ScreenController {
	private HashMap<String, Pane> screenMap = new HashMap<>();
	private Scene main;

	public ScreenController(Scene main) {
		this.main = main;
	}

	protected void addScreen(String name, Pane pane) {
		screenMap.put(name, pane);
	}

	protected void addScreen(String name, String fxmlFile) {
		try {
			screenMap.put(name, FXMLLoader.load(getClass().getResource("/resources/fxml/" + fxmlFile)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void removeScreen(String name) {
		screenMap.remove(name);
	}

	protected void activate(String name) {
		main.setRoot(screenMap.get(name));
	}
}