package main.services;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class GuiUtil {

	public static void setAnchorsZero(Pane pane) {
		AnchorPane.setTopAnchor(pane, 0.0);
		AnchorPane.setRightAnchor(pane, 0.0);
		AnchorPane.setBottomAnchor(pane, 0.0);
		AnchorPane.setLeftAnchor(pane, 0.0);
	}

}
