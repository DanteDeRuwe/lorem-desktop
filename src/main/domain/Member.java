package main.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Member{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long member_id;
	private String username;
	private String firstName;
	private String lastName;
	
	@OneToMany(
			mappedBy = "organizer")
	private Set<Session> sessions;

	private MemberType memberType;
	
	public Member() { };

	public Member(String username, String firstName, String lastName, MemberType memberType) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.memberType = memberType;
		
		sessions = new HashSet<Session>();
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
	
	public void addSession(Session session) {
		sessions.add(session);
	}

}
