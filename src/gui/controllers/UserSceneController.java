package gui.controllers;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import main.domain.Member;
import main.domain.facades.MemberFacade;
import main.services.GuiUtil;

public class UserSceneController extends GuiController {

	// Controllers
	private GuiController userDetailsController, newUserController, userFiltersController;

	// Own vars
	private AnchorPane userDetails, newUser, userFilters;

	// FXML vars
	@FXML
	private AnchorPane leftPane, middlePane, rightPane;
	@FXML
	protected TableView<Member> userTable;
	@FXML
	private TableColumn<Member, String> lastNameColumn, firstNameColumn, usernameColumn;
	@FXML
	private TableColumn<Member, Object> typeColumn, statusColumn;

	/*
	 * Init
	 */

	@FXML
	public void initialize() {

		// initialize controllers
		userDetailsController = new UserDetailsController();
		newUserController = new NewUserController();
		userFiltersController = new UserFiltersController();

		// load FXML once, this also sets parentcontrollers and facades
		userDetails = loadFXML("users/UserDetails.fxml", userDetailsController, this.getFacade());
		newUser = loadFXML("users/NewUser.fxml", newUserController, this.getFacade());
		userFilters = loadFXML("users/UserFilters.fxml", userFiltersController, this.getFacade());

		// Center Panel
		fillTableColumns(((MemberFacade) getFacade()).getAllMembers());

		// Right panel
		GuiUtil.bindAnchorPane(userDetails, rightPane);

		// Left Panel
		GuiUtil.bindAnchorPane(userFilters, leftPane);

		// Event Handlers
		userTable.getSelectionModel().selectedItemProperty()
				.addListener((x, y, user) -> ((UserDetailsController) userDetailsController).setInspectedUser(user));

	}

	/*
	 * Helpers
	 */

	void fillTableColumns(List<Member> members) {
		GuiUtil.fillColumn(lastNameColumn, "lastName", 40, 200);
		GuiUtil.fillColumn(firstNameColumn, "firstName", 40, 200);
		GuiUtil.fillColumn(usernameColumn, "username", 40, 200);
		GuiUtil.fillColumnWithObjectToString(typeColumn, "memberType", 40, 200);
		GuiUtil.fillColumnWithObjectToString(statusColumn, "memberStatus", 40, 200);

		userTable.setItems(FXCollections.observableArrayList(members));
	}

	void update() {
		// update the view
		fillTableColumns(((MemberFacade) getFacade()).getAllMembers());
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
		else
			throw new RuntimeException("key not valid");
	}

}
