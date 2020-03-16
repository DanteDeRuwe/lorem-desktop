package gui.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

import javafx.collections.FXCollections;
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

	// FXML vars
	@FXML
	private AnchorPane leftPane, middlePane, rightPane;
	@FXML
	protected TableView<Session> sessionTable;
	@FXML
	private TableColumn<Session, String> titleColumn, organizerColumn, speakerColumn, locationColumn, capacityColumn;
	@FXML
	private TableColumn<Session, LocalDateTime> startColumn;
	@FXML
	private TableColumn<Session, Duration> durationColumn;

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
		fillTableColumns(((SessionCalendarFacade) getFacade()).getAllSessions());

		// Right panel: default for tabs
		displayOnRightPane("SessionTabs");

		// Event Handlers
		sessionTable.getSelectionModel().selectedItemProperty().addListener(
				(x, y, session) -> ((SessionTabsController) sessionTabsController).setInspectedSession(session));

	}

	/*
	 * Helpers
	 */

	void fillTableColumns(Set<Session> sessions) {
		GuiUtil.fillColumn(titleColumn, "title", 40, 500);
		GuiUtil.fillColumnWithDateTime(startColumn, "start", 40, 150);
		GuiUtil.fillColumnWithDuration(durationColumn, "duration", 60, 100);
		GuiUtil.fillColumn(organizerColumn, "organizer", 40, 300);
		GuiUtil.fillColumn(speakerColumn, "speaker", 40, 200);
		GuiUtil.fillColumn(locationColumn, "location", 40, 200);
		GuiUtil.fillColumn(capacityColumn, "capacity", 40, 60);

		sessionTable.setItems(FXCollections.observableArrayList(sessions));
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
		// update the view
		fillTableColumns(((SessionCalendarFacade) getFacade()).getAllSessions());
		((SessionFiltersController) sessionFiltersController).UpdateAcademicYear();
		sessionTable.getSelectionModel().selectFirst(); // select first session
	}

	void updateWithSession(Session s) {
		update();
		sessionTable.getSelectionModel().select(s); // select newly added session
	}

}
