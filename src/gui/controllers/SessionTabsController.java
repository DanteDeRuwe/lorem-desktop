package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import main.domain.Session;
import main.domain.facades.Facade;
import main.domain.facades.SessionFacade;
import main.services.GuiUtil;

public class SessionTabsController extends GuiController {

	@FXML
	private AnchorPane infoTab, announcementTab;

	private InfoTabController infoTabController;
	private AnnouncementTabController announcementTabController;
	private Facade sessionFacade;

	@FXML
	public void initialize() {
		infoTabController = new InfoTabController();
		announcementTabController = new AnnouncementTabController();

		// Info Tab
		AnchorPane infoTabRoot = loadFXML("sessions/tabs/InfoTab.fxml", infoTabController, getFacade());
		GuiUtil.bindAnchorPane(infoTabRoot, infoTab);

		// we will need a facade on sessionlevel (for announcements, media, feedback...)
		sessionFacade = new SessionFacade(getMainController().getLoggedInMemberManager());

		// Announcement Tab
		AnchorPane announcementTabRoot = loadFXML("sessions/tabs/AnnouncementTab.fxml", announcementTabController,
				sessionFacade);
		GuiUtil.bindAnchorPane(announcementTabRoot, announcementTab);
	}

	public void updateInspectedSession(Session session) {
		infoTabController.setInspectedSession(session);
		announcementTabController.setInspectedSession(session);
	}
}
