package main.domain.facades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import main.domain.Member;
import main.domain.MemberType;
import main.domain.Session;
import main.domain.SessionCalendar;
import main.exceptions.InvalidSessionCalendarException;
import main.exceptions.InvalidSessionException;
import main.exceptions.UserNotAuthorizedException;
import main.services.Util;
import persistence.GenericDaoJpa;
import persistence.SessionCalendarDaoJpa;
import persistence.SessionDaoJpa;

public class SessionCalendarFacade implements Facade {

	private SessionCalendarDaoJpa sessionCalendarRepo;
	private SessionDaoJpa sessionRepo;
	private SessionCalendar calendar;

	private LoggedInMemberManager loggedInMemberManager;

	public SessionCalendarFacade() {
		setSessionCalendarRepo(new SessionCalendarDaoJpa());
		setSessionRepo(new SessionDaoJpa());

		this.loggedInMemberManager = LoggedInMemberManager.getInstance();
	}

	/*
	 * -----------------------------------------------------------------------------
	 * Calendar
	 * -----------------------------------------------------------------------------
	 */

	public void setSessionCalendarRepo(SessionCalendarDaoJpa sessionCalendarRepo) {
		this.sessionCalendarRepo = sessionCalendarRepo;
	}

	public SessionCalendarDaoJpa getSessionCalendarRepo() {
		return this.sessionCalendarRepo;
	}

	public SessionCalendar createSessionCalendar(LocalDate start, LocalDate end)
			throws InvalidSessionCalendarException {
		try {
			return new SessionCalendar(start, end);
		} catch (IllegalArgumentException iae) {
			switch (iae.getMessage()) {
			case "startDate and endDate must not be null":
				throw new InvalidSessionCalendarException("Start- en eindmoment zijn niet correct ingegeven", iae);
			case "Cannot create calendar that far in the past":
				throw new InvalidSessionCalendarException("Startdatum kan niet zo ver in het verleden liggen", iae);
			case "Academic years must start and end in consecutive years":
				throw new InvalidSessionCalendarException("Een academiejaar bestaat uit 2 opeenvolgende jaartallen",
						iae);
			default:
				throw new InvalidSessionCalendarException(iae.getMessage(), iae);
			}
		}

	}

	public List<SessionCalendar> getAllSessionCalendars() {
		return sessionCalendarRepo.findAll();
	}

	public SessionCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(SessionCalendar calendar) {
		if (calendar == null) {
			throw new IllegalArgumentException("calendar can't be null");
		}
		this.calendar = calendar;
	}

	public String getAcademicYear() {
		return calendar.academicYearProperty().getValue();
	}

	public void addSessionCalendar(SessionCalendar calendar)
			throws UserNotAuthorizedException, InvalidSessionCalendarException {
		MemberType memberType = loggedInMemberManager.getLoggedInMember().getMemberType();
		if (memberType != MemberType.ADMIN && memberType != MemberType.HEADADMIN)
			throw new UserNotAuthorizedException();

		// check for overlap
		if (!calendar.doesNotOverlap(getAllSessionCalendars()))
			throw new InvalidSessionCalendarException(
					"De kalender mag niet overlappen met een reeds bestaande kalender");

		GenericDaoJpa.startTransaction();
		sessionCalendarRepo.insert(calendar);
		GenericDaoJpa.commitTransaction();
	}

	public void editSessionCalendar(SessionCalendar calendar, LocalDate startDate, LocalDate endDate)
			throws UserNotAuthorizedException, InvalidSessionCalendarException {
		MemberType memberType = loggedInMemberManager.getLoggedInMember().getMemberType();
		if (memberType != MemberType.ADMIN && memberType != MemberType.HEADADMIN)
			throw new UserNotAuthorizedException();

		// try to create a calendar, this has all exception logic
		SessionCalendar sc = createSessionCalendar(startDate, endDate);
		calendar.setStartDate(sc.getStartDate());
		calendar.setEndDate(sc.getEndDate());

		// check for overlap
		if (!calendar.doesNotOverlap(getAllSessionCalendars()))
			throw new InvalidSessionCalendarException(
					"De kalender mag niet overlappen met een reeds bestaande kalender");

		GenericDaoJpa.startTransaction();
		sessionCalendarRepo.update(calendar);
		GenericDaoJpa.commitTransaction();
	}

	/*
	 * -----------------------------------------------------------------------------
	 * SESSION
	 * -----------------------------------------------------------------------------
	 */

	public void setSessionRepo(SessionDaoJpa sessionRepo) {
		this.sessionRepo = sessionRepo;
	}

	public SessionDaoJpa getSessionRepo() {
		return this.sessionRepo;
	}

	public Session createSessionFromFields(Member organizer, String title, String description, String speakerName,
			LocalDate startDate, LocalTime startTime, String duration, String location, String capacity,
			String externalUrl, String type) throws InvalidSessionException {
		try {
			LocalDateTime start = LocalDateTime.of(startDate, startTime);
			LocalDateTime end = Util.calculateEnd(start, duration);

			// this will throw IllegalSessionException if session outside calendar
			calendar.sessionIsWithinRange(start, end);

			// construct a session
			return new Session(organizer, title, description, speakerName, start, end, location,
					Integer.parseInt(capacity), externalUrl, type);

		} catch (IllegalArgumentException iae) {
			switch (iae.getMessage()) {
			case "start in past":
				throw new InvalidSessionException("Startdatum ligt in het verleden", iae);
			case "start less than 1 day in future":
				throw new InvalidSessionException("Start moet minstens 1 dag in de toekomst liggen", iae);
			case "start and end do not meet minimum period requirement":
				throw new InvalidSessionException("Duurtijd is minstens 30 minuten", iae);
			case "duration has to be of format h:mm":
				throw new InvalidSessionException("Duurtijd is van het formaat u:mm", iae);
			default:
				throw new InvalidSessionException(iae.getMessage(), iae);
			}
		}
	}

	public void addSession(Session session) throws UserNotAuthorizedException {
		MemberType memberType = loggedInMemberManager.getLoggedInMember().getMemberType();
		if (memberType != MemberType.HEADADMIN && memberType != MemberType.ADMIN)
			throw new UserNotAuthorizedException();

		// add to calendar
		calendar.addSession(session);

		// persist
		GenericDaoJpa.startTransaction();
		sessionRepo.insert(session);
		GenericDaoJpa.commitTransaction();
	}

	public void editSession(Session session, Session newSession) throws UserNotAuthorizedException {
		MemberType memberType = loggedInMemberManager.getLoggedInMember().getMemberType();
		if (memberType != MemberType.HEADADMIN && memberType != MemberType.ADMIN)
			throw new UserNotAuthorizedException();

		// user is an admin but session to edit isn't organized by this admin
		if (memberType == MemberType.ADMIN
				&& !session.getFullOrganizerName().equals(loggedInMemberManager.getLoggedInMember().getFullName()))
			throw new UserNotAuthorizedException();

		// update the session
		session.setLocation(newSession.getLocation());
		session.setTitle(newSession.getTitle());
		session.setSpeakerName(newSession.getSpeakerName());
		session.setDescription(newSession.getDescription());
		session.setStart(newSession.getStart());
		session.setEnd(newSession.getEnd());
		session.setCapacity(newSession.getCapacity());
		session.setExternalLink(newSession.getExternalLink());
		session.setType(newSession.getType());

		// persist
		GenericDaoJpa.startTransaction();
		sessionRepo.update(session);
		GenericDaoJpa.commitTransaction();
	}

	public void deleteSession(Session session) throws UserNotAuthorizedException {
		MemberType memberType = loggedInMemberManager.getLoggedInMember().getMemberType();
		if (memberType != MemberType.HEADADMIN && memberType != MemberType.ADMIN)
			throw new UserNotAuthorizedException();

		// user is an admin but session to edit isn't organized by this admin
		if (memberType == MemberType.ADMIN
				&& !session.getFullOrganizerName().equals(loggedInMemberManager.getLoggedInMember().getFullName()))
			throw new UserNotAuthorizedException();

		// delete from calendar
		calendar.deleteSession(session);

		// persist
		GenericDaoJpa.startTransaction();
		sessionRepo.delete(session);
		GenericDaoJpa.commitTransaction();
	}

	public Session getSessionByTitle(String title) {
		return sessionRepo.getSessionByTitle(title);
	}

	public Set<Session> getAllSessions() {
		return calendar.getSessions();
	}

	public Set<Session> getAllFinishedSessions() {
		return calendar.getFinishedSessions();
	}

}
