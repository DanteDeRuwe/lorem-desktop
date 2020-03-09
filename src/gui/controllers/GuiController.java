package gui.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.domain.facades.Facade;

public abstract class GuiController {

	private Facade facade;
	private GuiController parentController;
	private MainController mainController;

	protected AnchorPane loadFXML(String relativeFXMLPath, GuiController controllerToAppoint, Facade facadeToAppoint) {
		try {

			// The FXML that will be loaded will be a child of "this"
			controllerToAppoint
					.injectParentController(this)
					.injectFacade(facadeToAppoint)
					.injectMainController(this.getMainController());

			// load the AnchorPane and set it's controller
			FXMLLoader loader = new FXMLLoader(GuiController.class.getResource("/resources/fxml/" + relativeFXMLPath));
			loader.setController(controllerToAppoint);
			return (AnchorPane) loader.load();

		} catch (IOException e) {
			System.err.println("FXML not loaded");
			e.printStackTrace();
			return new AnchorPane(new Label("Error loading..."));
		}
	}

	/*
	 * Injections return "this" after injecting to make chainable
	 */

	protected GuiController injectParentController(GuiController controller) {
		this.parentController = controller;
		return this;
	}

	protected GuiController injectFacade(Facade facade) {
		this.facade = facade;
		return this;
	}

	protected GuiController injectMainController(MainController mc) {
		this.mainController = mc;
		return this;
	}

	/*
	 * Getters
	 */

	protected Facade getFacade() {
		return facade;
	}

	protected GuiController getParentController() {
		return parentController;
	}

	protected MainController getMainController() {
		return mainController;
	}

}
