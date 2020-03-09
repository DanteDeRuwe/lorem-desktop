package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import main.domain.facades.Facade;
import main.domain.facades.MemberFacade;
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
	private Facade sessionCalendarFacade, memberFacade;

	// Controllers
	private GuiController sessionSceneController, calendarSceneController;

	// Nodes
	@FXML private AnchorPane root;
	@FXML private AnchorPane sessionTab, calendarTab, userTab, accountTab, statsTab, aboutTab;

	/*
	 * INIT
	 */

	@FXML
	public void initialize() {

		// Set the main controller of the main controller to the main controller :O
		this.injectMainController(this);

		// initialize facades
		sessionCalendarFacade = new SessionCalendarFacade();
		memberFacade = new MemberFacade();

		// initialize controllers
		sessionSceneController = new SessionSceneController();
		calendarSceneController = new CalendarSceneController();

		// Load the FXML
		// (this injects a parentcontroller and a facade into the controllers)
		AnchorPane sessionSceneRoot = loadFXML("sessions/SessionScene.fxml", sessionSceneController,
				sessionCalendarFacade);

		AnchorPane calendarSceneRoot = loadFXML("calendar/CalendarScene.fxml", calendarSceneController,
				sessionCalendarFacade);

		// Bind the panes to the tabs
		GuiUtil.bindAnchorPane(sessionSceneRoot, sessionTab);
		GuiUtil.bindAnchorPane(calendarSceneRoot, calendarTab);

	}

	/*
	 * Getters and setters
	 */

	public Facade getSessionCalendarFacade() {
		return sessionCalendarFacade;
	}

	public void setSessionCalendarFacade(Facade sessionCalendarFacade) {
		this.sessionCalendarFacade = sessionCalendarFacade;
	}

	public Facade getMemberFacade() {
		return memberFacade;
	}

	public void setMemberFacade(Facade memberFacade) {
		this.memberFacade = memberFacade;
	}

}
