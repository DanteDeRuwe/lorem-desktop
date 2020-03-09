package gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SessionFiltersController extends GuiController {

	@FXML private Label academicYear;
	@FXML private JFXButton newSessionButton;
	@FXML private JFXTextField titleFilter, speakerFilter, locationFilter;

	@FXML private JFXDatePicker startdateFilter;
	@FXML private JFXTimePicker startTimefilter;

	@FXML
	public void initialize() {

		// Event Listeners
		newSessionButton.setOnAction(e -> handleNewSession());

		// TODO filters
		// titleFilter.textProperty().addListener((obs, oldText, newText) -> { });

	}

	private void handleNewSession() {
		((SessionSceneController) getParentController()).displayOnRightPane("NewSession");
	}
}
