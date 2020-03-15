package main.domain.facades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import main.domain.Member;
import main.domain.Session;
import main.domain.SessionCalendar;
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

	public Session createSessionFromFields(Member organizer, String title, String speakerName, LocalDate startDate,
			LocalTime startTime, String duration, String location, String capacity) {

		LocalDateTime start = LocalDateTime.of(startDate, startTime);

		double durationInHours = Double.parseDouble(duration);
		int durationInSeconds = (int) (durationInHours * 3600);
		LocalDateTime end = start.plusSeconds(durationInSeconds);

		return new Session(organizer, title, speakerName, start, end, location, Integer.parseInt(capacity));
	}

	public SessionCalendarFacade(SessionCalendar calendar) {
		setCalendar(calendar);
	}

	public void addSession(Session session) {
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

	public Session getSessionById(int id) {
		// TODO getSession from database
		return null;
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

}
