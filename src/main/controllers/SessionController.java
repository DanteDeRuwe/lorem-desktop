package main.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

}
