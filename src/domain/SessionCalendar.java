package domain;

import java.lang.UnsupportedOperationException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SessionCalendar {
	private LocalDate startDate;
	private LocalDate endDate;
	private ArrayList<Session> sessions = new ArrayList<Session>();
	
	public SessionCalendar(int startYear) {
		throw new UnsupportedOperationException();
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
	
	

}