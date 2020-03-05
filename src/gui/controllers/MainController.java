package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import main.services.Util;

public class MainController {

	@FXML
	private AnchorPane main;

	@FXML
	private MenuBar menuBar;

	@FXML
	private Menu sessionMenu, calendarMenu, memberMenu, accountMenu, statsMenu, aboutMenu;

	@FXML
	private MenuItem newSessionMenuItem, editSessionMenuItem, openCalendarMenuItem, editCalendarMenuItem,
			newMemberMenuItem, editMemberMenuItem, logOutMenuItem, editAccountMenuItem;

	@FXML
	private AnchorPane leftPane, centerPane, rightPane;

	@FXML
	private SessionController sessionController;

	@FXML
	public void initialize() {

		// bind panes
		Util.bindAnchorPane("sessions/SessionSideBar.fxml", leftPane);
		Util.bindAnchorPane("sessions/DetailsPane.fxml", rightPane);
	}

}
