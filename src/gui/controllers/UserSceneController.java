package gui.controllers;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import main.domain.Member;
import main.domain.facades.MemberFacade;
import main.services.GuiUtil;

public class UserSceneController extends GuiController {

	// Controllers
	private GuiController userDetailsController, newUserController, userFiltersController, editUserController;

	// Own vars
	private AnchorPane userDetails, newUser, userFilters, editUser;

	// FXML vars
	@FXML
	private AnchorPane leftPane, middlePane, rightPane;
	@FXML
	protected TableView<Member> userTable;
	@FXML
	private TableColumn<Member, String> lastNameColumn, firstNameColumn, usernameColumn;
	@FXML
	private TableColumn<Member, Object> typeColumn, statusColumn;

	private MemberFacade mf;
	private UserDetailsController udc;

	@FXML
	public void initialize() {
		mf = (MemberFacade) getFacade();
		udc = ((UserDetailsController) userDetailsController);

		// initialize controllers
		userDetailsController = new UserDetailsController();
		newUserController = new NewUserController();
		userFiltersController = new UserFiltersController();

		// load FXML once, this also sets parentcontrollers and facades
		userDetails = loadFXML("users/UserDetails.fxml", userDetailsController, getFacade());
		newUser = loadFXML("users/EditOrCreateUser.fxml", newUserController, getFacade());
		userFilters = loadFXML("users/UserFilters.fxml", userFiltersController, getFacade());

		// Center Panel
		GuiUtil.setTablePlaceholderText(userTable, "Het is hier nogal leeg...");
		fillTableColumns(mf.getAllMembers());

		// Right panel
		GuiUtil.bindAnchorPane(userDetails, rightPane);

		// Left Panel
		GuiUtil.bindAnchorPane(userFilters, leftPane);

		// Event Handlers
		userTable.getSelectionModel().selectedItemProperty().addListener((x, y, user) -> {
			((UserDetailsController) userDetailsController).setInspectedUser(user);
			GuiUtil.bindAnchorPane(userDetails, rightPane);
		});
		userTable.getSelectionModel().selectFirst();

		// Double click to edit user
		userTable.setOnMouseClicked(mouseClickedEvent -> {
			if (mouseClickedEvent.getButton().equals(MouseButton.PRIMARY) && mouseClickedEvent.getClickCount() == 2) {
				displayOnRightPane("EditUser");
			}
		});

	}

	void update() {
		// update the view
		fillTableColumns(mf.getAllMembers());
		userTable.getSelectionModel().selectFirst(); // select first user
	}

	void updateWithMember(Member m) {
		update();
		userTable.getSelectionModel().select(m); // select newly added session
	}

	void displayOnRightPane(String key) {
		if (key.equals("UserDetails"))
			GuiUtil.bindAnchorPane(userDetails, rightPane);
		else if (key.equals("NewUser"))
			GuiUtil.bindAnchorPane(newUser, rightPane);
		else if (key.equals("EditUser")) {
			// editing a user relies heavily on a selected session.
			// That's why we only load it when needed.
			editUserController = new EditUserController();
			editUser = loadFXML("users/EditOrCreateUser.fxml", editUserController, this.getFacade());
			GuiUtil.bindAnchorPane(editUser, rightPane);
		} else
			throw new RuntimeException("key not valid");
	}

	public Member getInspectedUser() {
		return ((UserDetailsController) userDetailsController).getInspectedUser();
	}

	public void fillTableColumns(List<Member> members) {
		GuiUtil.fillColumn(lastNameColumn, "lastName", 40, 200);
		GuiUtil.fillColumn(firstNameColumn, "firstName", 40, 200);
		GuiUtil.fillColumn(usernameColumn, "username", 40, 200);
		GuiUtil.fillColumnWithObjectToString(typeColumn, "memberType", 40, 200);
		GuiUtil.fillColumnWithObjectToString(statusColumn, "memberStatus", 40, 200);

		userTable.setItems(FXCollections.observableArrayList(members));
		userTable.getSortOrder().add(usernameColumn);
		userTable.refresh();
	}

}
