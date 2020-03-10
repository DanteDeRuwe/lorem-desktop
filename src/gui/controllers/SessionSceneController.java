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
			modifySessionController, announcementTabController, feedbackTabController;

	// Own vars
	private AnchorPane sessionFilters, sessionTabs, newSession;
	private ObservableList<Session> sessionList;

	// FXML vars
	@FXML private AnchorPane leftPane, middlePane, rightPane;
	@FXML protected TableView<Session> sessionTable;
	@FXML private TableColumn<Session, String> titleColumn, startColumn, durationColumn, organizerColumn, speakerColumn,
			locationColumn, capacityColumn;

	/*
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

		// Event Handlers
		sessionTable.setOnMouseClicked(e -> showSessionInfo());

	}

	/*
	 * Helpers
	 */

	private void showSessionInfo() {
		// ((InfoTabController)
		// infoTabController).setInspectedSession(sessionTable.getSelectionModel().getSelectedItem());
		((SessionTabsController) sessionTabsController)
				.showSessionInfo(sessionTable.getSelectionModel().getSelectedItem());
	}

	void fillTableColumns() {
		sessionList = FXCollections.observableArrayList(((SessionCalendarFacade) getFacade()).getAllSessions());

		GuiUtil.fillColumn(titleColumn, "title", 40, 500);
		GuiUtil.fillColumn(startColumn, "start", 40, 150);
		GuiUtil.fillColumn(durationColumn, "duration", 60, 60);
		GuiUtil.fillColumn(organizerColumn, "organizer", 40, 300);
		GuiUtil.fillColumn(speakerColumn, "speaker", 40, 200);
		GuiUtil.fillColumn(locationColumn, "location", 40, 200);
		GuiUtil.fillColumn(capacityColumn, "capacity", 40, 60);

		sessionTable.setItems(sessionList);

	}

	void displayOnRightPane(String key) {
		if (key.equals("SessionTabs"))
			GuiUtil.bindAnchorPane(sessionTabs, rightPane);
		else if (key.equals("NewSession"))
			GuiUtil.bindAnchorPane(newSession, rightPane);
		else
			throw new RuntimeException("key not valid");
	}

	void update() {
		// gets called when selecting new calendar

		fillTableColumns();
		((SessionFiltersController) sessionFiltersController).UpdateAcademicYear();
	}

}
