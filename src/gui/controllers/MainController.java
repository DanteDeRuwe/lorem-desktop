package gui.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

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
		try {
			Node leftPaneInner = FXMLLoader
					.<AnchorPane>load(getClass().getResource("/resources/fxml/sessions/SessionSideBar.fxml"));

			leftPane.getChildren().setAll(leftPaneInner);
			AnchorPane.setBottomAnchor(leftPaneInner, 0.0d);
			AnchorPane.setTopAnchor(leftPaneInner, 0.0d);
			AnchorPane.setLeftAnchor(leftPaneInner, 0.0d);
			AnchorPane.setRightAnchor(leftPaneInner, 0.0d);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}