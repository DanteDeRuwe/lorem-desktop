package gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SessionFiltersController extends GuiController {

	@FXML
	private Label academicYear;
	@FXML
	private JFXButton newSessionButton;
	@FXML
	private JFXTextField titleFilter, speakerFilter, locationFilter;

	@FXML
	private JFXDatePicker startdateFilter;
	@FXML
	private JFXTimePicker startTimefilter;

	@FXML
	public void initialize() {

		newSessionButton.setOnAction((e) -> {
			// open up the newsession view on the right
			((SessionSceneController) getParentController()).displayOnRightPane("NewSession");
		});
	}
}
