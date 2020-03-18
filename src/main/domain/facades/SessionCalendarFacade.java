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

	private SessionCalendarDaoJpa sessionCalendarRepo = new SessionCalendarDaoJpa();
	private SessionDaoJpa sessionRepo = new SessionDaoJpa();
	private SessionCalendar calendar;

	public SessionCalendarFacade() {
	}

	/*
	 * -----------------------------------------------------------------------------
	 * Calendar
	 * -----------------------------------------------------------------------------
	 */

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

	/**
	 * 
	 * @param organizer
	 * @param title
	 * @param description
	 * @param speakerName
	 * @param startDate
	 * @param startTime
	 * @param duration    has to be a string of format "hh:mm"!!!
	 * @param location
	 * @param capacity
	 * @return
	 * @throws InvalidSessionException
	 */
	public Session createSessionFromFields(
			Member organizer, String title, String description, String speakerName,
			LocalDate startDate, LocalTime startTime, String duration, String location, String capacity
	)
			throws InvalidSessionException {

		try {

			// get start
			LocalDateTime start = LocalDateTime.of(startDate, startTime);

			// calculate duration
			String[] d = duration.split(":");
			if (d.length != 2)
				throw new IllegalArgumentException("duration has to be of format h:mm");
			int durationHours = Integer.parseInt(d[0].trim());
			int durationMinutes = Integer.parseInt(d[1].trim());

			// get end
			LocalDateTime end = start.plusHours(durationHours).plusMinutes(durationMinutes);

			// check if start and end are ok for the current calendar
			if (calendar.getStartDate().isAfter(start.toLocalDate())
					|| start.toLocalDate().isAfter(calendar.getEndDate()))
				throw new InvalidSessionException(
						String.format(
								"Startdatum moet binnen de data van de kalender liggen (nl. %s - %s)",
								calendar.getStartDate().format(Util.DATEFORMATTER),
								calendar.getEndDate().format(Util.DATEFORMATTER)
						)
				);

			if (calendar.getEndDate().isBefore(end.toLocalDate()))
				throw new InvalidSessionException(
						String.format(
								"Einddatum kan niet later zijn dan die van de kalender (nl. %s)",
								calendar.getEndDate().format(Util.DATEFORMATTER)
						)
				);

			// construct a session
			return new Session(
					organizer, title, description, speakerName, start, end, location,
					Integer.parseInt(capacity)
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
