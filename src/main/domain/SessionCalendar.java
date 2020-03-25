package main.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.exceptions.InvalidSessionException;
import main.services.Util;

@Entity
@NamedQueries({
		@NamedQuery(name = "SessionCalendar.getCurrentSessionCalendar", query = "select c from SessionCalendar c where CURRENT_DATE between c.startDate and c.endDate") })
public class SessionCalendar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long calendar_id;
	private LocalDate startDate;
	private LocalDate endDate;
	@OneToMany(mappedBy = "calendar")
	private Set<Session> sessions;

	public SessionCalendar() {
	}

	public SessionCalendar(LocalDate startDate, LocalDate endDate) {

		setStartDate(startDate);
		setEndDate(endDate);
		sessions = new HashSet<>();
	}

	public boolean sessionIsWithinRange(LocalDateTime sessionStart, LocalDateTime sessionEnd)
			throws InvalidSessionException {
		if (getStartDate().isAfter(sessionStart.toLocalDate()) || sessionStart.toLocalDate().isAfter(getEndDate()))
			throw new InvalidSessionException(
					String.format("Startdatum moet binnen de data van de kalender liggen (nl. %s - %s)",
							getStartDate().format(Util.DATEFORMATTER), getEndDate().format(Util.DATEFORMATTER)));

		if (getEndDate().isBefore(sessionEnd.toLocalDate()))
			throw new InvalidSessionException(
					String.format("Einddatum kan niet later zijn dan die van de kalender (nl. %s)",
							getEndDate().format(Util.DATEFORMATTER)));

		return true;
	}

	public boolean doesNotOverlap(List<SessionCalendar> allSessionCalendars) {
		for (SessionCalendar existingCalendar : allSessionCalendars) {
			if ((getStartDate().isAfter(existingCalendar.getStartDate())
					&& getStartDate().isBefore(existingCalendar.getEndDate()))
					|| (getEndDate().isAfter(existingCalendar.getStartDate())
							&& getEndDate().isBefore(existingCalendar.getEndDate())))
				return false;
		}
		return true;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		if (startDate == null) {
			throw new IllegalArgumentException("the start date of the calendar is null");
		}

		if (startDate.getYear() < LocalDate.now().getYear() - 1)
			throw new IllegalArgumentException("Cannot create calendar that far in the past");

		if (endDate != null && endDate.getYear() != startDate.getYear() + 1)
			throw new IllegalArgumentException("Academic years must start and end in consecutive years");

		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		if (endDate == null) {
			throw new IllegalArgumentException("the end date of the calendar is null");
		}

		if (startDate != null && endDate.getYear() != startDate.getYear() + 1)
			throw new IllegalArgumentException("Academic years must start and end in consecutive years");

		this.endDate = endDate;
	}

	public Set<Session> getSessions() {
		return sessions;
	}

	public void setSessions(Set<Session> sessions) {
		this.sessions = sessions;
	}

	public void addSession(Session session) {
		// set the calendar on the session
		session.setCalendar(this);

		// add the session to this calendar
		sessions.add(session);
	}

	public void deleteSession(Session session) {
		sessions.remove(session);
	}

	public StringProperty academicYearProperty() {
		return new SimpleStringProperty(String.format("%d - %d", startDate.getYear(), endDate.getYear()));
	}

	// Statistics finished sessions
	public Set<Session> getFinishedSessions() {
		Set<Session> set = new HashSet<>();
		LocalDateTime now = LocalDateTime.now();

		for (Session s : this.sessions) {
			if (s.getEnd().isBefore(now)) {
				set.add(s);
			}
		}

		return set;
	}

	public int getTotalRegistreesFinishedSessions() {
		Set<Session> fSessions = getFinishedSessions();
		int total = 0;
		for (Session s : fSessions) {
			total += s.countRegistrees();
		}
		return total;
	}

	public int getTotalAttendeesFinishedSessions() {
		Set<Session> fSessions = getFinishedSessions();
		int total = 0;
		for (Session s : fSessions) {
			total += s.countAttendees();
		}
		return total;
	}

	public double getAverageRegistreesPerSessionFinishedSessions() {
		double totalOfRegistrees = getTotalRegistreesFinishedSessions();
		return totalOfRegistrees / getFinishedSessions().size();
	}

	public double getAverageAttendeesPerSessionFinishedSessions() {
		double totalOfAttendees = getTotalAttendeesFinishedSessions();
		return totalOfAttendees / getFinishedSessions().size();
	}

	public double getAverageAttendeesPerRegistreesFinishedSessions() {
		double totalOfAttendees = getTotalAttendeesFinishedSessions();
		double totalOfRegistrees = getTotalRegistreesFinishedSessions();
		return totalOfAttendees / totalOfRegistrees;
	}

	// Statistics planned sessions
	public Set<Session> getPlannedSessions() {
		Set<Session> set = new HashSet<>();
		LocalDateTime now = LocalDateTime.now();

		for (Session s : this.sessions) {
			if (now.isBefore(s.getEnd())) {
				set.add(s);
			}
		}

		return set;
	}

	public int getTotalRegistreesPlannedSessions() {
		Set<Session> pSessions = getPlannedSessions();
		int total = 0;
		for (Session s : pSessions) {
			total += s.countRegistrees();
		}
		return total;
	}

	public double getAverageRegistreesPerSessionPlannedSessions() {
		double totalOfRegistrees = getTotalRegistreesPlannedSessions();
		return totalOfRegistrees / getPlannedSessions().size();
	}

}