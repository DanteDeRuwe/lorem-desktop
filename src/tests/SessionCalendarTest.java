package tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import main.java.domain.SessionCalendar;

public class SessionCalendarTest {
	static LocalDate startDate;
	static LocalDate endDate;
	static int[] aYear;

	@BeforeAll
	static void beforeAll() {
		startDate = LocalDate.of(2020, 9, 21);
		endDate = LocalDate.of(2021, 9, 20);
		aYear = new int[2];
		aYear[0] = startDate.getYear();
		aYear[1] = endDate.getYear();
	}
	
	@ParameterizedTest
	@NullSource
	public void createCalendar_NullStartDate_ThrowsIllegalArgumentException(LocalDate sDate) {
		assertThrows(IllegalArgumentException.class, () -> new SessionCalendar(sDate, endDate));
	}
	
	@ParameterizedTest
	@NullSource
	public void createCalendar_NullEndDate_ThrowsIllegalArgumentException(LocalDate eDate) {
		assertThrows(IllegalArgumentException.class, () -> new SessionCalendar(startDate, eDate));
	}
	
	@ParameterizedTest
	@CsvSource({"2020, 9, 20"})
	public void createCalendar_EndDateBeforeStartDate_ThrowsIllegalArgumentException(int year, int month, int day) {
		assertThrows(IllegalArgumentException.class, () -> new SessionCalendar(startDate, LocalDate.of(year, month, day)));
	}
	
	@Test
	public void createCalendar_CorrectStartDateAndEndDate_ReturnsCorrectStartDate() {
		assertEquals(new SessionCalendar(startDate, endDate).getStartDate(), startDate);
	}

	@Test
	public void createCalendar_CorrectStartDateAndEndDate_ReturnsCorrectEndDate() {
		assertEquals(new SessionCalendar(startDate, endDate).getEndDate(), endDate);
	}

	@Test
	public void createCalendar_CorrectStartDateAndEndDate_ReturnsCorrectAcademicYear() {
		assertArrayEquals(new SessionCalendar(startDate, endDate).getAcademicYear(), aYear);
	}
}
