package main.domain.facades;

import java.util.List;

import main.domain.Session;
import main.domain.SessionCalendar;

public class SessionCalendarFacade implements Facade {

	SessionCalendar currentCalendar;

	public SessionCalendarFacade() {
		// TODO set default calendar here
	}

	public SessionCalendarFacade(SessionCalendar calendar) {
		setCurrentCalendar(calendar);
	}

	public void addSession(Session session) {
		currentCalendar.addSession(session);
	}

	public void removeSession(int id) {
		// TODO
	}

	public Session getSession(int id) {
		// TODO
		return null;
	}

	public List<Session> getAllSessions() {
		// TODO
		return null;
	}

	public SessionCalendar getCurrentCalendar() {
		return currentCalendar;
	}

	public void setCurrentCalendar(SessionCalendar currentCalendar) {
		this.currentCalendar = currentCalendar;
	}

}
