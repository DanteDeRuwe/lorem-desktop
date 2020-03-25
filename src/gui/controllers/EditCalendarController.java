package gui.controllers;

import com.jfoenix.controls.JFXDatePicker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.domain.SessionCalendar;
import main.domain.facades.SessionCalendarFacade;
import main.exceptions.UserNotAuthorizedException;
import main.services.Alerts;
import main.services.GuiUtil;

public class EditCalendarController extends GuiController {

	@FXML
	private Label topLabel;
	@FXML
	private JFXDatePicker startDatePicker, endDatePicker;
	@FXML
	private Button saveButton, cancelButton;

	@FXML
	public void initialize() {
		topLabel.setText("Wijzig Kalender");

		// Date picker formats
		GuiUtil.fixDatePicker(startDatePicker);
		GuiUtil.fixDatePicker(endDatePicker);

		fillInFields();
		saveButton.setOnMouseClicked((e) -> handleSave());
		cancelButton.setOnMouseClicked((e) -> goBack());
	}

	private void fillInFields() {
		SessionCalendar calendar = getMainController().getCalendarSceneController().getInspectedCalendar();
		startDatePicker.setValue(calendar.getStartDate());
		endDatePicker.setValue(calendar.getEndDate());
	}

	private void handleSave() {
		try {
			((SessionCalendarFacade) getFacade()).editSessionCalendar(
					getMainController().getCalendarSceneController().getInspectedCalendar(), startDatePicker.getValue(),
					endDatePicker.getValue());
			fillInFields();
			getMainController().getCalendarSceneController().update();
			goBack();
		} catch (IllegalArgumentException e) {
			if (e.getMessage().equals("Academic years must start and end in consecutive years")) {
				Alerts.errorAlert("Kalender wijzigen",
						"Een academiejaar moet starten en eindigen in opeenvolgende jaren").showAndWait();
			}
		} catch (UserNotAuthorizedException e) {
			Alerts.errorAlert("Kalender wijzigen", "Je hebt niet de juiste machtigingen om deze kalender te wijzigen.")
					.show();
		}
	}

	private void goBack() {
		getMainController().getCalendarSceneController().displayCalendarList();
	}

}
