package gui.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import main.domain.Announcement;
import main.domain.Session;
import main.domain.facades.LoggedInMemberManager;
import main.domain.facades.SessionFacade;
import main.exceptions.UserNotAuthorizedException;
import main.services.Alerts;
import main.services.AnnouncementCellFactory;
import main.services.GuiUtil;

public class AnnouncementTabController extends GuiController {

	private Session inspectedSession;
	private Announcement inspectedAnnouncement;

	@FXML
	private Button addAnnouncementButton, modifyAnnouncementButton, deleteAnnouncementButton;
	@FXML
	private ListView<Announcement> announcementListView;
	@FXML
	private AnchorPane announcementTabRoot;
	@FXML
	private AnchorPane announcementOverview;

	@FXML
	public void initialize() {

		// fill the list
		announcementListView.setCellFactory(new AnnouncementCellFactory<Announcement>());
		update();

		// disable buttons, nothing selected
		deleteAnnouncementButton.setDisable(true);
		modifyAnnouncementButton.setDisable(true);

		// Event listener for selected item (also enables/disables buttons if no
		// announcement selected)
		announcementListView.getSelectionModel().selectedItemProperty().addListener((x, y, announcement) -> {
			inspectedAnnouncement = announcement;
			deleteAnnouncementButton.setDisable(announcement == null);
			modifyAnnouncementButton.setDisable(announcement == null);
		});

		// if buttons are shown, these are the event listeners
		addAnnouncementButton.setOnAction((e) -> handleCreate());
		deleteAnnouncementButton.setOnAction((e) -> handleDelete());
		modifyAnnouncementButton.setOnAction((e) -> handleEdit());
	}

	public void update() {
		fillList();
		announcementListView.refresh();
	}

	public void showOverview() {
		GuiUtil.bindAnchorPane(announcementOverview, announcementTabRoot);
	}

	private void fillList() {
		if (inspectedSession == null)
			return;
		Set<Announcement> announcements = inspectedSession.getAnnouncements();
		List<Announcement> announcementsList = new ArrayList<>();
		announcementsList.addAll(announcements);
		Collections.sort(announcementsList);
		Collections.reverse(announcementsList);
		announcementListView.setItems(FXCollections.observableArrayList(announcementsList));
	}

	private void handleCreate() {
		NewAnnouncementController newAnnouncementController = new NewAnnouncementController();
		AnchorPane newAnnouncement = loadFXML("sessions/tabs/EditOrCreateAnnouncement.fxml", newAnnouncementController,
				getFacade());
		GuiUtil.bindAnchorPane(newAnnouncement, announcementTabRoot);
	}

	private void handleDelete() {
		Alert alert = Alerts.confirmationAlert("Aankondiging verwijderen", String.format(
				"Ben je zeker dat je de aankondiging \"%s\" wilt verwijderen?", inspectedAnnouncement.getTitle()));
		if (alert.showAndWait().get() == ButtonType.OK) {
			alert.close();
			try {
				((SessionFacade) getFacade()).removeAnnouncement(inspectedAnnouncement, inspectedSession);
				update();
			} catch (UserNotAuthorizedException e) {
				Alerts.errorAlert("Aankondiging verwijderen",
						"Je hebt niet de juiste machtigingen om deze aankondiging te verwijderen.");
			}
		}
	}

	private void handleEdit() {
		EditAnnouncementController editAnnouncementController = new EditAnnouncementController();
		AnchorPane editAnnouncement = loadFXML("sessions/tabs/EditOrCreateAnnouncement.fxml",
				editAnnouncementController, getFacade());

		GuiUtil.bindAnchorPane(editAnnouncement, announcementTabRoot);
	}

	private void updateButtonPermissions(Session s) {
		// If inspected session changes, recheck if logged in member can manipulate it
		boolean visible = LoggedInMemberManager.getInstance().loggedInMemberCanManipulateSession(s);
		addAnnouncementButton.setDisable(!visible);
		modifyAnnouncementButton.setVisible(visible);
		deleteAnnouncementButton.setVisible(visible);
	}

	/*
	 * Getters and setters
	 */
	public void setInspectedSession(Session session) {
		if (session == null)
			return;
		inspectedSession = session;

		updateButtonPermissions(session);
		update();
	}

	public Session getInspectedSession() {
		return inspectedSession;
	}

	public Announcement getInspectedAnnouncement() {
		return inspectedAnnouncement;
	}

	public void setInspectedAnnouncement(Announcement inspectedAnnouncement) {
		this.inspectedAnnouncement = inspectedAnnouncement;
	}

}
