package main.domain.facades;

import main.domain.Member;
import main.domain.MemberStatus;
import main.domain.MemberType;
import main.domain.Session;

public class LoggedInMemberManager {

	private static LoggedInMemberManager manager;
	private Member loggedInMember;

	// private constructor to force use of
	// getInstance() to create Singleton object
	private LoggedInMemberManager() {
	}

	public static LoggedInMemberManager getInstance() {
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

	/**
	 * Checks if a user can manipulated data: this is when he is an active admin or
	 * headadmin
	 * 
	 * @return whether the user has the correct status and type
	 */
	public boolean loggedInMemberCanManipulateData() {
		// get logged in member with type and status
		Member loggedInMember = getLoggedInMember();
		MemberType memberType = loggedInMember.getMemberType();
		MemberStatus memberStatus = loggedInMember.getMemberStatus();

		// check
		return ((memberType == MemberType.ADMIN || memberType == MemberType.HEADADMIN)
				&& memberStatus == MemberStatus.ACTIVE);

	}

	/**
	 * check if a logged in member can manipulate a session. This means he can
	 * manipulate data (active admins and headadmins) + if he is an admin, he owns
	 * the session.
	 * 
	 * @param session
	 * @return whether the logged in member can manipulate the given session
	 */
	public boolean loggedInMemberCanManipulateSession(Session session) {

		Member loggedInMember = getLoggedInMember();
		MemberType memberType = loggedInMember.getMemberType();

		if (!loggedInMemberCanManipulateData())
			return false;

		if (memberType == MemberType.ADMIN && session.getOrganizer() != loggedInMember)
			return false;

		return true;
	}

	public boolean loggedInMemberCanManipulateUsers() {
		// get logged in member with type and status
		Member loggedInMember = getLoggedInMember();
		MemberType memberType = loggedInMember.getMemberType();
		MemberStatus memberStatus = loggedInMember.getMemberStatus();

		// only active headadmins can manipulate users
		return (memberType == MemberType.HEADADMIN && memberStatus == MemberStatus.ACTIVE);
	}

}