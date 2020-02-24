package main.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class SessionCalendar {
	private int[] academicYear;
	private LocalDate startDate;
	private LocalDate endDate;
	private ArrayList<Session> sessions;

	public SessionCalendar(LocalDate startDate, LocalDate endDate) {
		// we also need to check if there are no already existing sessionCalendars which would overlap with this one
		if (startDate == null || endDate == null) {
			throw new IllegalArgumentException("startDate and endDdate must not be null.");
		}
		setAcademicYear(startDate.getYear(), endDate.getYear());
		setStartDate(startDate);
		setEndDate(endDate);
		sessions = new ArrayList<>();
		// we also need to add any pre-existing sessions from the database for this academic year
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		if (startDate == null ) {
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

	public ArrayList<Session> getSessions() {
		return sessions;
	}

	public void setSessions(ArrayList<Session> sessions) {
		this.sessions = sessions;
	}
	
	public void addSession(Session session) {
		this.sessions.add(session);
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
			throw new IllegalArgumentException("Academic years must start and end in consecutive years(e.g if it starts in 2020, it must end in 2021)");
		}
		
		int[] academicYear = new int[2];
		academicYear[0] = startYear;
		academicYear[1] = endYear;
		this.academicYear = academicYear;
	}
	
	

}