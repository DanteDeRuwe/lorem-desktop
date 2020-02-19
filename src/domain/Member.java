package domain;

public abstract class Member {

	private String username;
	private String firstName;
	private String lastName;
	
	
	public Member(String username, String firstName, String lastName) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String value) {
		this.username = value;
	}
	public String getFirstName() {
		return this.firstName;
	}
	public void setFirstName(String value) {
		this.firstName = value;
	}
	public String getLastName() {
		return this.lastName;
	}
	public void setLastName(String value) {
		this.lastName = value;
	}

}
