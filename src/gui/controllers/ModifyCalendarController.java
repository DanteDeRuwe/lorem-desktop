package gui.controllers;

import com.jfoenix.controls.JFXDatePicker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.domain.SessionCalendar;
import main.domain.facades.SessionCalendarFacade;
import main.services.GuiUtil;

public class ModifyCalendarController extends GuiController {

	@FXML private Label topLabel;
	@FXML private JFXDatePicker startDatePicker, endDatePicker;
	@FXML private Button saveButton, cancelButton;

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
		((SessionCalendarFacade) getFacade()).editSessionCalendar(
				getMainController().getCalendarSceneController().getInspectedCalendar(), startDatePicker.getValue(),
				endDatePicker.getValue()
		);
		fillInFields();
		getMainController().getCalendarSceneController().update();
		goBack();
	}

	private void goBack() {
		getMainController().getCalendarSceneController().displayCalendarList();
	}

}
