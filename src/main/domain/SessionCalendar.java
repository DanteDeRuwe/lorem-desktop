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
import persistence.GenericDaoJpa;
import persistence.SessionDaoJpa;

@Entity
@NamedQueries({
		@NamedQuery(name = "SessionCalendar.getCurrentSessionCalendar", query = "select c from SessionCalendar c where CURRENT_DATE between c.startDate and c.endDate") })
public class SessionCalendar {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long calendar_id;
	private int[] academicYear;
	private LocalDate startDate;
	private LocalDate endDate;
	@OneToMany(mappedBy = "calendar") private Set<Session> sessions;

	public SessionCalendar() {
	}

	public SessionCalendar(LocalDate startDate, LocalDate endDate) {
		// we also need to check if there are no already existing sessionCalendars which
		// would overlap with this one
		if (startDate == null || endDate == null) {
			throw new IllegalArgumentException("startDate and endDdate must not be null.");
		}
		setAcademicYear(startDate.getYear(), endDate.getYear());
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

		// persist
		SessionDaoJpa sessionRepo = new SessionDaoJpa();
		GenericDaoJpa.startTransaction();
		sessionRepo.insert(session);
		GenericDaoJpa.commitTransaction();
	}

	public int[] getAcademicYear() {
		return academicYear;
	}

	private void setAcademicYear(int startYear, int endYear) {

		if (startYear < 0 || endYear < 0) {
			throw new IllegalArgumentException("The start and end years of an academic year must be positive");
		}
		// no need to check for null, because an int is a primitive and can't be null

		if (endYear != startYear + 1) {
			throw new IllegalArgumentException(
					"Academic years must start and end in consecutive years(e.g if it starts in 2020, it must end in 2021)");
		}

		int[] academicYear = new int[2];
		academicYear[0] = startYear;
		academicYear[1] = endYear;
		this.academicYear = academicYear;
	}

	public StringProperty academicYearProperty() {
		return new SimpleStringProperty(String.format("%d - %d", academicYear[0], academicYear[1]));
	}

}