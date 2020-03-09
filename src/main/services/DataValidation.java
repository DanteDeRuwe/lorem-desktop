package main.services;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DataValidation {

	public static boolean textFilledIn(TextField inputTextField, Label validationLabel, String validationText) {
		if (inputTextField.getText().isEmpty()) {
			validationLabel.setText(validationText);
			return false;
		}
		return true;
	}

	public static boolean textAlphabet(TextField inputTextField, Label validationLabel, String validationText) {
		if (!inputTextField.getText().matches("[a-z A-Z]+")) {
			validationLabel.setText(validationText);
			return false;
		}
		return true;
	}

	public static boolean textNumeric(TextField inputTextField, Label validationLabel, String validationText) {
		if (!inputTextField.getText().matches("[0-9]+")) {
			validationLabel.setText(validationText);
			return false;
		}
		return true;
	}

	public static boolean textEmail(TextField inputTextField, Label validationLabel, String validationText) {

		if (!inputTextField.getText().matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")) {
			validationLabel.setText(validationText);
			return false;
		}
		return true;

	}

}