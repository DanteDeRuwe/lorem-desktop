package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.domain.Session;

public class MainController {

	@FXML
	private AnchorPane main;
	
	@FXML
	private AnchorPane leftPane;
	
	@FXML
	private AnchorPane centerPane;
	
	@FXML
	private AnchorPane rightPane;

	@FXML
	private SessionController sessionController;

	@FXML
	public void initialize() {
		//TODO: set default panes
	}

}