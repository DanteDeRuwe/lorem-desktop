package gui.controllers;

import com.jfoenix.controls.JFXTabPane;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.domain.facades.Facade;
import main.domain.facades.LoggedInMemberManager;
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
	private GuiController sessionSceneController, calendarSceneController, userSceneController, accountSceneController;

	private LoggedInMemberManager loggedInMemberManager;

	// Nodes
	@FXML
	private AnchorPane root;
	@FXML
	private AnchorPane sessionTab, calendarTab, userTab, accountTab, statsTab, aboutTab;

	@FXML
	private JFXTabPane navigationTabs;
	@FXML
	private Tab sessionNavigationTab;

	/*
	 * INIT
	 */

	@FXML
	public void initialize() {

		// Set the main controller of the main controller to the main controller :O
		this.injectMainController(this);

		// initialize facades
		loggedInMemberManager = LoggedInMemberManager.getInstance();
		sessionCalendarFacade = new SessionCalendarFacade();
		memberFacade = new MemberFacade();

		// initialize controllers
		calendarSceneController = new CalendarSceneController();
		userSceneController = new UserSceneController();
		accountSceneController = new AccountSceneController();

		// Load the calendar pane
		AnchorPane calendarSceneRoot = loadFXML("calendar/CalendarScene.fxml", calendarSceneController,
				sessionCalendarFacade);

		// load the user pane
		AnchorPane userSceneRoot = loadFXML("users/UserScene.fxml", userSceneController, memberFacade);

		// load my account pane
		AnchorPane accountSceneRoot = loadFXML("users/MyAccount.fxml", accountSceneController, memberFacade);

		// Bind the panes to the tabs
		GuiUtil.bindAnchorPane(calendarSceneRoot, calendarTab);
		GuiUtil.bindAnchorPane(userSceneRoot, userTab);
		GuiUtil.bindAnchorPane(accountSceneRoot, accountTab);

		// Set sessiontab disabled in the beginning
		setSessionTabEnabled(false);

	}

	public void setSessionTabEnabled(boolean enable) {
		if (enable) {
			// Load everything for the session
			sessionSceneController = new SessionSceneController();
			AnchorPane sessionSceneRoot = loadFXML("sessions/SessionScene.fxml", sessionSceneController,
					sessionCalendarFacade);
			GuiUtil.bindAnchorPane(sessionSceneRoot, sessionTab);
		}

		navigationTabs.getTabs().get(1).setDisable(!enable);
	}

	public void switchToSessionTab() {
		navigationTabs.getSelectionModel().select(sessionNavigationTab);
	}

	public void closeMainWindow() {
		((Stage) root.getScene().getWindow()).close();
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

	public UserSceneController getUserSceneController() {
		return (UserSceneController) userSceneController;
	}

	public CalendarSceneController getCalendarSceneController() {
		return (CalendarSceneController) calendarSceneController;
	}
	
	public AccountSceneController getAccountSceneController() {
		return (AccountSceneController) accountSceneController;
	}

	public LoggedInMemberManager getLoggedInMemberManager() {
		return loggedInMemberManager;
	}

}
