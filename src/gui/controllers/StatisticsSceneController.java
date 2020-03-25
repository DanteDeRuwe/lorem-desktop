package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.domain.facades.SessionCalendarFacade;

public class StatisticsSceneController extends GuiController {

	@FXML
	private Label aantalAfgelopenSessies, aantalInschrijvingenAS, aantalAanwezigenAS, gemiddeldeIpSAS, gemiddeldeApS,
			gemiddeldeApI, aantalGeplandeSessies, aantalInschrijvingenGS, gemiddeldeIpSGS;
	
	@FXML
	private Button updateButton;
	
	@FXML
	public void initialize() {
		update();
		updateButton.setOnMouseClicked((event) -> update());
	}
	
	private void update() {
		SessionCalendarFacade f = (SessionCalendarFacade) getMainController().getSessionCalendarFacade();
		
		aantalAfgelopenSessies.setText(String.valueOf(f.getAllFinishedSessions().size()));
		aantalInschrijvingenAS.setText(String.valueOf(f.getTotalRegistreesFS()));
		aantalAanwezigenAS.setText(String.valueOf(f.getTotalAttendeesFS()));
		gemiddeldeIpSAS.setText(String.valueOf(f.getAverageRpSFS()));
		gemiddeldeApS.setText(String.valueOf(f.getAverageApSFS()));
		gemiddeldeApI.setText(String.valueOf(f.getAverageApRFS()));
		
		aantalGeplandeSessies.setText(String.valueOf(f.getAllPlannedSessions().size()));
		aantalInschrijvingenGS.setText(String.valueOf(f.getTotalRegistreesPS()));
		gemiddeldeIpSGS.setText(String.valueOf(f.getAverageRpSPS()));
		
	}
	
}
