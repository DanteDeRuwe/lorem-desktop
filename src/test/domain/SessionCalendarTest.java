package test.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import main.domain.Session;
import main.domain.SessionCalendar;
import main.exceptions.InvalidSessionException;

public class SessionCalendarTest {
	
	private static SessionCalendar exSesCal;
	private static Set<Session> exSessions;
	private static Session exSession;
	private static LocalDate startDate;
	private static LocalDate endDate;
	private static int[] aYear;

	@BeforeAll
	public static void beforeAll() {
		startDate = LocalDate.of(2020, 9, 21);
		endDate = LocalDate.of(2021, 9, 20);
		aYear = new int[2];
		aYear[0] = startDate.getYear();
		aYear[1] = endDate.getYear();
		exSessions = new HashSet<>();
		exSession = new Session();
	}
	
	@BeforeEach
	public void beforeEach() {
		exSesCal = new SessionCalendar(startDate, endDate);
	}

	//Constructor tests
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
	@CsvSource({ "2020, 9, 20" })
	public void createCalendar_EndDateBeforeStartDate_ThrowsIllegalArgumentException(int year, int month, int day) {
		assertThrows(IllegalArgumentException.class,
				() -> new SessionCalendar(startDate, LocalDate.of(year, month, day)));
	}

	@Test
	public void createCalendar_CorrectStartDateAndEndDate_ReturnsCorrectStartDate() {
		assertEquals(new SessionCalendar(startDate, endDate).getStartDate(), startDate);
	}

	@Test
	public void createCalendar_CorrectStartDateAndEndDate_ReturnsCorrectEndDate() {
		assertEquals(new SessionCalendar(startDate, endDate).getEndDate(), endDate);
	}
	
	//SessionRange tests
	private static Stream<Arguments> addGoodFixture() {
		return Stream.of(
				Arguments.of(LocalDateTime.of(2020, 9, 21, 0, 0), LocalDateTime.of(2020, 9, 21, 1, 0), true),
				Arguments.of(LocalDateTime.of(2021, 9, 20, 23, 0), LocalDateTime.of(2021, 9, 20, 23, 59), true)
		);
	}
	
	@ParameterizedTest
	@MethodSource("addGoodFixture")
	public void sessionIsWithinRange_ReturnsTrue(LocalDateTime start, LocalDateTime end, boolean res) {
		try {
			assertEquals(res, exSesCal.sessionIsWithinRange(start, end));
		} catch (InvalidSessionException e) {
			e.printStackTrace();
		}
	}
	
	private static Stream<Arguments> addBadFixture() {
		return Stream.of(
				Arguments.of(LocalDateTime.of(2020, 9, 20, 23, 59), LocalDateTime.of(2020, 9, 21, 1, 0)),
				Arguments.of(LocalDateTime.of(2021, 9, 21, 0, 0), LocalDateTime.of(2021, 9, 21, 0, 59)),
				Arguments.of(LocalDateTime.of(2021, 9, 20, 23, 0), LocalDateTime.of(2021, 9, 21, 0, 0))
		);
	}
	
	@ParameterizedTest
	@MethodSource("addBadFixture")
	public void sessionIsWithinRange_ThrowsInvalidSessionException(LocalDateTime start, LocalDateTime end) {
		assertThrows(InvalidSessionException.class, () -> exSesCal.sessionIsWithinRange(start, end));
	}
	
	//StartDate tests
	@Test
	public void getStartDate_GetsStartDate() {
		assertEquals(startDate, exSesCal.getStartDate());
	}
	
	private static Stream<Arguments> addCorrectStartDates() {
		return Stream.of(
				Arguments.of(LocalDate.of(2020, 9, 22)),
				Arguments.of(LocalDate.of(2020, 9, 23)),
				Arguments.of(LocalDate.of(2020, 9, 24)),
				Arguments.of(LocalDate.of(2020, 9, 25)),
				Arguments.of(LocalDate.of(2020, 9, 26))
		);
	}
	
	@ParameterizedTest
	@MethodSource("addCorrectStartDates")
	public void setStartDate_CorrectStartDate_SetsStartDate(LocalDate start) {
		exSesCal.setStartDate(start);
		assertEquals(start, exSesCal.getStartDate());
	}
	
	@ParameterizedTest
	@NullSource
	public void setStartDate_NullStartDate_ThrowsIllegalArgumentException(LocalDate start) {
		assertThrows(IllegalArgumentException.class, () -> exSesCal.setStartDate(start));
	}
	
	//EndDate tests
	@Test
	public void getEndDate_GetsEndDate() {
		assertEquals(endDate, exSesCal.getEndDate());
	}
	
	private static Stream<Arguments> addCorrectEndDates() {
		return Stream.of(
				Arguments.of(LocalDate.of(2021, 9, 22)),
				Arguments.of(LocalDate.of(2021, 9, 23)),
				Arguments.of(LocalDate.of(2021, 9, 24)),
				Arguments.of(LocalDate.of(2021, 9, 25)),
				Arguments.of(LocalDate.of(2021, 9, 26))
		);
	}
	
	@ParameterizedTest
	@MethodSource("addCorrectStartDates")
	public void setEndDate_CorrectEndDate_SetsEndDate(LocalDate end) {
		exSesCal.setEndDate(end);
		assertEquals(end, exSesCal.getEndDate());
	}
	
	@ParameterizedTest
	@NullSource
	public void setEndDate_NullEndDate_ThrowsIllegalArgumentException(LocalDate end) {
		assertThrows(IllegalArgumentException.class, () -> exSesCal.setEndDate(end));
	}
	
	//Sessions tests
	@Test
	public void getSessions_GetsCorrectSessions() {
		assertEquals(new HashSet<>(), exSesCal.getSessions());
	}
	
	@Test
	public void setSessions_setsSessions() {
		Set<Session> sessionSet = new HashSet<>();
		Session ses = new Session();
		sessionSet.add(ses);
		exSesCal.setSessions(sessionSet);
		assertEquals(sessionSet, exSesCal.getSessions());
	}
	
	@Test
	public void addSession_AddsCorrectSession() {
		exSesCal.addSession(exSession);
		exSessions.add(exSession);
		assertEquals(exSessions, exSesCal.getSessions());
	}
	
	@Test
	public void deleteSession_DeletesCorrectSession() {
		exSesCal.deleteSession(exSession);
		exSessions.remove(exSession);
		assertEquals(exSessions, exSesCal.getSessions());
	}
	

}
