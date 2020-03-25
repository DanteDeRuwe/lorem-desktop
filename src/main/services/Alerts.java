package main.services;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {

	public static Alert errorAlert(String title, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText("Fout");
		alert.setContentText(content);
		return alert;
	}

	public static Alert confirmationAlert(String title, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText("Waarschuwing");
		alert.setContentText(content);
		alert.setHeight(300);
		return alert;
	}

}
