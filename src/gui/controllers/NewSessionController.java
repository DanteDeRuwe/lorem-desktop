package gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.fxml.FXML;

public class NewSessionController extends GuiController {

	@FXML
	private JFXTextField titleField, speakerField, durationField, locationField, capacityField;

	@FXML
	private JFXDatePicker startDateField;

	@FXML
	private JFXTimePicker startTimeField;

	@FXML
	private JFXButton confirmButton, cancelButton;

	@FXML
	public void initialize() {

		// Cancel
		cancelButton.setOnAction((e) -> {
			((SessionSceneController) getParentController()).displayOnRightPane("SessionTabs");
		});

		// Add
		confirmButton.setOnAction((e) -> {
			// Get form values
			// TODO

			// create a new session via facade
			// TODO

			// In the end, go back to the details view
			((SessionSceneController) getParentController()).displayOnRightPane("SessionTabs");
		});

	}
}
