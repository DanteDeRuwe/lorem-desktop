package main.services;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class GuiUtil {

	public static void setAnchorsZero(Pane pane) {
		AnchorPane.setTopAnchor(pane, 0.0);
		AnchorPane.setRightAnchor(pane, 0.0);
		AnchorPane.setBottomAnchor(pane, 0.0);
		AnchorPane.setLeftAnchor(pane, 0.0);
	}

	public static void bindAnchorPane(AnchorPane child, AnchorPane parent) {
		setAnchorsZero(child);
		parent.getChildren().clear();
		parent.getChildren().add(child);
	}

	public static <T> void fillColumn(TableColumn<T, String> col, String propname, int minWidth, int maxWidth) {
		col.setCellValueFactory(new PropertyValueFactory<>(propname));
		col.setMinWidth(minWidth);
		col.setMaxWidth(maxWidth);
		col.setResizable(true);
	}
}
