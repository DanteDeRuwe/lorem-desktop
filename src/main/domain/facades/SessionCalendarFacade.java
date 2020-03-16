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
import persistence.GenericDaoJpa;
import persistence.SessionCalendarDaoJpa;
import persistence.SessionDaoJpa;

public class SessionCalendarFacade implements Facade {

	SessionCalendarDaoJpa sessionCalendarRepo = new SessionCalendarDaoJpa();
	SessionDaoJpa sessionRepo = new SessionDaoJpa();
	SessionCalendar calendar;

	public SessionCalendarFacade() {

		// set calendar to current calendar by default
		// TODO: this will throw an exception if there is no sessionCalendar for right
		// now, change this so the user gets taken to NEW CALENDAR screen
		setCalendar(sessionCalendarRepo.getCurrentSessionCalendar());
	}

	public Session createSessionFromFields(
			Member organizer, String title, String description, String speakerName, LocalDate startDate,
			LocalTime startTime, String duration, String location, String capacity
	) throws InvalidSessionException {

		try {
			LocalDateTime start = LocalDateTime.of(startDate, startTime);

			double durationInHours = Double.parseDouble(duration);
			int durationInSeconds = (int) (durationInHours * 3600);
			LocalDateTime end = start.plusSeconds(durationInSeconds);

			return new Session(
					organizer, title, description, speakerName, start, end, location, Integer.parseInt(capacity)
			);

		} catch (IllegalArgumentException iae) {
			switch (iae.getMessage()) {
			case "start in past":
				throw new InvalidSessionException("Startdatum ligt in het verleden", iae);
			case "start less than 1 day in future":
				throw new InvalidSessionException("Start moet minstens 1 dag in de toekomst liggen", iae);
			case "start and end do not meet minimum period requirement":
				throw new InvalidSessionException("Duurtijd is minstens 30 minuten", iae);
			default:
				throw new InvalidSessionException(iae.getMessage(), iae);
			}
		}
	}

	public SessionCalendarFacade(SessionCalendar calendar) {
		setCalendar(calendar);
	}

	public void addSession(Session session) {
		// add to calendar
		calendar.addSession(session);

		// persist
		GenericDaoJpa.startTransaction();
		sessionRepo.insert(session);
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

	public List<SessionCalendar> getAllSessionCalendars() {
		return sessionCalendarRepo.findAll();
	}

	public SessionCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(SessionCalendar calendar) {
		this.calendar = calendar;
	}

	public String getAcademicYear() {
		return calendar.getAcademicYear()[0] + " - " + calendar.getAcademicYear()[1];
	}

	public Session editSession(Session session, Session newSession) {
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

		// add it again with updated info
		calendar.addSession(session);

		// persist
		GenericDaoJpa.startTransaction();
		sessionRepo.update(session);
		GenericDaoJpa.commitTransaction();

		return session;
	}

}
