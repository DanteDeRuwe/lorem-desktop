package gui.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import main.domain.Member;
import main.domain.MemberStatus;
import main.domain.MemberType;
import main.domain.facades.MemberFacade;

public class UserFiltersController extends GuiController {

	@FXML
	private JFXButton newUserButton;
	@FXML
	private JFXTextField lastNameFilterField, firstNameFilterField, usernameFilterField;
	@FXML
	private JFXComboBox<String> typeFilterBox;
	@FXML
	private JFXComboBox<String> statusFilterBox;

	private final Map<String, MemberType> typeMap = new HashMap<>();
	private final Map<String, MemberStatus> statusMap = new HashMap<>();

	@FXML
	public void initialize() {
		// Fill maps
		typeMap.put("Gebruiker", MemberType.USER);
		typeMap.put("Verantwoordelijke", MemberType.ADMIN);
		typeMap.put("Hoofdverantwoordelijke", MemberType.HEADADMIN);

		statusMap.put("Actief", MemberStatus.ACTIVE);
		statusMap.put("Inactief", MemberStatus.INACTIVE);
		statusMap.put("Geblokkeerd", MemberStatus.BLOCKED);

		// Populate comboboxes
		typeFilterBox.getItems().add("/");
		typeFilterBox.getItems().addAll(typeMap.keySet());
		typeFilterBox.getSelectionModel().selectFirst();

		statusFilterBox.getItems().add("/");
		statusFilterBox.getItems().addAll(statusMap.keySet());
		statusFilterBox.getSelectionModel().selectFirst();

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
		typeFilterBox.valueProperty().addListener((obv, oldValue, newValue) -> {
			getMainController().getUserSceneController().fillTableColumns(filter());
		});
		statusFilterBox.valueProperty().addListener((obv, oldValue, newValue) -> {
			getMainController().getUserSceneController().fillTableColumns(filter());
		});
	}

	private List<Member> filter() {
		String lastNameFilter = lastNameFilterField.getText();
		String firstNameFilter = firstNameFilterField.getText();
		String usernameFilter = usernameFilterField.getText();
		String typeFilter = typeFilterBox.getValue();
		String statusFilter = statusFilterBox.getValue();
		List<Member> memberList = new ArrayList<>(((MemberFacade) getFacade()).getAllMembers());
		return memberList.stream()
				.filter(m -> m.getLastName().toLowerCase().contains((lastNameFilter.toLowerCase().trim())))
				.filter(m -> m.getFirstName().toLowerCase().contains((firstNameFilter.toLowerCase().trim())))
				.filter(m -> m.getUsername().toLowerCase().contains((usernameFilter.toLowerCase().trim())))
				.filter(m -> typeFilter.equals("/") ? true : m.getMemberType().equals(typeMap.get(typeFilter)))
				.filter(m -> statusFilter.equals("/") ? true : m.getMemberStatus().equals(statusMap.get(statusFilter)))
				.collect(Collectors.toList());
	}

	private void handleNewUser() {
		((UserSceneController) getParentController()).displayOnRightPane("NewUser");
	}

}