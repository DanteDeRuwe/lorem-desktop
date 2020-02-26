module javag09 {
	requires javafx.base;
	requires javafx.controls;
	requires transitive javafx.graphics;
	requires javafx.fxml;

	requires java.persistence;
	requires java.instrument;
	requires java.sql;
	requires org.junit.jupiter.api;
	requires mockito.junit.jupiter;
	requires org.mockito;
	requires org.junit.jupiter.params;

	opens main.controllers to javafx.fxml;
	opens main.domain to javafx.base;

	exports main;
}