package gui.controllers;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.domain.Session;
import main.domain.facades.SessionCalendarFacade;
import main.services.Util;

public class InfoTabController extends GuiController {

	private Session inspectedSession;

	@FXML private AnchorPane infoTabRoot, descriptionContainer;
	@FXML private Label sessionTitle, sessionDate, sessionTime, sessionLocation, sessionSpeaker;
	@FXML private Text sessionDescription;
	@FXML private Button editSessionButton, deleteSessionButton;

	@FXML
	public void initialize() {
		deleteSessionButton.setOnMouseClicked((event) -> handleDeleteSession());
	}

	private void handleDeleteSession() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sessie verwijderen");
		alert.setHeaderText("Waarschuwing");
		alert.setContentText(String.format("Ben je zeker dat je de sessie \"%s\" wilt verwijderen?", inspectedSession.getTitle()));

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			((SessionCalendarFacade) getFacade()).deleteSession(inspectedSession);
			getMainController().getSessionSceneController().update();
		}
	}

	public void setInspectedSession(Session session) {
		inspectedSession = session;
		updateLabels();
	}

	private void updateLabels() {
		if (inspectedSession == null)
			return;

		sessionTitle.setText(inspectedSession.getTitle());
		sessionDate.setText(inspectedSession.getStart().format(Util.DATEFORMATTER));
		sessionTime.setText(inspectedSession.getStart().format(Util.TIMEFORMATTER));
		sessionLocation.setText(inspectedSession.getLocation());
		sessionSpeaker.setText(inspectedSession.getSpeakerName());
		sessionDescription.setText(inspectedSession.getDescription());
	}

}
