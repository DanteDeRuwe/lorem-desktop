package main.domain;

public enum SessionStatus {
	CREATED {
		@Override
		public String toString() {
			return "Aangemaakt";
		}
	},

	OPEN {
		@Override
		public String toString() {
			return "Open";
		}
	},

	CLOSED {
		@Override
		public String toString() {
			return "Gesloten";
		}
	},

	FINISHED {
		@Override
		public String toString() {
			return "Afgelopen";
		}
	}
}
