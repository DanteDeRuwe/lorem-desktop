package gui.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import main.domain.Member;
import main.domain.facades.MemberFacade;

public class UserFiltersController extends GuiController {

	@FXML
	private JFXButton newUserButton;
	@FXML
	private JFXTextField lastNameFilterField, firstNameFilterField, usernameFilterField;

	@FXML
	public void initialize() {
		// Event Listeners
		newUserButton.setOnAction(e -> handleNewUser());

		// Filtering
		lastNameFilterField.textProperty().addListener((obs, oldText, newText) -> {
			getMainController().getUserSceneController().fillTableColumns(filter());
		});
		firstNameFilterField.textProperty().addListener((obs, oldText, newText) -> {
			getMainController().getUserSceneController().fillTableColumns(filter());
		});
		usernameFilterField.textProperty().addListener((obs, oldText, newText) -> {
			getMainController().getUserSceneController().fillTableColumns(filter());
		});
	}

	private List<Member> filter() {
		String lastNameFilter = lastNameFilterField.getText();
		String firstNameFilter = firstNameFilterField.getText();
		String usernameFilter = usernameFilterField.getText();
		List<Member> memberList = new ArrayList<>(((MemberFacade) getFacade()).getAllMembers());
		return memberList.stream()
				.filter(m -> m.getLastName().toLowerCase().contains((lastNameFilter.toLowerCase().trim())))
				.filter(m -> m.getFirstName().toLowerCase().contains((firstNameFilter.toLowerCase().trim())))
				.filter(m -> m.getUsername().toLowerCase().contains((usernameFilter.toLowerCase().trim())))
				.collect(Collectors.toList());
	}

	private void handleNewUser() {
		((UserSceneController) getParentController()).displayOnRightPane("NewUser");
	}

}