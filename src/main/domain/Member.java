package main.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
		@NamedQuery(name = "Member.findByUsername", query = "select m from Member m where m.username = :username") })
public class Member {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long member_id;
	private String username;
	private String firstName;
	private String lastName;

	@OneToMany(mappedBy = "organizer") private Set<Session> sessions;

	private MemberType memberType;
	
	private MemberStatus memberStatus;

	public Member() {
	};

	public Member(String username, String firstName, String lastName, MemberType memberType, MemberStatus memberStatus) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.setMemberType(memberType);
		this.memberStatus = memberStatus;

		sessions = new HashSet<Session>();
	}
	
	public Member(String username, String firstName, String lastName, MemberType memberType) {
		this(username, firstName, lastName, memberType, MemberStatus.ACTIVE);
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

	public MemberStatus getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(MemberStatus memberStatus) {
		this.memberStatus = memberStatus;
	}

	public MemberType getMemberType() {
		return memberType;
	}

	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}

}
