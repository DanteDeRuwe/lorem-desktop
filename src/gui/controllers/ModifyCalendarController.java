package gui.controllers;

import com.jfoenix.controls.JFXDatePicker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.domain.SessionCalendar;
import main.domain.facades.SessionCalendarFacade;

public class ModifyCalendarController extends GuiController {

	@FXML
	private JFXDatePicker startDatePicker, endDatePicker;
	@FXML
	private Button saveButton, cancelButton;

	@FXML
	public void initialize() {
		fillInFields();
		saveButton.setOnMouseClicked((event) -> handleSave());
		cancelButton.setOnMouseClicked((event) -> handleCancel());
	}

	private void fillInFields() {
		SessionCalendar calendar = getMainController().getCalendarSceneController().getInspectedCalendar();
		startDatePicker.setValue(calendar.getStartDate());
		endDatePicker.setValue(calendar.getEndDate());
	}

	private void handleSave() {
		((SessionCalendarFacade) getMainController().getCalendarSceneController().getFacade()).editSessionCalendar(
				getMainController().getCalendarSceneController().getInspectedCalendar(), startDatePicker.getValue(),
				endDatePicker.getValue());
		fillInFields();
		getMainController().getCalendarSceneController().goBack();
	}

	private void handleCancel() {
		fillInFields();
		getMainController().getCalendarSceneController().goBack();
	}

}
