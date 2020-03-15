package main.domain;

public enum MemberType {
	USER {
		public String toString() {
			return "Gebruiker";
		}
	},
	
	ADMIN {
		public String toString() {
			return "Admin";
		}
	},
	
	HEADADMIN {
		public String toString() {
			return "Hoofd Admin";
		}
	}
}
