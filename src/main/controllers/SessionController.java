package main.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.domain.Session;
import main.services.DummySessionProvider;

public class SessionController implements Initializable {

	// seed 50 sessions for now
	private final DummySessionProvider dummySessionProvider = new DummySessionProvider(50);

	@FXML
	private AnchorPane session;

	@FXML
	private TableView<Session> sessionTable;

	@FXML
	private TableColumn<Session, String> sessionTitleCol, sessionLocationCol, sessionDateCol, sessionStartCol,
			sessionEndCol, sessionOrganizerCol, sessionSpeakerCol;

	@FXML
	private VBox detailsBox;
	
	@FXML
	private Button newSessionBtn;
	
	@FXML
	private VBox addSession;
	
	@FXML
	private AddSessionController addSessionController;

	private Session selectedSession;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Bind properties to table cells
		sessionTitleCol.setCellValueFactory(new PropertyValueFactory<Session, String>("title"));
		sessionDateCol.setCellValueFactory(new PropertyValueFactory<Session, String>("date"));
		sessionStartCol.setCellValueFactory(new PropertyValueFactory<Session, String>("start"));
		sessionEndCol.setCellValueFactory(new PropertyValueFactory<Session, String>("end"));
		sessionOrganizerCol.setCellValueFactory(new PropertyValueFactory<Session, String>("organizer"));
		sessionSpeakerCol.setCellValueFactory(new PropertyValueFactory<Session, String>("speaker"));
		sessionLocationCol.setCellValueFactory(new PropertyValueFactory<Session, String>("location"));

		// fill in the table
		sessionTable.getItems().setAll(dummySessionProvider.getSessions()); // dummy!

		// event listener for the selected row
		sessionTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null && !newSelection.equals(oldSelection)) {
				setSelectedSession(newSelection);
				displaySesionDetails();
			}
		});
		
		newSessionBtn.setVisible(false);
		
		newSessionBtn.setOnMouseClicked(x -> {
			System.out.println(addSession);
			detailsBox.getChildren().forEach(node -> node.setVisible(true));
		});
		
		addSessionController.setSessionController(this);
		

	}

	public void setSelectedSession(Session selectedSession) {
		this.selectedSession = selectedSession;
	}

	private void displaySesionDetails() {
		detailsBox.getChildren().clear();
		detailsBox.getChildren()
				.add(new Label("Session details:\n-----------------\n" + this.selectedSession.toString()));
	}

	public Session getSelectedSession() {
		return selectedSession;
	}
	
	public void addSession(String organizer, String title, String speaker, LocalDateTime start, LocalDateTime end, String loc) {
		
		dummySessionProvider.addSession(organizer, title, speaker, start, end, loc);
		System.out.println(dummySessionProvider.getSessions());
	}
	
	public void refreshSessions() {
		sessionTable.getItems().setAll(dummySessionProvider.getSessions());
	}

}
