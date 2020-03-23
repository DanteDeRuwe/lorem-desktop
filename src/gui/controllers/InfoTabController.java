package gui.controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.domain.Session;
import main.domain.facades.SessionCalendarFacade;
import main.services.GuiUtil;
import main.services.Util;

public class InfoTabController extends GuiController {

	private Session inspectedSession;

	@FXML private AnchorPane infoTabRoot, descriptionContainer;
	@FXML private Label sessionTitle, sessionDate, sessionTime, sessionLocation, sessionSpeaker;
	@FXML private Text sessionDescription;
	@FXML private Button editSessionButton, deleteSessionButton;
	@FXML private HBox byLabels;
    @FXML private Hyperlink externalUrlHyperlink;

	@FXML
	public void initialize() {
		SessionSceneController ssc = getMainController().getSessionSceneController();

		// Event handlers
		deleteSessionButton.setOnMouseClicked((e) -> handleDeleteSession());
		editSessionButton.setOnMouseClicked(
				(e) -> { ssc.displayOnRightPane("EditSession"); }
		);
	}

	private void handleDeleteSession() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sessie verwijderen");
		alert.setHeaderText("Waarschuwing");
		alert.setContentText(
				String.format(
						"Ben je zeker dat je de sessie \"%s\" wilt verwijderen?",
						inspectedSession.getTitle()
				)
		);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			((SessionCalendarFacade) getFacade()).deleteSession(inspectedSession);
			getMainController().getSessionSceneController().update();
		}
	}

	private void updateLabels() {
		if (inspectedSession == null)
			return;

		sessionTitle.setText(inspectedSession.getTitle());
		sessionDate.setText(inspectedSession.getStart().format(Util.DATEFORMATTER));
		sessionTime.setText(inspectedSession.getStart().format(Util.TIMEFORMATTER));
		sessionLocation.setText(inspectedSession.getLocation());
		sessionDescription.setText(inspectedSession.getDescription());
		GuiUtil.updateHyperlink(inspectedSession, externalUrlHyperlink);
		
		
		
		// Hide "Door <Speaker>" labels when there is no speaker in session
		if (!inspectedSession.getSpeakerName().isBlank()) {
			byLabels.setVisible(true);
			sessionSpeaker.setText(inspectedSession.getSpeakerName());
		} else
			byLabels.setVisible(false);
	}
	
	

	public Session getInspectedSession() {
		return inspectedSession;
	}

	public void setInspectedSession(Session inspectedSession) {
		this.inspectedSession = inspectedSession;
		updateLabels();
	}

}
