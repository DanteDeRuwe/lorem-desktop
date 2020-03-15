package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import main.domain.Session;
import main.services.GuiUtil;

public class SessionTabsController extends GuiController {

	@FXML private AnchorPane infoTab, announcementTab;

	private InfoTabController infoTabController;
	private AnnouncementTabController announcementTabController;

	@FXML
	public void initialize() {
		infoTabController = new InfoTabController();
		announcementTabController = new AnnouncementTabController();
		
		AnchorPane infoTabRoot = loadFXML("sessions/tabs/InfoTab.fxml", infoTabController, getFacade());
		AnchorPane announcementTabRoot = loadFXML("sessions/tabs/AnnouncementTab.fxml", announcementTabController, getFacade());
		
		GuiUtil.bindAnchorPane(infoTabRoot, infoTab);
		GuiUtil.bindAnchorPane(announcementTabRoot, announcementTab);
	}

	public void showSessionInfo(Session session) {
		infoTabController.setInspectedSession(session);
	}

}
