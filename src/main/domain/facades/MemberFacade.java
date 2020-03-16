package main.domain.facades;

import java.util.List;

import main.domain.Member;
import persistence.GenericDaoJpa;
import persistence.MemberDao;
import persistence.MemberDaoJpa;

public class MemberFacade implements Facade {

	private Member loggedInMember;
	private MemberDao memberRepo = new MemberDaoJpa();

	public MemberFacade() {
		loggedInMember = getMemberByUsername("freddy"); // TODO Hardcoded for now
	}

	public Member getMemberByUsername(String username) {
		return memberRepo.getMemberByUsername(username);
	}

	/*
	 * Getters and setters
	 */

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

	public void deleteUser(Member member) {
		if (member.equals(loggedInMember))
			throw new IllegalArgumentException();

		GenericDaoJpa.startTransaction();
		memberRepo.delete(member);
		GenericDaoJpa.commitTransaction();
	}

}
