package main.services;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class PropertyValueFactoryWrapperCellFactory<T> implements Callback<ListView<T>, ListCell<T>> {

	private final PropertyValueFactory<T, String> pvf;

	public PropertyValueFactoryWrapperCellFactory(String propertyName) {
		super();
		pvf = new PropertyValueFactory<>(propertyName);
	}

	@Override
	public ListCell<T> call(ListView<T> param) {
		return new ListCell<T>() {
			@Override
			public void updateItem(T item, boolean empty) {
				super.updateItem(item, empty);
				textProperty().unbind();
				if (item == null) {
					return;
				}
				TableColumn.CellDataFeatures<T, String> cdf = new TableColumn.CellDataFeatures<>(null, null, item);
				textProperty().bind(pvf.call(cdf));
			}
		};
	}

}