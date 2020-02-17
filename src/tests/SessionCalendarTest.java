package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.SessionCalendar;

public class SessionCalendarTest {
	SessionCalendar cal;
	
	@BeforeEach
	void init() {
		cal = new SessionCalendar(2020);
	}
	

    @Test
    public void newCalendar2020StartDateTest() {
    	assertEquals(cal.getStartDate(), LocalDate.of(2020, 9, 21));
    }
    
    @Test
    public void newCalendar2020EndtDateTest() {
    	assertEquals(cal.getEndDate(), LocalDate.of(2021, 9, 26));
    }
    
}
