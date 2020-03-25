package main.domain.facades;

import main.domain.Member;
import persistence.MemberDaoJpa;

public class LoggedInMemberManager {

	private Member loggedInMember;
	
	public LoggedInMemberManager() {
		MemberDaoJpa repo  = new MemberDaoJpa();
		loggedInMember = repo.getMemberByUsername("harm.de.weirdt"); // TODO Hardcoded for now
	}
	
	public Member getLoggedInMember() {
		return loggedInMember;
	}
	
	public void setLoggedInMember(Member loggedInMember) {
		this.loggedInMember = loggedInMember;
	}
	
}
