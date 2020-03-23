open module javag09 {
	exports main.domain;
	exports main.domain.facades;
	exports test.domain;
	exports main.services;
	exports gui.controllers;
	exports main;

	requires transitive javafx.fxml;
	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.graphics;
	requires transitive com.jfoenix;

	requires java.persistence;
	requires java.instrument;
	requires java.sql;
	requires org.junit.jupiter.api;
	requires mockito.junit.jupiter;
	requires org.mockito;
	requires org.junit.jupiter.params;
	requires java.desktop;


}