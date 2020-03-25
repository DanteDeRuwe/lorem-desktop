package gui.controllers;

import java.util.Optional;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import main.domain.Announcement;
import main.domain.Session;
import main.domain.facades.SessionFacade;
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

		// Event listener for selected item (also enables/disables buttons
		announcementListView.getSelectionModel().selectedItemProperty().addListener((x, y, announcement) -> {
			inspectedAnnouncement = announcement;
			deleteAnnouncementButton.setDisable(announcement == null);
			modifyAnnouncementButton.setDisable(announcement == null);
		});

		addAnnouncementButton.setOnAction((e) -> handleCreate());
		deleteAnnouncementButton.setOnAction((e) -> handleDelete());
		modifyAnnouncementButton.setOnAction((e) -> handleEdit());
	}

	public void update() {
		fillList();
		announcementListView.refresh();
	}

	private void fillList() {
		if (inspectedSession == null)
			return;
		Set<Announcement> announcements = inspectedSession.getAnnouncements();
		announcementListView.setItems(FXCollections.observableArrayList(announcements));
	}

	private void handleCreate() {
		NewAnnouncementController newAnnouncementController = new NewAnnouncementController();
		AnchorPane newAnnouncement = loadFXML("sessions/tabs/EditOrCreateAnnouncement.fxml", newAnnouncementController,
				getFacade());
		GuiUtil.bindAnchorPane(newAnnouncement, announcementTabRoot);
	}

	public void showOverview() {
		GuiUtil.bindAnchorPane(announcementOverview, announcementTabRoot);
	}

	private void handleDelete() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Aankondiging verwijderen");
		alert.setHeaderText("Waarschuwing");
		alert.setContentText(String.format("Ben je zeker dat je de aankondiging \"%s\" wilt verwijderen?",
				inspectedAnnouncement.getTitle()));
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			((SessionFacade) getFacade()).removeAnnouncement(inspectedAnnouncement, inspectedSession);
			update();
		}
	}

	private void handleEdit() {
		EditAnnouncementController editAnnouncementController = new EditAnnouncementController();
		AnchorPane editAnnouncement = loadFXML("sessions/tabs/EditOrCreateAnnouncement.fxml",
				editAnnouncementController, getFacade());

		GuiUtil.bindAnchorPane(editAnnouncement, announcementTabRoot);
	}

	public void setInspectedSession(Session session) {
		inspectedSession = session;
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
