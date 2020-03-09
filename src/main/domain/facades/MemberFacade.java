package main.domain.facades;

import main.domain.Member;
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

}
