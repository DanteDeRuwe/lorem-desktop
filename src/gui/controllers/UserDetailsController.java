package gui.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.TextBoundsType;
import main.domain.Member;
import main.domain.Session;
import main.services.Util;

public class UserDetailsController extends GuiController {
	
	private Member inspectedUser;
	
    @FXML private Label nameLabel;
    @FXML private Label membertypeLabel;
    @FXML private Label usernameLabel;
    @FXML private JFXButton editUserButton;
    @FXML private JFXButton deleteUserButton;
    
    @FXML
	public void initialize() {

		
	}
    
    public void setInspectedUser(Member member) {
		inspectedUser = member;
		updateLabels();
	}
    
    private void updateLabels() {
    	if (inspectedUser == null)
			return;
    	
    	nameLabel.setText(inspectedUser.getFullName());
    	membertypeLabel.setText(inspectedUser.getMemberType().toString());
    	usernameLabel.setText(inspectedUser.getUsername());
    }
}
