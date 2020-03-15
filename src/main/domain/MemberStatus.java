package main.domain;

public enum MemberStatus {
	ACTIVE {
		public String toString() {
			return "Actief";
		}
	},
	
	INACTIVE {
		public String toString() {
			return "Inactief";
		}
	},
	
	BLOCKED {
		public String toString() {
			return "Geblokkeerd";
		}
	}
	
}