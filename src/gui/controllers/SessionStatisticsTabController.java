package gui.controllers;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import main.domain.Member;
import main.domain.Session;
import main.services.PropertyValueFactoryWrapperCellFactory;

public class SessionStatisticsTabController extends GuiController {

	private Session inspectedSession;
	private SessionSceneController ssc;

	@FXML
	private Label totalAttendees, totalRegistrees;

	@FXML
	private ListView<Member> attendeesListView;

	@FXML
	private Button exportButton;

	private ObservableList<Member> attendeesList;

	@FXML
	public void initialize() {
		ssc = getMainController().getSessionSceneController();
		inspectedSession = ssc.getInspectedSession();
	}

	public void update() {
		updateLabels();
		fillList();
	}

	private void fillList() {
		// make a list
		attendeesList = FXCollections.observableArrayList(inspectedSession.getAttendees());

		// sort by start date
		attendeesList.sort(Comparator.comparing(Member::getUsername));

		// Set the listview
		attendeesListView.setCellFactory(new PropertyValueFactoryWrapperCellFactory<Member>("username"));

		attendeesListView.setItems(attendeesList);
		attendeesListView.refresh();
	}

	private void updateLabels() {
		totalAttendees.setText(String.valueOf(inspectedSession.countAttendees()));
		totalRegistrees.setText(String.valueOf(inspectedSession.countRegistrees()));
	}

	public Session getInspectedSession() {
		return inspectedSession;
	}

	public void setInspectedSession(Session inspectedSession) {
		if (inspectedSession == null)
			return;
		this.inspectedSession = inspectedSession;
		update();

	}
}
