package domain;

import java.lang.UnsupportedOperationException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SessionCalendar {
	private int[] academicYear;
	private LocalDate startDate;
	private LocalDate endDate;
	private ArrayList<Session> sessions;
	
	public SessionCalendar(int[] academicYear, LocalDate startDate, LocalDate endDate) {
		this.academicYear = academicYear;
		this.startDate = startDate;
		this.endDate = endDate;
		sessions = new ArrayList<Session>(); //Hier worden dan later in de functie getSessions() alle sessions van de huidige maand aan toegevoegd.
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public ArrayList<Session> getSessions() {
		return sessions;
	}

	public void setSessions(ArrayList<Session> sessions) {
		this.sessions = sessions;
	}

	public int[] getAcademicYear() {
		return academicYear;
	}
	
	

}