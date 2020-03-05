open module javag09 {
	exports main.domain;
	exports test.domain;
	exports main.services;
	exports gui.controllers;
	exports main;

	requires javafx.fxml;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires com.jfoenix;

	requires java.persistence;
	requires java.instrument;
	requires java.sql;
	requires org.junit.jupiter.api;
	requires mockito.junit.jupiter;
	requires org.mockito;
	requires org.junit.jupiter.params;

}