package gui.controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import main.domain.Member;
import main.domain.MemberStatus;
import main.domain.MemberType;
import main.domain.facades.LoggedInMemberManager;
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

	private final Map<String, MemberType> typeMap = new LinkedHashMap<>(); // linkedhashmaps because their iteration
																			// order does not change
	private final Map<String, MemberStatus> statusMap = new LinkedHashMap<>();

	@FXML
	public void initialize() {
		// Fill maps
		Stream.of(MemberStatus.values()).forEach(ms -> statusMap.put(ms.toString(), ms));
		Stream.of(MemberType.values()).forEach(mt -> typeMap.put(mt.toString(), mt));

		// Populate comboboxes
		typeFilterBox.getItems().add("Alle");
		typeFilterBox.getItems().addAll(typeMap.keySet());
		typeFilterBox.getSelectionModel().selectFirst();

		statusFilterBox.getItems().add("Alle");
		statusFilterBox.getItems().addAll(statusMap.keySet());
		statusFilterBox.getSelectionModel().selectFirst();

		// New User Button only works when you can add users
		if (!LoggedInMemberManager.getInstance().loggedInMemberCanManipulateUsers())
			newUserButton.setDisable(true);
		else
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
				.filter(m -> typeFilter.equals("Alle") ? true : m.getMemberType().equals(typeMap.get(typeFilter)))
				.filter(m -> statusFilter.equals("Alle") ? true
						: m.getMemberStatus().equals(statusMap.get(statusFilter)))
				.collect(Collectors.toList());
	}

	private void handleNewUser() {
		((UserSceneController) getParentController()).displayOnRightPane("NewUser");
	}

}