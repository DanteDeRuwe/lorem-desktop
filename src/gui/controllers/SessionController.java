package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.domain.Session;

public class SessionController {

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

	private Session selectedSession;

	@FXML
	public void initialize() {

	}

}
