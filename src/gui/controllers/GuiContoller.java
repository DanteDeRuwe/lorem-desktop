package gui.controllers;

import main.domain.facades.Facade;

public abstract class GuiContoller {

	private Facade facade;

	public Facade getFacade() {
		return facade;
	}

	public void setFacade(Facade facade) {
		this.facade = facade;
	}

	public void injectFacade(Facade facade) {
		this.facade = facade;
	}
}
