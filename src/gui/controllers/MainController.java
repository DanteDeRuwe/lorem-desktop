package gui.controllers;

import com.jfoenix.controls.JFXTabPane;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
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
	private GuiController sessionSceneController, calendarSceneController, userSceneController;

	// Nodes
	@FXML private AnchorPane root;
	@FXML private AnchorPane sessionTab, calendarTab, userTab, accountTab, statsTab, aboutTab;

	@FXML private JFXTabPane navigationTabs;
	@FXML private Tab sessionNavigationTab;

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
		calendarSceneController = new CalendarSceneController();
		sessionSceneController = new SessionSceneController();
		userSceneController = new UserSceneController();

		// Load the FXML
		// (this injects a parentcontroller and a facade into the controllers)
		AnchorPane calendarSceneRoot = loadFXML("calendar/CalendarScene.fxml", calendarSceneController,
				sessionCalendarFacade);
		AnchorPane sessionSceneRoot = loadFXML("sessions/SessionScene.fxml", sessionSceneController,
				sessionCalendarFacade);
		AnchorPane userSceneRoot = loadFXML("users/UserScene.fxml", userSceneController,
				memberFacade);

		// Bind the panes to the tabs
		GuiUtil.bindAnchorPane(sessionSceneRoot, sessionTab);
		GuiUtil.bindAnchorPane(calendarSceneRoot, calendarTab);
		GuiUtil.bindAnchorPane(userSceneRoot, userTab);

		// Set all tabs disabled in the beginning
		setSessionTabEnabled(false);

	}

	public void setSessionTabEnabled(boolean b) {
		navigationTabs.getTabs().get(1).setDisable(!b);
	}

	public void switchToSessionTab() {
		navigationTabs.getSelectionModel().select(sessionNavigationTab);
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

	public SessionSceneController getSessionSceneController() {
		return (SessionSceneController) sessionSceneController;
	}

}
