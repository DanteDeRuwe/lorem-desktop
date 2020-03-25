package main.domain.facades;

import main.domain.Member;
import main.domain.MemberStatus;
import main.domain.MemberType;
import main.domain.Session;
import main.exceptions.UserNotAuthorizedException;

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
	 * @return true if the user has the correct status and type
	 * @throws UserNotAuthorizedException if this is not the case
	 */
	boolean loggedInMemberCanManipulateData() throws UserNotAuthorizedException {
		// get logged in member with type and status
		Member loggedInMember = getLoggedInMember();
		MemberType memberType = loggedInMember.getMemberType();
		MemberStatus memberStatus = loggedInMember.getMemberStatus();

		// check
		if ((memberType != MemberType.ADMIN && memberType != MemberType.HEADADMIN)
				|| memberStatus != MemberStatus.ACTIVE)
			throw new UserNotAuthorizedException();
		else
			return true;
	}

	/**
	 * check if a logged in member can manipulate a session. This means he can
	 * manipulate data + if he is an admin, he owns the session.
	 * 
	 * @param session
	 * @return true if the logged in member can manipulate the given session
	 * @throws UserNotAuthorizedException if it doesn't
	 */
	boolean loggedInMemberCanManipulateSession(Session session) throws UserNotAuthorizedException {

		Member loggedInMember = getLoggedInMember();
		MemberType memberType = loggedInMember.getMemberType();

		if (!loggedInMemberCanManipulateData())
			throw new UserNotAuthorizedException();

		if (memberType == MemberType.ADMIN && !session.getFullOrganizerName().equals(loggedInMember.getFullName()))
			throw new UserNotAuthorizedException();

		return true;
	}

}