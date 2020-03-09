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

	private ObservableList<SessionCalendar> calendarList;

	@FXML private ListView<SessionCalendar> calendarListView;

	@FXML private Button selectButton, editButton;

	@FXML
	public void initialize() {

		fillList();

		// Event Listeners
		selectButton.setOnAction((event) -> handleSelectButton());
		editButton.setOnAction((event) -> handleEditButton());
	}

	/*
	 * Private Helpers
	 */

	private void fillList() {
		calendarList = FXCollections
				.observableArrayList(((SessionCalendarFacade) getFacade()).getAllSessionCalendars());
		calendarListView.setCellFactory(new PropertyValueFactoryWrapperCellFactory<>("academicYear"));
		calendarListView.setItems(calendarList);
	}

	private void handleSelectButton() {
		((SessionCalendarFacade) getFacade()).setCalendar(calendarListView.getSelectionModel().getSelectedItem());
		getMainController().getSessionSceneController().update();
		getMainController().switchToSessionTab();
	}

	private void handleEditButton() {
		// TODO: edit dialog using current selected session
	}

}
