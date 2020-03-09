package gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import main.domain.SessionCalendar;
import main.domain.facades.SessionCalendarFacade;
import main.services.PropertyValueFactoryWrapperCellFactory;

public class CalendarSceneController extends GuiController {

	// TODO: temporary facade tot getFacade werkt
	private SessionCalendarFacade tempFacade = new SessionCalendarFacade();
	private ObservableList<SessionCalendar> calendarList;

	@FXML
	private ListView<SessionCalendar> calendarListView;

	@FXML
	private Button selectButton, editButton;

	@FXML
	public void initialize() {
		fillList();
		addButtonHandlers();
	}

	private void fillList() {
		calendarList = FXCollections.observableArrayList(tempFacade.getAllSessionCalendars());
		calendarListView.setCellFactory(new PropertyValueFactoryWrapperCellFactory<>("academicYear"));
		calendarListView.setItems(calendarList);
	}

	private void addButtonHandlers() {
		selectButton.setOnAction((event) -> handleSelectButton());
		editButton.setOnAction((event) -> handleEditButton());
	}

	private void handleSelectButton() {
		tempFacade.setCalendar(calendarListView.getSelectionModel().getSelectedItem());
		//TODO: the sessions tab list has to be updated after changing calendars
		//TODO: switch to sessions tab for UX bonus points
	}

	private void handleEditButton() {
		// TODO: edit dialog using current selected session
	}

}
