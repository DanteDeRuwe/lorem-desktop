package main.domain.facades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import main.domain.Member;
import main.domain.Session;
import main.domain.SessionCalendar;
import main.exceptions.InvalidSessionException;
import main.services.Util;
import persistence.GenericDaoJpa;
import persistence.SessionCalendarDaoJpa;
import persistence.SessionDaoJpa;

public class SessionCalendarFacade implements Facade {

	private SessionCalendarDaoJpa sessionCalendarRepo;
	private SessionDaoJpa sessionRepo;
	private SessionCalendar calendar;

	public SessionCalendarFacade() {
		setSessionCalendarRepo(new SessionCalendarDaoJpa());
		setSessionRepo(new SessionDaoJpa());
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

	public SessionCalendar createSessionCalendar(LocalDate start, LocalDate end) {
		return new SessionCalendar(start, end);
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

	public void addSessionCalendar(SessionCalendar calendar) {
		// TODO we also need to check if there are no already existing sessionCalendars
		// which would overlap with this one

		GenericDaoJpa.startTransaction();
		sessionCalendarRepo.insert(calendar);
		GenericDaoJpa.commitTransaction();
	}

	public void editSessionCalendar(SessionCalendar calendar, LocalDate startDate, LocalDate endDate) {
		GenericDaoJpa.startTransaction();
		calendar.setStartDate(startDate);
		calendar.setEndDate(endDate);
		GenericDaoJpa.commitTransaction();
	}

	public void deleteSessionCalendar() {
		// TODO deleteSessionCalendar
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
			LocalDate startDate, LocalTime startTime, String duration, String location, String capacity, String externalUrl, String type)
			throws InvalidSessionException {
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

	public void addSession(Session session) {
		// add to calendar
		calendar.addSession(session);

		// persist
		GenericDaoJpa.startTransaction();
		sessionRepo.insert(session);
		GenericDaoJpa.commitTransaction();
	}

	public void editSession(Session session, Session newSession) {
		// delete the old session from the runtime calendar
		calendar.deleteSession(session);

		// update the session
		session.setLocation(newSession.getLocation());
		session.setTitle(newSession.getTitle());
		session.setSpeakerName(newSession.getSpeakerName());
		session.setDescription(newSession.getDescription());
		session.setStart(newSession.getStart());
		session.setEnd(newSession.getEnd());
		session.setCapacity(newSession.getCapacity());
		session.setExternalLink(newSession.getExternalLink());

		// add it again with updated info
		calendar.addSession(session);

		// persist
		GenericDaoJpa.startTransaction();
		sessionRepo.update(session);
		GenericDaoJpa.commitTransaction();
	}

	public void deleteSession(Session session) {
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

}
