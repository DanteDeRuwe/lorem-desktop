package gui.controllers;

import java.time.LocalDate;

import com.jfoenix.controls.JFXDatePicker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.domain.SessionCalendar;
import main.domain.facades.SessionCalendarFacade;
import main.services.GuiUtil;

public class NewCalendarController extends GuiController {

	@FXML private JFXDatePicker startDatePicker, endDatePicker;
	@FXML private Button saveButton, cancelButton;

	@FXML
	public void initialize() {
		// Date picker format
		GuiUtil.fixDatePicker(startDatePicker);
		GuiUtil.fixDatePicker(endDatePicker);

		// Event listeners
		saveButton.setOnMouseClicked((event) -> handleCreate());
		cancelButton.setOnMouseClicked((event) -> goBack());
	}

	private void handleCreate() {

		LocalDate start = startDatePicker.getValue();
		LocalDate end = endDatePicker.getValue();

		SessionCalendarFacade scf = ((SessionCalendarFacade) getFacade());

		SessionCalendar sc = scf.createSessionCalendar(start, end);
		scf.addSessionCalendar(sc);

		getMainController().getCalendarSceneController().update();

		goBack();
	}

	private void goBack() {
		getMainController().getCalendarSceneController().displayCalendarList();
	}

}
