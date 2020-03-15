package gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import main.domain.Member;
import main.domain.Session;
import main.domain.facades.MemberFacade;
import main.services.GuiUtil;

public class UserSceneController extends GuiController {
	
	// Own vars
	private ObservableList<Member> userList;
	
	// FXML vars
	@FXML private AnchorPane leftPane, middlePane, rightPane;
	@FXML protected TableView<Member> userTable;
	@FXML private TableColumn<Member, String> lastNameColumn, firstNameColumn, usernameColumn;
	@FXML private TableColumn<Member, Object> typeColumn, statusColumn;
	
	/*
	 * Init
	 */

	@FXML
	public void initialize() {

		// Center Panel
		fillTableColumns();
		
		
	}
	
	/*
	 * Helpers
	 */
	
	void fillTableColumns() {
		userList = FXCollections.observableArrayList(((MemberFacade) getFacade()).getAllMembers());
		
		GuiUtil.fillColumn(lastNameColumn, "lastName", 40, 200);
		GuiUtil.fillColumn(firstNameColumn, "firstName", 40, 200);
		GuiUtil.fillColumn(usernameColumn, "username", 40, 200);
		GuiUtil.fillColumnWithObjectToString(typeColumn, "memberType", 40, 200);
		GuiUtil.fillColumnWithObjectToString(statusColumn, "memberStatus", 40, 200);
		
		userTable.setItems(userList);
	}
	
}
