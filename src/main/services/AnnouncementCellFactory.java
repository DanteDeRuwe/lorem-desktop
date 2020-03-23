package main.services;

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
	public ListCell<Announcement> call(ListView<Announcement> param) {
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
				Label timestampLabel = new Label(item.getTimestamp().format(Util.DATETIMEFORMATTER));
				Label authorLabel = new Label(item.getAuthor().getFullName());
				Text text = new Text(item.getText());

				// add components to containers
				HBox hbox = new HBox();
				hbox.getChildren().addAll(authorLabel, new Label(" om "), timestampLabel);
				VBox vbox = new VBox();
				vbox.getChildren().addAll(titleLabel, hbox, text);
				setGraphic(vbox);
			}

		};
	}

}
