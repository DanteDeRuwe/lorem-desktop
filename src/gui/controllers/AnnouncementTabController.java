package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import main.domain.Session;

public class AnnouncementTabController extends GuiController {

	@FXML
	private Button addAnnouncementButton, modifyAnnouncementButton, deleteAnnouncementButton;
	@FXML
	private ListView announcementListView;

	private Session inspectedSession;

	@FXML
	public void initialize() {
	}

	public void setInspectedSession(Session session) {
		inspectedSession = session;
		// change listview source
	}

}
