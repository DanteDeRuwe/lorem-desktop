package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import main.domain.Session;
import main.services.GuiUtil;

public class SessionTabsController extends GuiController {

	@FXML private AnchorPane infoTab, announcementTab;

	private InfoTabController infoTabController;
	private AnnouncementTabController announcementTabController;

	private Session inspectedSession;

	@FXML
	public void initialize() {
		infoTabController = new InfoTabController();
		announcementTabController = new AnnouncementTabController();
		
		AnchorPane infoTabRoot = loadFXML("sessions/tabs/InfoTab.fxml", infoTabController, getFacade());
		AnchorPane announcementTabRoot = loadFXML("sessions/tabs/AnnouncementTab.fxml", announcementTabController, getFacade());
		
		GuiUtil.bindAnchorPane(infoTabRoot, infoTab);
		GuiUtil.bindAnchorPane(announcementTabRoot, announcementTab);
	}
	
	public void setInspectedSession(Session session) {
		inspectedSession = session;
		//update tabs
		infoTabController.setInspectedSession(session);
		announcementTabController.setInspectedSession(session);
	}
	
	public Session getInspectedSession(Session session) {
		return inspectedSession;
	}

}
