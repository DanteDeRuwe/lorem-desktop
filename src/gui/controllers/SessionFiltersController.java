package gui.controllers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.animation.FillTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.domain.Session;
import main.domain.facades.SessionCalendarFacade;
import main.services.GuiUtil;

public class SessionFiltersController extends GuiController {

	@FXML
	private Label academicYear;
	@FXML
	private JFXButton newSessionButton;
	@FXML
	private JFXTextField titleFilterField, speakerFilterField, locationFilterField;

	@FXML
	private JFXDatePicker startDateFilterField;
	@FXML
	private JFXTimePicker startTimeFilterField;

	@FXML
	public void initialize() {

		// 24hour view on the time picker
		GuiUtil.fixTimePicker(startTimeFilterField);

		// Change Label
		UpdateAcademicYear();

		// Event Listeners
		newSessionButton.setOnAction(e -> handleNewSession());

		// Filtering
		titleFilterField.textProperty().addListener((obs, oldText, newText) -> {
			((SessionSceneController) getParentController()).fillTableColumns(filter());
		});
		speakerFilterField.textProperty().addListener((obs, oldText, newText) -> {
			((SessionSceneController) getParentController()).fillTableColumns(filter());
		});
		locationFilterField.textProperty().addListener((obs, oldText, newText) -> {
			((SessionSceneController) getParentController()).fillTableColumns(filter());
		});
		// TODO: filter on start date and time
		startDateFilterField.setDisable(true);
		startTimeFilterField.setDisable(true);
		/*
		startDateFilterField.valueProperty().addListener((obs, oldText, newText) -> {
			((SessionSceneController) getParentController()).fillTableColumns(filter());
		});
		startTimeFilterField.valueProperty().addListener((obs, oldText, newText) -> {
			((SessionSceneController) getParentController()).fillTableColumns(filter());
		});
		*/
	}

	private Set<Session> filter() {
		String titleFilter = titleFilterField.getText();
		String speakerFilter = speakerFilterField.getText();
		String locationFilter = locationFilterField.getText();
		//LocalDateTime startDateTimeFilter = LocalDateTime.of(startDateFilterField.getValue(), startTimeFilterField.getValue());
		Set<Session> sessionSet = new HashSet<>(((SessionCalendarFacade) getFacade()).getAllSessions());
		return sessionSet.stream().filter(s -> s.getTitle().toLowerCase().contains((titleFilter.toLowerCase().trim())))
				.filter(s -> s.getSpeakerName().toLowerCase().contains((speakerFilter.toLowerCase().trim())))
				.filter(s -> s.getLocation().toLowerCase().contains((locationFilter.toLowerCase().trim())))
				/*
				.filter(s -> s.getStart().toLocalDate().format(Util.DATEFORMATTER)
						.equals(startDateTimeFilter.format(Util.DATEFORMATTER)))
				.filter(s -> s.getStart().toLocalTime().format(Util.TIMEFORMATTER)
						.equals(startDateTimeFilter.format(Util.TIMEFORMATTER)))
				*/
				.collect(Collectors.toSet());
	}

	public void UpdateAcademicYear() {
		academicYear
				.setText(((SessionCalendarFacade) getMainController().getSessionCalendarFacade()).getAcademicYear());

	}

	private void handleNewSession() {
		((SessionSceneController) getParentController()).displayOnRightPane("NewSession");
	}
}
