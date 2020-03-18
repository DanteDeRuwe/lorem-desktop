package gui.controllers;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import main.domain.SessionCalendar;
import main.domain.facades.SessionCalendarFacade;
import main.services.GuiUtil;
import main.services.PropertyValueFactoryWrapperCellFactory;

public class CalendarSceneController extends GuiController {

	@FXML
	private AnchorPane chooseCalendarPane, calendarSceneRoot;
	@FXML
	private ListView<SessionCalendar> calendarListView;
	@FXML
	private Button selectButton, editButton, addButton;

	private NewCalendarController newCalendarController;
	private ObservableList<SessionCalendar> calendarList;
	private AnchorPane editCalendar, newCalendar;

	@FXML
	public void initialize() {
		// Placeholder text when calendarListView is empty
		GuiUtil.setListPlaceholderText(
				calendarListView, "Het is hier nogal leeg...\nProbeer eens een kalender toe te voegen!"
		);

		// initialize controllers
		newCalendarController = new NewCalendarController();

		// load FXML once
		newCalendar = loadFXML("calendar/EditOrCreateCalendar.fxml", newCalendarController, this.getFacade());

		// fill and display the calendar list + get first item by default
		fillList();
		displayCalendarList();
		calendarListView.getSelectionModel().selectFirst();

		// Buttons
		selectButton.setOnAction((event) -> onCalendarSelect());
		editButton.setOnAction((event) -> onCalendarEdit());
		addButton.setOnAction((event) -> onCalendarAdd());
	}

	public void update() {
		fillList();
	}

	public void displayCalendarList() {
		GuiUtil.bindAnchorPane(chooseCalendarPane, calendarSceneRoot);
	}

	private void fillList() {

		// make a list
		calendarList = FXCollections
				.observableArrayList(((SessionCalendarFacade) getFacade()).getAllSessionCalendars());

		// sort by start date
		calendarList.sort(Comparator.comparing(SessionCalendar::getStartDate));

		// Set the listview
		calendarListView.setCellFactory(
				new PropertyValueFactoryWrapperCellFactory<SessionCalendar>("academicYear", this::onCalendarSelect)
		);

		calendarListView.setItems(calendarList);
		calendarListView.refresh();
	}

	private void onCalendarSelect() {

		// Set the calendar
		((SessionCalendarFacade) getFacade()).setCalendar(getInspectedCalendar());

		// Set session tabs enabled, update it, and switch to it
		getMainController().setSessionTabEnabled(true);
		getMainController().getSessionSceneController().update();
		getMainController().switchToSessionTab();
	}

	private void onCalendarEdit() {
		if (getInspectedCalendar().getSessions().size() > 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Wijzig kalender");
			alert.setHeaderText("Fout");
			alert.setContentText("Je kan geen kalender met toegevoegde sessies wijzigen!");
			alert.showAndWait();
		} else {
			calendarSceneRoot.getChildren().clear();
			editCalendar = loadFXML("calendar/EditOrCreateCalendar.fxml", new ModifyCalendarController(), getFacade());
			GuiUtil.bindAnchorPane(editCalendar, calendarSceneRoot);
		}
	}

	private void onCalendarAdd() {
		GuiUtil.bindAnchorPane(newCalendar, calendarSceneRoot);
	}

	public SessionCalendar getInspectedCalendar() {
		return calendarListView.getSelectionModel().getSelectedItem();
	}

}
