package main.services;

public class GuiUtil {

	/**
	 * Helper method to put an AnchorPane in a pane
	 * 
	 * @param fxmlFile : the file path starting from the
	 *                 <code>resources/fxml/</code> folder
	 * @param parent   : the parent
	 * @param facade   : the facade to inject in the child
	 */

	/*
	 * public static void bindAnchorPane(String fxmlFile, Pane parent, Facade
	 * facade) { try {
	 * 
	 * FXMLLoader loader = new
	 * FXMLLoader(MainController.class.getResource("/resources/fxml/" + fxmlFile));
	 * 
	 * AnchorPane child = (AnchorPane) loader.load();
	 * 
	 * loader.<GuiContoller>getController().injectFacade(facade);
	 * 
	 * parent.getChildren().setAll(child); AnchorPane.setBottomAnchor(child, 0.0d);
	 * AnchorPane.setTopAnchor(child, 0.0d); AnchorPane.setLeftAnchor(child, 0.0d);
	 * AnchorPane.setRightAnchor(child, 0.0d);
	 * 
	 * } catch (IOException e) { e.printStackTrace();
	 * 
	 * } }
	 */

}
