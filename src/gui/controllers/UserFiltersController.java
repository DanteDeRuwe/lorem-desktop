package gui.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

public class UserFiltersController extends GuiController{

    @FXML
    private JFXButton newUserButton;
    
    @FXML
	public void initialize() {

		// Event Listeners
		newUserButton.setOnAction(e -> handleNewUser());

		// TODO filters
		// titleFilter.textProperty().addListener((obs, oldText, newText) -> { });

	}
    
    private void handleNewUser() {
		((UserSceneController) getParentController()).displayOnRightPane("NewUser");
	}

}