package main.services;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DataValidation {

	public static boolean textFilledIn(TextField inputTextField, Label validationLabel, String validationText) {
		if (inputTextField.getText().isEmpty()) {
			validationLabel.setText(validationLabel.getText() + "\n" + validationText);
			return false;
		}
		return true;
	}

	public static boolean dateFilledIn(JFXDatePicker datepicker, Label validationLabel, String validationText) {
		if (datepicker.getValue() == null) {
			validationLabel.setText(validationLabel.getText() + "\n" + validationText);
			return false;
		}
		return true;
	}

	public static boolean timeFilledIn(JFXTimePicker timepicker, Label validationLabel, String validationText) {
		if (timepicker.getValue() == null) {
			validationLabel.setText(validationLabel.getText() + "\n" + validationText);
			return false;
		}
		return true;
	}

	public static boolean textAlphabet(TextField inputTextField, Label validationLabel, String validationText) {
		if (!inputTextField.getText().matches("[a-z A-Z]+")) {
			validationLabel.setText(validationLabel.getText() + "\n" + validationText);
			return false;
		}
		return true;
	}

	public static boolean textNumeric(TextField inputTextField, Label validationLabel, String validationText) {
		if (!inputTextField.getText().matches("[0-9]+\\.?[0-9]*")) {
			validationLabel.setText(validationLabel.getText() + "\n" + validationText);
			return false;
		}
		return true;
	}

	public static boolean textEmail(TextField inputTextField, Label validationLabel, String validationText) {

		if (!inputTextField.getText().matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")) {
			validationLabel.setText(validationLabel.getText() + "\n" + validationText);
			return false;
		}
		return true;
	}

	public static boolean textImagePath(TextField inputTextField, Label validationLabel, String validationText) {

		if (!inputTextField.getText().matches("^(http(s?):)?([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)(\\?.*)?$")) {
			validationLabel.setText(validationLabel.getText() + "\n" + validationText);
			return false;
		}
		return true;
	}

	public static boolean isDuration(JFXTextField inputTextField, Label validationLabel, String validationText) {
		if (!inputTextField.getText().matches("\\d+:\\d{2}")) {
			validationLabel.setText(validationLabel.getText() + "\n" + validationText);
			return false;
		}
		return true;
	}
	
	public static boolean textExternalLink(TextField inputTextField, Label validationLabel, String validationText) {

		if (!inputTextField.getText().matches("^(https?:\\/\\/)?([\\w\\-])+\\.{1}([a-zA-Z]{2,63})([\\/\\w-]*)*\\/?\\??([^#\\n\\r]*)?#?([^\\n\\r]*)$")) {
			validationLabel.setText(validationLabel.getText() + "\n" + validationText);
			return false;
		}
		return true;
	}


}