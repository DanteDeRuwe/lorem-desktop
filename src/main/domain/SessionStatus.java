package main.domain;

public enum SessionStatus {
	OPEN {
		public String toString() {
			return "Open";
		}
	},
	
	CLOSED {
		public String toString() {
			return "Gesloten";
		}
	}
}
