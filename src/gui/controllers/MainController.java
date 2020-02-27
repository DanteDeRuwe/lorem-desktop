package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.domain.Session;

public class MainController {

	@FXML
	private AnchorPane main;

	@FXML
	private TextField searchBox;

	@FXML
	private Button searchButton;

	@FXML
	private SessionController sessionController;

	@FXML
	public void initialize() {
		searchBox.textProperty().addListener((observable, oldValue, newValue) -> performSearch(newValue));
		searchBox.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler -> searchBox.setText(""));
	}

	private void performSearch(String q) {
		String query = q.toLowerCase();
		TableView<Session> sessionTable = sessionController.getSessionTable();

		sessionTable.getItems().stream().filter(item -> item.getTitle().toLowerCase().contains(query)).findAny()
				.ifPresent(item -> {
					sessionTable.getSelectionModel().select(item);
					sessionTable.scrollTo(item);
				});
	}

}