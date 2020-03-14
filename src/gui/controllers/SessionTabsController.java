package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import main.domain.Session;
import main.services.GuiUtil;

public class SessionTabsController extends GuiController {

	@FXML private AnchorPane infoTab;

	private InfoTabController infoTabController;

	@FXML
	public void initialize() {
		infoTabController = new InfoTabController();

		AnchorPane infoTabRoot = loadFXML("sessions/tabs/InfoTab.fxml", infoTabController, getFacade());

		GuiUtil.bindAnchorPane(infoTabRoot, infoTab);
	}

	public void showSessionInfo(Session session) {
		infoTabController.setInspectedSession(session);
	}

}
