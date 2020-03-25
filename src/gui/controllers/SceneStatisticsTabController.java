package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.domain.Session;
import main.domain.facades.LoggedInMemberManager;

public class SceneStatisticsTabController extends GuiController {

	private Session inspectedSession;
	private SessionSceneController ssc;
	
	@FXML
	private Label totalAttendees, totalRegistrees;
	
	@FXML
	private Button exportButton;
	
	@FXML
	public void initialize() {
		ssc = getMainController().getSessionSceneController();
		inspectedSession = ssc.getInspectedSession();
	}
	
	private void updateLabels() {
		totalAttendees.setText(String.valueOf(inspectedSession.countAttendees()));
		totalRegistrees.setText(String.valueOf(inspectedSession.countRegistrees()));
	}
	
	public Session getInspectedSession() {
		return inspectedSession;
	}

	public void setInspectedSession(Session inspectedSession) {
		if (inspectedSession == null)
			return;
		this.inspectedSession = inspectedSession;
		updateLabels();

	}
}
