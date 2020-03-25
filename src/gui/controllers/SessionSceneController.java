package gui.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import main.domain.Session;
import main.domain.facades.SessionCalendarFacade;
import main.services.GuiUtil;

public class SessionSceneController extends GuiController {

	// Controllers
	private GuiController sessionFiltersController, sessionTabsController, newSessionController, editSessionController;

	// Own vars
	private AnchorPane sessionFilters, sessionTabs, newSession, editSession;
	private ObservableList<Session> sessionList;
	private Session inspectedSession;

	// FXML vars
	@FXML
	private SplitPane splitpane;
	@FXML
	private AnchorPane leftPane, middlePane, rightPane;
	@FXML
	protected TableView<Session> sessionTable;
	@FXML
	private TableColumn<Session, String> titleColumn, organizerColumn, speakerColumn, locationColumn, capacityColumn,
			typeColumn;
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
		newSession = loadFXML("sessions/EditOrCreateSession.fxml", newSessionController, this.getFacade());
		sessionTabs = loadFXML("sessions/SessionTabs.fxml", sessionTabsController, this.getFacade());

		// Left Panel
		GuiUtil.bindAnchorPane(sessionFilters, leftPane);

		// Center Panel
		GuiUtil.setTablePlaceholderText(sessionTable, "Het is hier nogal leeg...");
		fillTableColumns(((SessionCalendarFacade) getFacade()).getAllSessions());

		// Right panel: activated when selecting a session
		displayOnRightPane("NothingAndShrink");
		sessionTable.getSelectionModel().selectedItemProperty().addListener((x, y, session) -> {
			setInspectedSession(session);
			((SessionTabsController) sessionTabsController).updateInspectedSession(session);
			displayOnRightPane("SessionTabs");
		});

		// select the first session by default
		sessionTable.getSelectionModel().selectFirst();

		// event handler for doubleclicking a session
		sessionTable.setOnMouseClicked(mouseClickedEvent -> {
			if (mouseClickedEvent.getButton().equals(MouseButton.PRIMARY) && mouseClickedEvent.getClickCount() == 2) {
				displayOnRightPane("EditSession");
			}
		});
	}

	/*
	 * Helpers, these are package private so other guicontrollers can access them
	 */

	void fillTableColumns(Set<Session> sessions) {
		GuiUtil.fillColumn(titleColumn, "title", 40, 500);
		GuiUtil.fillColumnWithDateTime(startColumn, "start", 40, 150);
		GuiUtil.fillColumnWithDuration(durationColumn, "duration", 60, 100);
		GuiUtil.fillColumn(organizerColumn, "organizer", 40, 300);
		GuiUtil.fillColumn(speakerColumn, "speaker", 40, 200);
		GuiUtil.fillColumn(locationColumn, "location", 40, 200);
		GuiUtil.fillColumn(capacityColumn, "capacity", 40, 60);
		GuiUtil.fillColumn(typeColumn, "type", 40, 300);

		sessionTable.setItems(FXCollections.observableArrayList(sessions));
		sessionTable.getSortOrder().add(startColumn);
		sessionTable.refresh();
	}

	void displayOnRightPane(String key) {

		// If no sessions, and no plan to create one, just stay collapsed
		if (((SessionCalendarFacade) getFacade()).getAllSessions().isEmpty() && !key.equals("NewSession"))
			key = "NothingAndShrink";
		// else show the right pane if it was previously collapsed
		else if (splitpane.getDividerPositions()[1] == 1.0)
			splitpane.setDividerPosition(1, 0.7);

		// Look what to display
		if (key.equals("SessionTabs"))
			GuiUtil.bindAnchorPane(sessionTabs, rightPane);
		else if (key.equals("NewSession"))
			GuiUtil.bindAnchorPane(newSession, rightPane);
		else if (key.equals("EditSession")) {
			// editing a session relies heavily on a selected session.
			// That's why we only load it when needed.
			editSessionController = new EditSessionController();
			editSession = loadFXML("sessions/EditOrCreateSession.fxml", editSessionController, this.getFacade());
			GuiUtil.bindAnchorPane(editSession, rightPane);
		} else if (key.equals("NothingAndShrink")) {
			rightPane.getChildren().clear();
			splitpane.setDividerPosition(1, 1.0); // collapse to the right
		} else
			throw new RuntimeException("displayOnRightPane: key not valid");
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

	public Session getInspectedSession() {
		return inspectedSession;
	}

	public void setInspectedSession(Session inspectedSession) {
		this.inspectedSession = inspectedSession;
	}

}
