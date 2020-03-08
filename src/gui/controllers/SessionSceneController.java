package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import main.services.GuiUtil;

public class SessionSceneController extends GuiController {

	// Controllers

	@FXML
	private SessionFiltersController sessionFiltersController;

	private SessionTabsController sessionTabsController;
	private NewSessionController newSessionController;

	/*
	 * @FXML private InfoTabController infoTabController;
	 * 
	 * @FXML private AnnouncementTabController announcementTabController;
	 * 
	 * @FXML private FeedbackTabController feedbackTabController;
	 */

	// Elements
	@FXML
	private AnchorPane leftPane;
	@FXML
	private AnchorPane middlePane;
	@FXML
	private AnchorPane rightPane;

	private AnchorPane newSession;
	private AnchorPane sessionTabs;

	@FXML
	public void initialize() {

		// Initialize swappable controllers
		sessionTabsController = new SessionTabsController();
		newSessionController = new NewSessionController();

		// load FXML once, this also sets parentcontrollers and facades
		newSession = loadFXML("sessions/NewSession.fxml", newSessionController, this.getFacade());
		sessionTabs = loadFXML("sessions/SessionTabs.fxml", sessionTabsController, this.getFacade());
		GuiUtil.setAnchorsZero(sessionTabs);
		GuiUtil.setAnchorsZero(newSession);

		// Default, right panel is for tabs
		displayOnRightPane("SessionTabs");

		// Inject parentcontroller and facade in the predefined controllers
		sessionFiltersController.injectParentController(this).injectFacade(this.getFacade());
	}

	public void displayOnRightPane(String key) {
		AnchorPane pane;

		if (key.equals("SessionTabs")) {
			pane = sessionTabs;
		} else if (key.equals("NewSession")) {
			pane = newSession;
		} else {
			throw new RuntimeException("key not valid");
		}

		rightPane.getChildren().clear();
		rightPane.getChildren().add(pane);
	}

}
