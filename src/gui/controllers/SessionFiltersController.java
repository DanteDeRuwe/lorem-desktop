package gui.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.domain.MemberStatus;
import main.domain.Session;
import main.domain.SessionStatus;
import main.domain.facades.SessionCalendarFacade;
import main.services.GuiUtil;

public class SessionFiltersController extends GuiController {

	@FXML
	private Label academicYear;
	@FXML
	private JFXButton newSessionButton;
	@FXML
	private JFXTextField titleFilterField, speakerFilterField, locationFilterField, organizerFilterField, typeFilterField;

	@FXML
	private JFXDatePicker fromFilterField;
	@FXML
	private JFXDatePicker toFilterField;
	
	@FXML
    private JFXComboBox<String> statusFilterBox;
	
	private final Map<String, SessionStatus> statusMap = new LinkedHashMap<>(); // linkedhashmap because their iteration order does not change

	@FXML
	public void initialize() {

		// Date picker formats
		GuiUtil.fixDatePicker(fromFilterField);
		GuiUtil.fixDatePicker(toFilterField);

		// Change Label
		UpdateAcademicYear();
		
		// fill map
		Stream.of(SessionStatus.values()).forEach(ss -> statusMap.put(ss.toString(), ss));
		
		// populate combo box
		statusFilterBox.getItems().add("Alle");
		statusFilterBox.getItems().addAll(statusMap.keySet());
		statusFilterBox.getSelectionModel().select(SessionStatus.CREATED.toString());
		

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
		typeFilterField.textProperty().addListener(
				(obs, oldText, newText) -> ((SessionSceneController) getParentController()).fillTableColumns(filter()));
		fromFilterField.valueProperty().addListener(
				(obs, oldText, newText) -> ((SessionSceneController) getParentController()).fillTableColumns(filter()));
		toFilterField.valueProperty().addListener(
				(obs, oldText, newText) -> ((SessionSceneController) getParentController()).fillTableColumns(filter()));
		statusFilterBox.valueProperty().addListener(
				(obv, oldValue, newValue) -> ((SessionSceneController) getParentController()).fillTableColumns(filter()));
	}

	private Set<Session> filter() {
		String titleFilter = titleFilterField.getText();
		String speakerFilter = speakerFilterField.getText();
		String locationFilter = locationFilterField.getText();
		String organizerFilter = organizerFilterField.getText();
		String typeFilter = typeFilterField.getText();
		LocalDate fromFilter = fromFilterField.getValue();
		LocalDate toFilter = toFilterField.getValue();
		String statusFilter = statusFilterBox.getValue();
		Set<Session> sessionSet = new HashSet<>(((SessionCalendarFacade) getFacade()).getAllSessions());
		return sessionSet.stream().filter(s -> s.getTitle().toLowerCase().contains((titleFilter.toLowerCase().trim())))
				.filter(s -> s.getSpeakerName().toLowerCase().contains((speakerFilter.toLowerCase().trim())))
				.filter(s -> s.getLocation().toLowerCase().contains((locationFilter.toLowerCase().trim())))
				.filter(s -> s.getType().toLowerCase().contains((typeFilter.toLowerCase().trim())))
				.filter(s -> s.getFullOrganizerName().toLowerCase().contains((organizerFilter.toLowerCase().trim())))
				.filter(s -> (fromFilter == null && toFilter == null) ? true
						: (toFilter == null) ? s.getStart().toLocalDate().compareTo(fromFilter) >= 0
								: (fromFilter == null) ? s.getStart().toLocalDate().compareTo(toFilter) <= 0
										: s.getStart().toLocalDate().compareTo(fromFilter) >= 0
												&& s.getStart().toLocalDate().compareTo(toFilter) <= 0

				).filter(s -> statusFilter.equals("Alle") ? true
						: s.getSessionStatus() == null ? false : s.getSessionStatus().equals(statusMap.get(statusFilter)))
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
