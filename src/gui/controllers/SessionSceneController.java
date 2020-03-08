package gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import main.domain.Session;
import main.domain.facades.SessionCalendarFacade;
import main.services.GuiUtil;

public class SessionSceneController extends GuiController {

	// TODO: temporary facade tot getFacade werkt
	private SessionCalendarFacade tempFacade = new SessionCalendarFacade();

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
	public TableView<Session> sessionTable; // public since child controllers will need access to it
	private ObservableList<Session> sessionList;

	@FXML
	private TableColumn<Session, String> titleColumn;
	@FXML
	private TableColumn<Session, String> startColumn;
	@FXML
	private TableColumn<Session, String> durationColumn;
	@FXML
	private TableColumn<Session, String> organizerColumn;
	@FXML
	private TableColumn<Session, String> speakerColumn;
	@FXML
	private TableColumn<Session, String> locationColumn;
	@FXML
	private TableColumn<Session, String> capacityColumn;

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

		fillTableColumns();
	}

	private void fillTableColumns() {
		sessionList = FXCollections.observableArrayList(tempFacade.getAllSessions()); // TODO: vervangen door getFacade
		// wanneer het werkt

		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

		startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
		startColumn.setMinWidth(120);
		startColumn.setMaxWidth(120);

		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
		durationColumn.setMinWidth(45);
		durationColumn.setMaxWidth(45);

		locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
		locationColumn.setMinWidth(120);

		organizerColumn.setCellValueFactory(new PropertyValueFactory<>("organizer"));

		speakerColumn.setCellValueFactory(new PropertyValueFactory<>("speaker"));

		capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
		capacityColumn.setMinWidth(50);
		capacityColumn.setMaxWidth(50);

		sessionTable.setItems(sessionList);
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
