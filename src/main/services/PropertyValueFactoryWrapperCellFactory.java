package main.services;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;

public class PropertyValueFactoryWrapperCellFactory<T> implements Callback<ListView<T>, ListCell<T>> {

	private final PropertyValueFactory<T, String> pvf;
	private final Runnable handleDoubleClick;

	/**
	 * Provides an easy way to make a propertyvaluefactory for a listview
	 * 
	 * @param propertyName      the JavaFX property name
	 * @param handleDoubleClick a lambda or Runnable that is called when
	 *                          doubleclicking a list item
	 */
	public PropertyValueFactoryWrapperCellFactory(String propertyName, Runnable handleDoubleClick) {
		super();
		pvf = new PropertyValueFactory<>(propertyName);
		this.handleDoubleClick = handleDoubleClick;
	}

	public PropertyValueFactoryWrapperCellFactory(String propertyName) {
		this(propertyName, () -> {}); // if no doubleclick action is specified, jsut do nothing ¯\_(^^)_/¯
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

				setOnMouseClicked(mouseClickedEvent -> {
					if (mouseClickedEvent.getButton().equals(MouseButton.PRIMARY)
							&& mouseClickedEvent.getClickCount() == 2) {
						handleDoubleClick.run();
					}
				});

				TableColumn.CellDataFeatures<T, String> cdf = new TableColumn.CellDataFeatures<>(null, null, item);
				textProperty().bind(pvf.call(cdf));
			}
		};
	}

}