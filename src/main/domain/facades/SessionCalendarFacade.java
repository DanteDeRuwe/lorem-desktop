package main.domain.facades;

import java.util.List;
import java.util.Set;

import main.domain.Session;
import main.domain.SessionCalendar;
import persistence.SessionCalendarDaoJpa;

public class SessionCalendarFacade implements Facade {

	SessionCalendarDaoJpa sesCalRepo = new SessionCalendarDaoJpa();
	SessionCalendar calendar;

	public SessionCalendarFacade() {
		// set calendar to current calendar by default
		// TODO: this will throw an exception if there is no sessionCalendar for right now, maybe a try catch or something.
		setCalendar(sesCalRepo.getCurrentSessionCalendar());
	}

	public SessionCalendarFacade(SessionCalendar calendar) {
		setCalendar(calendar);
	}

	public void addSession(Session session) {
		calendar.addSession(session);
	}

	public void removeSession(int id) {
		// TODO
	}

	public Session getSession(int id) {
		// TODO
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

}
