package main.domain.facades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import main.domain.Member;
import main.domain.Session;
import main.domain.SessionCalendar;
import persistence.SessionCalendarDaoJpa;

public class SessionCalendarFacade implements Facade {

	SessionCalendarDaoJpa sesCalRepo = new SessionCalendarDaoJpa();
	SessionCalendar calendar;

	public SessionCalendarFacade() {

		// set calendar to current calendar by default
		// TODO: this will throw an exception if there is no sessionCalendar for right
		// now, maybe a try catch or something.
		setCalendar(sesCalRepo.getCurrentSessionCalendar());

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
	}

	public void removeSession(int id) {
		// TODO remove session from database
	}

	public Session getSession(int id) {
		// TODO getSession from database
		return null;
	}

	public Set<Session> getAllSessions() {
		return calendar.getSessions();
	}

	public List<SessionCalendar> getAllSessionCalendars() {
		return sesCalRepo.findAll();
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

	public void deleteSession(Session session) {
		calendar.deleteSession(session);
	}

}
