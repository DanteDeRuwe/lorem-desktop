package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.SessionCalendar;
import domain.*;

public class SessionCalendarTest {
	SessionCalendar cal;
	int[] aYear;
	
	@BeforeEach
	void init() {
		aYear = new int[2];
		aYear[0] = 2020;
		aYear[1] = 2021;
		cal = new SessionCalendar(aYear, LocalDate.of(aYear[0], 9, 21), LocalDate.of(aYear[1], 9, 20));
	}
	

    @Test
    public void newCalendar2020StartDateTest() {
    	assertEquals(cal.getStartDate(), LocalDate.of(2020, 9, 21));
    }
    
    @Test
    public void newCalendar2020EndDateTest() {
    	assertEquals(cal.getEndDate(), LocalDate.of(2021, 9, 20));
    }
    
    @Test
    public void newCalendar2020AcademicYearTest() {
    	assertEquals(cal.getAcademicYear(), aYear);
    }
    
}
