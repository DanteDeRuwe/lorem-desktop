package main.domain.facades;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import main.domain.Member;
import main.domain.MemberStatus;
import main.domain.MemberType;
import main.domain.Session;
import main.exceptions.InvalidMemberException;
import main.exceptions.UserNotAuthorizedException;
import persistence.GenericDaoJpa;
import persistence.MemberDao;
import persistence.MemberDaoJpa;

public class MemberFacade implements Facade {

	private MemberDao memberRepo;
	private LoggedInMemberManager loggedInMemberManager;

	public MemberFacade(LoggedInMemberManager loggedInMemberManager) {
		setMemberRepo(new MemberDaoJpa());
		this.loggedInMemberManager = loggedInMemberManager;
	}

	public Member getMemberByUsername(String username) {
		return memberRepo.getMemberByUsername(username);
	}

	/*
	 * Getters and setters
	 */

	public void setMemberRepo(MemberDao memberRepo) {
		this.memberRepo = memberRepo;
	}

	public MemberDao getMemberRepo() {
		return this.memberRepo;
	}

	public List<Member> getAllMembers() {
		return memberRepo.findAll();
	}

	public Member createMemberFromFields(String username, String firstName, String lastName, MemberType type,
			MemberStatus status, String profilePicPath) throws InvalidMemberException {
		if (usernameExists(username)) {
			throw new InvalidMemberException("gebruikersnaam bestaat al");
		} else {
			return new Member(username, firstName, lastName, type, status, profilePicPath);
		}
	}

	public Member createMemberFromFields(String username, String firstName, String lastName, MemberType type,
			MemberStatus status, String profilePicPath, Member userBeingModified) throws InvalidMemberException {
		if (username.equals(userBeingModified.getUsername())) {
			return new Member(username, firstName, lastName, type, status, profilePicPath);
		} else {
			return createMemberFromFields(username, firstName, lastName, type, status, profilePicPath);
		}
	}

	public boolean usernameExists(String username) {
		try {
			memberRepo.getMemberByUsername(username);
			return true;
		} catch (EntityNotFoundException e) {
			return false;
		}
	}

	public void addMember(Member member) throws UserNotAuthorizedException {
		// check if user is authorized
		if (loggedInMemberManager.getLoggedInMember().getMemberType() != MemberType.HEADADMIN)
			throw new UserNotAuthorizedException();

		GenericDaoJpa.startTransaction();
		memberRepo.insert(member);
		GenericDaoJpa.commitTransaction();
	}

	public void editMember(Member member, Member newMember, String password) throws UserNotAuthorizedException {
		// check if user is authorized
		if (loggedInMemberManager.getLoggedInMember().getMemberType() != MemberType.HEADADMIN)
			throw new UserNotAuthorizedException();

		// update the user
		member.setFirstName(newMember.getFirstName());
		member.setLastName(newMember.getLastName());
		member.setUsername(newMember.getUsername());
		member.setMemberType(newMember.getMemberType());
		member.setMemberStatus(newMember.getMemberStatus());
		member.setProfilePicPath(newMember.getProfilePicPath());
		if (password != null && !password.isBlank()) member.setPassword(password); // if password field was left empty, leave it as is.

		// persist
		GenericDaoJpa.startTransaction();
		memberRepo.update(member);
		GenericDaoJpa.commitTransaction();
	}

	public void deleteUser(Member member) throws UserNotAuthorizedException {
		// check if user is authorized
		if (loggedInMemberManager.getLoggedInMember().getMemberType() != MemberType.HEADADMIN)
			throw new UserNotAuthorizedException();

		// make sure the user is not deleting themselves
		if (member.equals(loggedInMemberManager.getLoggedInMember()))
			throw new IllegalArgumentException();

		GenericDaoJpa.startTransaction();
		memberRepo.delete(member);
		GenericDaoJpa.commitTransaction();
	}

}
