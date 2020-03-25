package test.domain.facades;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import main.domain.Member;
import main.domain.MemberType;
import main.domain.Session;
import main.domain.SessionCalendar;
import main.domain.facades.SessionCalendarFacade;
import persistence.SessionCalendarDaoJpa;
import persistence.SessionDaoJpa;

@ExtendWith(MockitoExtension.class)
public class SessionCalendarFacadeTest {

	@Mock
	private SessionCalendarDaoJpa sessionCalendarRepoDummy;
	
	@Mock
	private SessionDaoJpa sessionRepoDummy;
	
	@Mock
	private SessionCalendar sessionCalendarDummy;
	
	@InjectMocks
	private SessionCalendarFacade sessionCalendarFacade;
	
	//SessionCalendar test
	private static Stream<Arguments> addSessionCalendarFixture() {
		return Stream.of(
				Arguments.of(LocalDate.of(2020, 9, 21), LocalDate.of(2021, 9, 20))
		);
	}
	
	@ParameterizedTest
	@MethodSource("addSessionCalendarFixture")
	public void createSession_ReturnsCorrectCalendar(LocalDate start, LocalDate end) {
		sessionCalendarFacade = new SessionCalendarFacade();
		assertEquals(start, sessionCalendarFacade.createSessionCalendar(start, end).getStartDate());
		assertEquals(end, sessionCalendarFacade.createSessionCalendar(start, end).getEndDate());
	}
	
	@ParameterizedTest
	@NullSource
	public void setSessionCalendar_NullSessionCalendar_ThrowsIllegalArgumentException(SessionCalendar sessionCalendar) {
		sessionCalendarFacade = new SessionCalendarFacade();
		assertThrows(IllegalArgumentException.class, () -> sessionCalendarFacade.setCalendar(sessionCalendar));
	}
	
	//SessionCalendarRepo test
	private static Stream<Arguments> getAllSessionCalendarsFixture() {
		return Stream.of(
				Arguments.of(new ArrayList<SessionCalendar>(List.of(
						new SessionCalendar(LocalDate.of(2020, 9, 21), LocalDate.of(2021, 9, 20)),
						new SessionCalendar(LocalDate.of(2021, 9, 21), LocalDate.of(2022, 9, 20))
				)), new SessionCalendar(LocalDate.of(2022, 9, 21), LocalDate.of(2023, 9, 20)))
		);
	}
	
	@ParameterizedTest
	@MethodSource("getAllSessionCalendarsFixture")
	public void getAllSessionCalendars_GetsAllSessionCalendarsCorrectly(List<SessionCalendar> listAllSessionCalendars, SessionCalendar calendar) {
		Mockito.when(sessionCalendarRepoDummy.findAll()).
				thenReturn(listAllSessionCalendars);
		
		List<SessionCalendar> result = sessionCalendarFacade.getAllSessionCalendars();
		assertEquals(listAllSessionCalendars, result);
		
		Mockito.verify(sessionCalendarRepoDummy).findAll();
	}
	
	@ParameterizedTest
	@MethodSource("getAllSessionCalendarsFixture")
	public void addSessionCalendar_AddsSessionCalendarCorrectly(List<SessionCalendar> listAllSessionCalendars, SessionCalendar calendar) {
		Mockito.when(sessionCalendarRepoDummy.findAll()).
				thenReturn(listAllSessionCalendars);
		Mockito.doNothing().
				when(sessionCalendarRepoDummy).insert(calendar);
		
		sessionCalendarFacade.addSessionCalendar(calendar);
		listAllSessionCalendars.add(calendar);
		List<SessionCalendar> result = sessionCalendarFacade.getAllSessionCalendars();
		assertEquals(listAllSessionCalendars, result);
		
		Mockito.verify(sessionCalendarRepoDummy).findAll();
		Mockito.verify(sessionCalendarRepoDummy).insert(calendar);
		
	}
	
	private static Stream<Arguments> editSessionCalendarFixture() {
		return Stream.of(
				Arguments.of(new SessionCalendar(LocalDate.of(2020, 9, 21), LocalDate.of(2021, 9, 20)), LocalDate.of(2020, 9, 20), LocalDate.of(2021, 9, 21), new ArrayList<SessionCalendar>(List.of(new SessionCalendar(LocalDate.of(2020, 9, 20), LocalDate.of(2021, 9, 21)))))
		);
	}
	
	@ParameterizedTest
	@MethodSource("editSessionCalendarFixture")
	public void editSessionCalendar_EditsSessionCalendarCorrectly(SessionCalendar pre, LocalDate start, LocalDate end, List<SessionCalendar> postList) {
		Mockito.when(sessionCalendarRepoDummy.findAll()).
				thenReturn(postList);
		
		sessionCalendarFacade.editSessionCalendar(pre, start, end);
		List<SessionCalendar> result = sessionCalendarFacade.getAllSessionCalendars();
		assertEquals(postList, result);
		
		Mockito.verify(sessionCalendarRepoDummy).findAll();
	}
	
	//SessionRepo and SessionCalendar tests
	private static Stream<Arguments> getSessionByTitleFixture() {
		return Stream.of(
				Arguments.of("title", new Session())		
		);
	}
	
	@ParameterizedTest
	@MethodSource("getSessionByTitleFixture")
	public void getSessionByTitle_GetsCorrectSession(String title, Session expected) {
		Mockito.when(sessionRepoDummy.getSessionByTitle(title)).
				thenReturn(expected);
		
		assertEquals(expected, sessionCalendarFacade.getSessionByTitle(title));
		
		Mockito.verify(sessionRepoDummy).getSessionByTitle(title);
	}
	
	private static Stream<Arguments> addSessionFixture() {
		return Stream.of(
				Arguments.of(new Session(), new HashSet<Session>(Arrays.asList(new Session())))
		);
	}
	
	@ParameterizedTest
	@MethodSource("addSessionFixture")
	public void addSession_AddsSessionCorrectly(Session session, Set<Session> listSessions) {
		Mockito.doNothing().
				when(sessionCalendarDummy).addSession(session);
		Mockito.doNothing().
				when(sessionRepoDummy).insert(session);
		Mockito.when(sessionCalendarDummy.getSessions()).
				thenReturn(listSessions);
		
		sessionCalendarFacade.addSession(session);
		listSessions.add(session);
		Set<Session> result = sessionCalendarFacade.getAllSessions();
		assertEquals(listSessions, result);
		
		Mockito.verify(sessionCalendarDummy).addSession(session);
		Mockito.verify(sessionRepoDummy).insert(session);
		Mockito.verify(sessionCalendarDummy).getSessions();
	}
	
	@ParameterizedTest
	@MethodSource("addSessionFixture")
	public void deleteSession_DeletesSessionCorrectly(Session session, Set<Session> listSessions) {
		Mockito.doNothing().
				when(sessionCalendarDummy).deleteSession(session);
		Mockito.doNothing().
				when(sessionRepoDummy).delete(session);
		Mockito.when(sessionCalendarDummy.getSessions()).
				thenReturn(listSessions);
		
		sessionCalendarFacade.deleteSession(session);
		listSessions.remove(session);
		Set<Session> result = sessionCalendarFacade.getAllSessions();
		assertEquals(listSessions, result);
		
		Mockito.verify(sessionCalendarDummy).deleteSession(session);
		Mockito.verify(sessionRepoDummy).delete(session);
		Mockito.verify(sessionCalendarDummy).getSessions();
	}
	
	private static Stream<Arguments> editSessionFixture() {
		return Stream.of(
				Arguments.of(new Session(), 
						new Session(new Member("JohnDoe", "John", "Doe", MemberType.HEADADMIN), 
									"title", 
									"description", 
									"name", 
									LocalDateTime.of(2020, 5, 5, 18, 0),
									LocalDateTime.of(2020, 5, 5, 19, 0),
									"GSCHB3.016",
									30), 
						new HashSet<Session>(Arrays.asList(new Session())))
		);
	}
	
	@ParameterizedTest
	@MethodSource("editSessionFixture")
	public void editSession_EditsSessionCorrectly(Session session, Session newSession, Set<Session> listSessions) {
		Mockito.doNothing().
				when(sessionCalendarDummy).addSession(session);
		Mockito.doNothing().
				when(sessionCalendarDummy).deleteSession(session);
		Mockito.when(sessionRepoDummy.update(session)).
				thenReturn(newSession);
		Mockito.when(sessionCalendarDummy.getSessions()).
				thenReturn(listSessions);
		
		sessionCalendarFacade.editSession(session, newSession);
		listSessions.remove(session);
		listSessions.add(newSession);
		Set<Session> result = sessionCalendarFacade.getAllSessions();
		assertEquals(listSessions, result);
		
		Mockito.verify(sessionCalendarDummy).addSession(session);
		Mockito.verify(sessionCalendarDummy).deleteSession(session);
		Mockito.verify(sessionRepoDummy).update(session);
		Mockito.verify(sessionCalendarDummy).getSessions();
	}
	
	
	
}
