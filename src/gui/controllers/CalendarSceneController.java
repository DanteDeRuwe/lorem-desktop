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
		selectButton.setOnAction((event) -> onCalendarSelect());
		editButton.setOnAction((event) -> onCalendarEdit());
	}

	/*
	 * Private Helpers
	 */

	private void fillList() {
		calendarList = FXCollections
				.observableArrayList(((SessionCalendarFacade) getFacade()).getAllSessionCalendars());

		calendarListView.setCellFactory(
				new PropertyValueFactoryWrapperCellFactory<SessionCalendar>("academicYear", this::onCalendarSelect)
		);

		calendarListView.setItems(calendarList);
	}

	private void onCalendarSelect() {

		// Set the calendar
		((SessionCalendarFacade) getFacade()).setCalendar(calendarListView.getSelectionModel().getSelectedItem());

		// Update the session scene
		getMainController().getSessionSceneController().update();

		// Set session tabs enabled and switch to it
		getMainController().setSessionTabEnabled(true);
		getMainController().switchToSessionTab();
	}

	private void onCalendarEdit() {
		// TODO: edit dialog using current selected session
	}

}
