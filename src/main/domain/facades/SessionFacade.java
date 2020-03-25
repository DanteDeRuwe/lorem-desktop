package main.domain.facades;

import main.domain.Announcement;
import main.domain.Member;
import main.domain.MemberType;
import main.domain.Session;
import main.exceptions.UserNotAuthorizedException;
import persistence.GenericDaoJpa;

public class SessionFacade implements Facade {

	private GenericDaoJpa<Announcement> announcementRepo;

	private LoggedInMemberManager loggedInMemberManager;

	public SessionFacade(LoggedInMemberManager loggedInMemberManager) {
		announcementRepo = new GenericDaoJpa<Announcement>(Announcement.class);
		this.loggedInMemberManager = loggedInMemberManager;
	}

	public void addAnnouncement(Announcement a, Session s) throws UserNotAuthorizedException {
		MemberType memberType = loggedInMemberManager.getLoggedInMember().getMemberType();
		if (memberType != MemberType.HEADADMIN && memberType != MemberType.ADMIN)
			throw new UserNotAuthorizedException();

		// user is an admin but session to add announcement to isn't organized by this
		// admin
		if (memberType == MemberType.ADMIN
				&& !s.getFullOrganizerName().equals(loggedInMemberManager.getLoggedInMember().getFullName()))
			throw new UserNotAuthorizedException();

		s.addAnnouncement(a);

		GenericDaoJpa.startTransaction();
		announcementRepo.insert(a);
		GenericDaoJpa.commitTransaction();
	}

	public Announcement createAnnouncementFromFields(Member author, String text, String title) {
		return new Announcement(author, text, title);
	}

	public void removeAnnouncement(Announcement announcement, Session session) throws UserNotAuthorizedException {
		MemberType memberType = loggedInMemberManager.getLoggedInMember().getMemberType();
		if (memberType != MemberType.HEADADMIN && memberType != MemberType.ADMIN)
			throw new UserNotAuthorizedException();

		// user is an admin but session to remove announcement from isn't organized by
		// this admin
		if (memberType == MemberType.ADMIN
				&& !session.getFullOrganizerName().equals(loggedInMemberManager.getLoggedInMember().getFullName()))
			throw new UserNotAuthorizedException();

		session.removeAnnouncement(announcement);

		GenericDaoJpa.startTransaction();
		announcementRepo.delete(announcement);
		GenericDaoJpa.commitTransaction();
	}

	public void editAnnouncement(Announcement announcement, Announcement template, Session s)
			throws UserNotAuthorizedException {
		MemberType memberType = loggedInMemberManager.getLoggedInMember().getMemberType();
		if (memberType != MemberType.HEADADMIN && memberType != MemberType.ADMIN)
			throw new UserNotAuthorizedException();

		// user is an admin but session where editing announcement isn't organized by
		// this admin
		if (memberType == MemberType.ADMIN
				&& !s.getFullOrganizerName().equals(loggedInMemberManager.getLoggedInMember().getFullName()))
			throw new UserNotAuthorizedException();

		announcement.setTitle(template.getTitle());
		announcement.setText(template.getText());

		GenericDaoJpa.startTransaction();
		announcementRepo.update(announcement);
		GenericDaoJpa.commitTransaction();
	}

}
