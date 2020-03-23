package gui.controllers;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import main.domain.Announcement;
import main.domain.Session;
import main.services.AnnouncementCellFactory;

public class AnnouncementTabController extends GuiController {

	private Session inspectedSession;

	@FXML
	private Button addAnnouncementButton, modifyAnnouncementButton, deleteAnnouncementButton;
	@FXML
	private ListView<Announcement> announcementListView;

	@FXML
	public void initialize() {
		announcementListView.setCellFactory(new AnnouncementCellFactory<Announcement>());
		fillList();
	}

	public void fillList() {
		if (inspectedSession == null)
			return;
		List<Announcement> announcements = inspectedSession.getAnnouncements();
		announcementListView.setItems(FXCollections.observableArrayList(announcements));
	}

	public void setInspectedSession(Session session) {
		inspectedSession = session;
		fillList();
	}

}
