package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import main.domain.facades.Facade;
import main.domain.facades.SessionCalendarFacade;
import main.services.GuiUtil;

/*
 * Controller Hierarchy
 * - Main
 *  - SessionScene
 *      - SessionFilters 
 *      - SessionTabs
 *          - InfoTab
 *          - AnnouncementTab
 *          - FeedbackTab
 *      - NewSession
 *      - ModifySession
 *  - CalendarScene
 *  - UserScene
 *      - UserFilters 
 *       - UserDetails
 *      - NewUser
 *      - ModifyUser
 *  - AccountScene
 *  - StatsScene
 *  - AboutScene
 */

public class MainController extends GuiController {

	// Facades
	private Facade sessionCalendarFacade;

	// Controllers
	private GuiController sessionSceneController;

	// Nodes
	@FXML private AnchorPane root;
	@FXML private AnchorPane sessionTab, calendarTab, userTab, accountTab, statsTab, aboutTab;

	@FXML
	public void initialize() {

		// initialize facades
		sessionCalendarFacade = new SessionCalendarFacade();

		// initialize controllers
		sessionSceneController = new SessionSceneController();

		// Load the FXML
		// (this injects a parentcontroller and a facade into the controllers)
		AnchorPane sessionSceneRoot = loadFXML("sessions/SessionScene.fxml", sessionSceneController,
				sessionCalendarFacade);
		GuiUtil.bindAnchorPane(sessionSceneRoot, sessionTab);

	}

}
