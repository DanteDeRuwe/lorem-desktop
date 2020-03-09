package gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.domain.facades.SessionCalendarFacade;

public class SessionFiltersController extends GuiController {

	@FXML private Label academicYear;
	@FXML private JFXButton newSessionButton;
	@FXML private JFXTextField titleFilter, speakerFilter, locationFilter;

	@FXML private JFXDatePicker startdateFilter;
	@FXML private JFXTimePicker startTimefilter;

	@FXML
	public void initialize() {

		// Change Label
		UpdateAcademicYear();

		// Event Listeners
		newSessionButton.setOnAction(e -> handleNewSession());

		// TODO filters
		// titleFilter.textProperty().addListener((obs, oldText, newText) -> { });

	}

	public void UpdateAcademicYear() {
		academicYear
				.setText(((SessionCalendarFacade) getMainController().getSessionCalendarFacade()).getAcademicYear());

	}

	private void handleNewSession() {
		((SessionSceneController) getParentController()).displayOnRightPane("NewSession");
	}
}
