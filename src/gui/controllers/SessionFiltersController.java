package gui.controllers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

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
	private JFXTextField titleFilterField, speakerFilterField, locationFilterField, organizerFilterField;

	@FXML
	private JFXDatePicker fromFilterField;
	@FXML
	private JFXDatePicker toFilterField;

	@FXML
	public void initialize() {

		// Date picker formats
		GuiUtil.fixDatePicker(fromFilterField);
		GuiUtil.fixDatePicker(toFilterField);

		// Change Label
		UpdateAcademicYear();

		// Event Listeners
		newSessionButton.setOnAction(e -> handleNewSession());

		// Filtering
		titleFilterField.textProperty().addListener(
				(obs, oldText, newText) -> ((SessionSceneController) getParentController()).fillTableColumns(filter()));
		speakerFilterField.textProperty().addListener(
				(obs, oldText, newText) -> ((SessionSceneController) getParentController()).fillTableColumns(filter()));
		locationFilterField.textProperty().addListener(
				(obs, oldText, newText) -> ((SessionSceneController) getParentController()).fillTableColumns(filter()));
		organizerFilterField.textProperty().addListener(
				(obs, oldText, newText) -> ((SessionSceneController) getParentController()).fillTableColumns(filter()));
		fromFilterField.valueProperty().addListener(
				(obs, oldText, newText) -> ((SessionSceneController) getParentController()).fillTableColumns(filter()));
		toFilterField.valueProperty().addListener(
				(obs, oldText, newText) -> ((SessionSceneController) getParentController()).fillTableColumns(filter()));
	}

	private Set<Session> filter() {
		String titleFilter = titleFilterField.getText();
		String speakerFilter = speakerFilterField.getText();
		String locationFilter = locationFilterField.getText();
		String organizerFilter = organizerFilterField.getText();
		LocalDate fromFilter = fromFilterField.getValue();
		LocalDate toFilter = toFilterField.getValue();
		Set<Session> sessionSet = new HashSet<>(((SessionCalendarFacade) getFacade()).getAllSessions());
		return sessionSet.stream().filter(s -> s.getTitle().toLowerCase().contains((titleFilter.toLowerCase().trim())))
				.filter(s -> s.getSpeakerName().toLowerCase().contains((speakerFilter.toLowerCase().trim())))
				.filter(s -> s.getLocation().toLowerCase().contains((locationFilter.toLowerCase().trim())))
				.filter(s -> s.getFullOrganizerName().toLowerCase().contains((organizerFilter.toLowerCase().trim())))
				.filter(s -> (fromFilter == null && toFilter == null) ? true
						: (toFilter == null) ? s.getStart().toLocalDate().compareTo(fromFilter) >= 0
								: (fromFilter == null) ? s.getStart().toLocalDate().compareTo(toFilter) <= 0
										: s.getStart().toLocalDate().compareTo(fromFilter) >= 0
												&& s.getStart().toLocalDate().compareTo(toFilter) <= 0

				).collect(Collectors.toSet());
	}

	public void UpdateAcademicYear() {
		academicYear
				.setText(((SessionCalendarFacade) getMainController().getSessionCalendarFacade()).getAcademicYear());

	}

	private void handleNewSession() {
		((SessionSceneController) getParentController()).displayOnRightPane("NewSession");
	}
}
