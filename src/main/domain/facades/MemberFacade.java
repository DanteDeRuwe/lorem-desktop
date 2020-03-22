package main.domain.facades;

import java.util.List;

import main.domain.Member;
import main.domain.Session;
import persistence.GenericDaoJpa;
import persistence.MemberDao;
import persistence.MemberDaoJpa;

public class MemberFacade implements Facade {

	private Member loggedInMember;
	private MemberDao memberRepo;

	public MemberFacade() {
		setMemberRepo(new MemberDaoJpa());
		loggedInMember = getMemberByUsername("harm.de.weirdt"); // TODO Hardcoded for now
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

	public Member getLoggedInMember() {
		return loggedInMember;
	}

	public void setLoggedInMember(Member loggedInMember) {
		this.loggedInMember = loggedInMember;
	}

	public List<Member> getAllMembers() {
		return memberRepo.findAll();
	}

	public void addMember(Member member) {
		GenericDaoJpa.startTransaction();
		memberRepo.insert(member);
		GenericDaoJpa.commitTransaction();
	}
	
	public void editMember(Member member, Member newMember) {
		// delete the old session from the runtime calendar
		deleteUser(member);

		// update the user
		member.setFirstName(newMember.getFirstName());
		member.setLastName(newMember.getLastName());
		member.setUsername(newMember.getUsername());
		member.setMemberType(newMember.getMemberType());
		member.setMemberStatus(newMember.getMemberStatus());
		member.setProfilePicPath(newMember.getProfilePicPath());
		
		// add it again with updated info
		addMember(member);

		// persist
		GenericDaoJpa.startTransaction();
		memberRepo.update(member);
		GenericDaoJpa.commitTransaction();
	}

	public void deleteUser(Member member) {
		if (member.equals(loggedInMember))
			throw new IllegalArgumentException();

		GenericDaoJpa.startTransaction();
		memberRepo.delete(member);
		GenericDaoJpa.commitTransaction();
	}

}
