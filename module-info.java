open module JpaEnJavaFx {
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	
	requires java.persistence;
	requires java.instrument;
	requires java.sql;
	requires org.junit.jupiter.api;
	requires mockito.junit.jupiter;
	requires org.mockito;
}