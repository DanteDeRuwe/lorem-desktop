package main.domain;

public enum MemberType {
	USER {
		@Override
		public String toString() {
			return "Gebruiker";
		}
	},
	
	ADMIN {
		@Override
		public String toString() {
			return "Verantwoordelijke";
		}
	},
	
	HEADADMIN {
		@Override
		public String toString() {
			return "Hoofdverantwoordelijke";
		}
	}
}
