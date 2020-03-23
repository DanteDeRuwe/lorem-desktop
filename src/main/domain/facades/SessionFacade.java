package main.domain.facades;

import main.domain.Announcement;
import main.domain.Member;
import main.domain.Session;
import persistence.GenericDaoJpa;
import persistence.SessionDaoJpa;

public class SessionFacade implements Facade {

	SessionDaoJpa sessionRepo = new SessionDaoJpa();
	GenericDaoJpa<Announcement> announcementRepo = new GenericDaoJpa<Announcement>(Announcement.class);

	public void addAnnouncement(Announcement a, Session s) {

		s.addAnnouncement(a);

		GenericDaoJpa.startTransaction();
		announcementRepo.insert(a);
		GenericDaoJpa.commitTransaction();
	}

	public Announcement createAnnouncementFromFields(Member author, String text, String title) {
		return new Announcement(author, text, title);
	}

	public void removeAnnouncement(Announcement announcement, Session session) {
		session.removeAnnouncement(announcement);

		GenericDaoJpa.startTransaction();
		announcementRepo.delete(announcement);
		GenericDaoJpa.commitTransaction();
	}

}
