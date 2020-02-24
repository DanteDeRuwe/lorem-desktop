package domain;

import java.lang.UnsupportedOperationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.*;
import java.util.*;

public class SessionCalendar {
	private int[] academicYear;
	private LocalDate startDate;
	private LocalDate endDate;
	private ArrayList<Session> sessions;
	
	public SessionCalendar(int[] academicYear, LocalDate startDate, LocalDate endDate) {
		setAcademicYear(academicYear);
		this.startDate = startDate;
		this.endDate = endDate;
		sessions = new ArrayList<>();
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
	
	private void setAcademicYear(int[] acYear) {
		if (acYear.length != 2) {
			throw new IllegalArgumentException("An academic year must be an array with exactly two integers inside.");
		}
		if (acYear[1] != acYear[0] + 1) {
			throw new IllegalArgumentException("The second year of the academic year must be exactly one higher than the first year.");
		}
		this.academicYear = acYear;
	}
	
	

}