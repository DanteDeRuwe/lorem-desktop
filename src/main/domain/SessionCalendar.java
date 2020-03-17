package main.domain;

import java.time.LocalDate;
import java.util.HashSet;
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

@Entity
@NamedQueries({
		@NamedQuery(name = "SessionCalendar.getCurrentSessionCalendar", query = "select c from SessionCalendar c where CURRENT_DATE between c.startDate and c.endDate") })
public class SessionCalendar {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long calendar_id;
	private LocalDate startDate;
	private LocalDate endDate;
	@OneToMany(mappedBy = "calendar") private Set<Session> sessions;

	public SessionCalendar() {
	}

	public SessionCalendar(LocalDate startDate, LocalDate endDate) {

		if (startDate == null || endDate == null) {
			throw new IllegalArgumentException("startDate and endDate must not be null.");
		}

		if (endDate.getYear() != startDate.getYear() + 1) {
			throw new IllegalArgumentException(
					"Academic years must start and end in consecutive years"
			);
		}

		setStartDate(startDate);
		setEndDate(endDate);
		sessions = new HashSet<>();
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		if (startDate == null) {
			throw new IllegalArgumentException("the start date of the calendar is null");
		}
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		if (startDate == null) {
			throw new IllegalArgumentException("the end date of the calendar is null");
		}
		this.endDate = endDate;
	}

	public Set<Session> getSessions() {
		return sessions;
	}

	public void setSessions(HashSet<Session> sessions) {
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

}