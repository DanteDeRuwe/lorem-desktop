package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import main.domain.facades.Facade;
import main.domain.facades.MemberFacade;
import main.domain.facades.SessionCalendarFacade;
import main.services.GuiUtil;

public class MainController extends GuiContoller {

	private Facade sessionFacade, memberFacade, sessionCalendarFacade;

	// fxml
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
	public void initialize() {

		// initialize facades
		sessionCalendarFacade = new SessionCalendarFacade();
		memberFacade = new MemberFacade();

		// bind initial panes
		GuiUtil.bindAnchorPane("sessions/SessionSideBar.fxml", leftPane, sessionCalendarFacade);
		GuiUtil.bindAnchorPane("sessions/DetailsPane.fxml", rightPane, sessionCalendarFacade);

		// Make tableview
		// TODO

	}

}
