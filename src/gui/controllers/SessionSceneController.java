package gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import main.domain.Session;
import main.domain.facades.SessionCalendarFacade;
import main.services.GuiUtil;

public class SessionSceneController extends GuiController {

	// Controllers
	private GuiController sessionFiltersController, sessionTabsController, newSessionController,
			modifySessionController, infoTabController, announcementTabController, feedbackTabController;

	// Own vars
	private AnchorPane sessionFilters, sessionTabs, newSession;
	private ObservableList<Session> sessionList;

	// FXML vars
	@FXML private AnchorPane leftPane, middlePane, rightPane;

	@FXML public TableView<Session> sessionTable; // public since child controllers will need access to it

	@FXML private TableColumn<Session, String> titleColumn, startColumn, durationColumn, organizerColumn, speakerColumn,
			locationColumn, capacityColumn;

	/*
	 * -----------------------------------------------------------------------------
	 * Init
	 */

	@FXML
	public void initialize() {

		// Initialize controllers
		sessionFiltersController = new SessionFiltersController();
		sessionTabsController = new SessionTabsController();
		newSessionController = new NewSessionController();

		// load FXML once, this also sets parentcontrollers and facades
		sessionFilters = loadFXML("sessions/SessionFilters.fxml", sessionFiltersController, this.getFacade());
		newSession = loadFXML("sessions/NewSession.fxml", newSessionController, this.getFacade());
		sessionTabs = loadFXML("sessions/SessionTabs.fxml", sessionTabsController, this.getFacade());

		// Left Panel
		GuiUtil.bindAnchorPane(sessionFilters, leftPane);

		// Center Panel
		fillTableColumns();

		// Right panel: default for tabs
		displayOnRightPane("SessionTabs");

		/*
		 * Set event handlers
		 */
		sessionTable.setOnMouseClicked(e -> showSessionInfo());
	}

	/*
	 * -----------------------------------------------------------------------------
	 * Private helpers
	 */

	private void showSessionInfo() {
		Session selectedsession = sessionTable.getSelectionModel().getSelectedItem();

		// TODO update infotab
	}

	private void fillTableColumns() {
		sessionList = FXCollections.observableArrayList(((SessionCalendarFacade) getFacade()).getAllSessions());

		GuiUtil.fillColumn(titleColumn, "title", 200, 400);
		GuiUtil.fillColumn(startColumn, "start", 120, 120);
		GuiUtil.fillColumn(durationColumn, "duration", 45, 45);
		GuiUtil.fillColumn(organizerColumn, "organizer", 100, 200);
		GuiUtil.fillColumn(speakerColumn, "speaker", 100, 200);
		GuiUtil.fillColumn(locationColumn, "location", 100, 200);
		GuiUtil.fillColumn(capacityColumn, "capacity", 100, 200);

		sessionTable.setItems(sessionList);
	}

	public void displayOnRightPane(String key) {
		AnchorPane pane;

		if (key.equals("SessionTabs")) {
			GuiUtil.bindAnchorPane(sessionTabs, rightPane);
		} else if (key.equals("NewSession")) {
			GuiUtil.bindAnchorPane(newSession, rightPane);
		} else {
			throw new RuntimeException("key not valid");
		}

	}

}
