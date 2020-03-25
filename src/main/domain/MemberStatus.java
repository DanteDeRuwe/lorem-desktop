package main.domain;

public enum MemberStatus {
	ACTIVE {
		@Override
		public String toString() {
			return "Actief";
		}
	},
	
	INACTIVE {
		@Override
		public String toString() {
			return "Inactief";
		}
	},
	
	BLOCKED {
		@Override
		public String toString() {
			return "Geblokkeerd";
		}
	}
	
}