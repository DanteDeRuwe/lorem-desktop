package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import main.domain.facades.Facade;
import main.domain.facades.MemberFacade;
import main.domain.facades.SessionCalendarFacade;

public class MainController extends GuiContoller {

	private Facade sessionFacade, memberFacade, sessionCalendarFacade;

	// Controllers

	// Nodes
	@FXML
	private AnchorPane main;

	@FXML
	private AnchorPane leftPane, centerPane, rightPane;

	@FXML
	public void initialize() {

		// initialize facades
		sessionCalendarFacade = new SessionCalendarFacade();
		memberFacade = new MemberFacade();

	}

}
