package main.domain.facades;

import main.domain.Member;
import persistence.MemberDaoJpa;

public class LoggedInMemberManager {

	private static LoggedInMemberManager manager;
	private Member loggedInMember;
	  
    // private constructor to force use of 
    // getInstance() to create Singleton object 
    private LoggedInMemberManager() {} 
  
    public static LoggedInMemberManager getInstance() 
    { 
        if (manager == null) 
        	manager = new LoggedInMemberManager(); 
        return manager; 
    }

	public Member getLoggedInMember() {
		return loggedInMember;
	}

	public void setLoggedInMember(Member loggedInMember) {
		this.loggedInMember = loggedInMember;
	}
	
}