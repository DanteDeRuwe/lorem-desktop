package gui.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.domain.facades.Facade;

public abstract class GuiController {

	private Facade facade;
	private GuiController parentController;

	public Facade getFacade() {
		return facade;
	}

	public GuiController getParentController() {
		return parentController;
	}

	// Injections
	// return this after injecting to make chainable
	public GuiController injectParentController(GuiController controller) {
		this.parentController = controller;
		return this;
	}

	public GuiController injectFacade(Facade facade) {
		this.facade = facade;
		return this;
	}

	public AnchorPane loadFXML(String relativeFXMLPath, GuiController controllerToAppoint, Facade facadeToAppoint) {
		try {
			// The FXML that will be loaded will be a child of "this"
			controllerToAppoint.injectParentController(this).injectFacade(facadeToAppoint);

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

}
