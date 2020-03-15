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
import javafx.scene.text.TextBoundsType;
import main.domain.Session;
import main.domain.facades.SessionCalendarFacade;
import main.services.Util;

public class InfoTabController extends GuiController {

	private Session inspectedSession;

	@FXML private AnchorPane infoTabRoot;

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
		sessionDescription.setText(
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");

		sessionDescription.wrappingWidthProperty().bind(infoTabRoot.widthProperty());
		sessionDescription.setBoundsType(TextBoundsType.VISUAL);
	}

}
