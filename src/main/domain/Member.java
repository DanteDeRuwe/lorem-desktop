package main.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	
	private String profilePicPath;
	
	@ManyToMany(mappedBy = "registrees")
	private Set<Session> registrations;
	
	@ManyToMany(mappedBy = "attendees")
	private Set<Session> attendances;

	public Member() {
	};

	public Member(String username, String firstName, String lastName, MemberType memberType, MemberStatus memberStatus, String profilePicPath) {
		setUsername(username);
		this.firstName = firstName;
		this.lastName = lastName;
		this.setMemberType(memberType);
		this.memberStatus = memberStatus;
		this.profilePicPath = profilePicPath;

		sessions = new HashSet<Session>();
		setRegistrations(new HashSet<Session>());
		setAttendances(new HashSet<Session>());
	}
	
	public Member(String username, String firstName, String lastName, MemberType memberType) {
		this(username, firstName, lastName, memberType, MemberStatus.ACTIVE);
	}
	
	public Member(String username, String firstName, String lastName, MemberType memberType, MemberStatus memberStatus) {
		this(username, firstName, lastName, memberType, memberStatus, "");
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

	public String getProfilePicPath() {
		return profilePicPath;
	}

	public void setProfilePicPath(String profilePicPath) {
		this.profilePicPath = profilePicPath;
	}

	public Set<Session> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(Set<Session> registrations) {
		this.registrations = registrations;
	}

	public Set<Session> getAttendances() {
		return attendances;
	}

	public void setAttendances(Set<Session> attendances) {
		this.attendances = attendances;
	}

	
	
	

}
