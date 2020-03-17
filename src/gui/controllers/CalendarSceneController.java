package gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import main.domain.SessionCalendar;
import main.domain.facades.SessionCalendarFacade;
import main.services.GuiUtil;
import main.services.PropertyValueFactoryWrapperCellFactory;

public class CalendarSceneController extends GuiController {

	private ObservableList<SessionCalendar> calendarList;
	private SessionCalendar inspectedCalendar;

	@FXML
	private AnchorPane chooseCalendarPane, modifyCalendarRoot, calendarSceneRoot;

	@FXML
	private ListView<SessionCalendar> calendarListView;
	@FXML
	private Button selectButton, editButton, addButton;

	@FXML
	public void initialize() {
		fillList();

		calendarListView.getSelectionModel().selectFirst();
		inspectedCalendar = calendarListView.getSelectionModel().getSelectedItem();
		modifyCalendarRoot = loadFXML("calendar/ModifyCalendar.fxml", new ModifyCalendarController(), getFacade());

		// Event Listeners
		calendarListView.getSelectionModel().selectedItemProperty()
				.addListener((x, y, sessionCalendar) -> inspectedCalendar = sessionCalendar);
		selectButton.setOnAction((event) -> onCalendarSelect());
		editButton.setOnAction((event) -> onCalendarEdit());
		addButton.setOnAction((event) -> onCalendarAdd());
	}

	private void fillList() {
		calendarList = FXCollections
				.observableArrayList(((SessionCalendarFacade) getFacade()).getAllSessionCalendars());

		calendarListView.setCellFactory(
				new PropertyValueFactoryWrapperCellFactory<SessionCalendar>("academicYear", this::onCalendarSelect));

		calendarListView.setItems(calendarList);
		calendarListView.refresh();
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
		GuiUtil.bindAnchorPane(modifyCalendarRoot, calendarSceneRoot);
	}

	private void onCalendarAdd() {

	}

	public SessionCalendar getInspectedCalendar() {
		return inspectedCalendar;
	}
	
	public void goBack() {
		fillList();
		GuiUtil.bindAnchorPane(chooseCalendarPane, calendarSceneRoot);
	}

}
