package main.services;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import main.domain.Announcement;

public class AnnouncementCellFactory<T> implements Callback<ListView<Announcement>, ListCell<Announcement>> {

	@Override
	public ListCell<Announcement> call(ListView<Announcement> listView) {
		return new ListCell<Announcement>() {
			@Override
			protected void updateItem(Announcement item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setGraphic(null);
					setText(null);
					return;
				}
				// create components with announcement data
				Label titleLabel = new Label(item.getTitle());
				titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");
				Label timestampLabel = new Label(item.getTimestamp().format(Util.DATETIMEFORMATTER));
				Label authorLabel = new Label(item.getAuthor().getFullName());
				Text text = new Text(item.getText());

				// add components to containers
				VBox authorAndTimestamp = new VBox();
				authorAndTimestamp.setStyle("-fx-font-size: 13px;");
				authorAndTimestamp.setPadding(new Insets(0, 0, 4, 0));
				authorAndTimestamp.getChildren().addAll(authorLabel, timestampLabel);
				VBox vbox = new VBox();
				vbox.getChildren().addAll(titleLabel, authorAndTimestamp);
				if (!text.getText().isBlank())
					vbox.getChildren().add(text);
				setGraphic(vbox);

				// bind the content widths to listview width
				text.wrappingWidthProperty().bind(listView.widthProperty().subtract(30d));
			}

		};
	}

}
