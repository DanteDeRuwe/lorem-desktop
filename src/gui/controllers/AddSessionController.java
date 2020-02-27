package gui.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.domain.Session;
import main.services.DummySessionProvider;

public class AddSessionController implements Initializable{
	
	@FXML
	private VBox addSession;
	
	@FXML
	private TextField organizerField;
	
	@FXML
	private TextField titleField;
	
	@FXML
	private TextField speakerField;
	
	@FXML
	private Button addBtn;
	
    @FXML
    private DatePicker startDatePicker;
    
    @FXML
    private ComboBox<Integer> startTimePicker;
    
    @FXML
    private DatePicker endDatePicker;
    
    @FXML
    private ComboBox<Integer> endTimePicker;
    
    @FXML
    private TextField locationField;
    
    private SessionController sessionController;
    
	

	public AddSessionController() {
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ObservableList<Integer> startTimes = FXCollections.observableArrayList();
		for (int i = 0; i < 24; i++) {
			startTimes.add(i);
		}
		
		this.startTimePicker.setItems(startTimes);
		
		ObservableList<Integer> endTimes = FXCollections.observableArrayList();
		for (int i = 0; i < 24; i++) {
			endTimes.add(i);
		}
		
		this.endTimePicker.setItems(startTimes);
		
		addBtn.setOnMouseClicked(x -> {
			this.addSession();
		});
	
	}
	
	private void addSession() {
		String organizer = organizerField.getText();
		String title = titleField.getText();
		String speaker = speakerField.getText();
		String loc = locationField.getText();
		LocalDateTime start;
		LocalDateTime end;
		
		start = LocalDateTime.of(startDatePicker.getValue(), LocalTime.of((int)startTimePicker.getValue(), 0));
		end = LocalDateTime.of(endDatePicker.getValue(), LocalTime.of((int)endTimePicker.getValue(), 0));
		
		this.sessionController.addSession(organizer, title, speaker, start, end, loc);
		this.sessionController.refreshSessions();
	}

	public void setSessionController(SessionController sessionController) {
	    this.sessionController = sessionController;
	}
}
