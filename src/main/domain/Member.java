package main.domain;

import java.io.Serializable;

public class Member implements Serializable{

	private String username;
	private String firstName;
	private String lastName;

	private MemberType memberType;

	public Member(String username, String firstName, String lastName, MemberType memberType) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.memberType = memberType;
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

	public String getFullName() {
		return firstName + " " + lastName;
	}

}
