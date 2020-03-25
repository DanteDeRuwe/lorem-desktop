package test.domain.facades;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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
import main.domain.facades.LoggedInMemberManager;
import main.domain.facades.SessionCalendarFacade;
import main.exceptions.UserNotAuthorizedException;
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
	
	@Mock
	private LoggedInMemberManager loggedInMemberManagerDummy;

	@InjectMocks
	private SessionCalendarFacade sessionCalendarFacade;

	// SessionCalendar test
	private static Stream<Arguments> addSessionCalendarFixture() {
		return Stream.of(Arguments.of(LocalDate.of(2020, 9, 21), LocalDate.of(2021, 9, 20)));
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

	// SessionCalendarRepo test
	private static Stream<Arguments> getAllSessionCalendarsFixture() {
		return Stream.of(Arguments.of(
				new ArrayList<SessionCalendar>(
						List.of(new SessionCalendar(LocalDate.of(2020, 9, 21), LocalDate.of(2021, 9, 20)),
								new SessionCalendar(LocalDate.of(2021, 9, 21), LocalDate.of(2022, 9, 20)))),
				new SessionCalendar(LocalDate.of(2022, 9, 21), LocalDate.of(2023, 9, 20)),
				new Member())
		);
	}

	@ParameterizedTest
	@MethodSource("getAllSessionCalendarsFixture")
	public void getAllSessionCalendars_GetsAllSessionCalendarsCorrectly(List<SessionCalendar> listAllSessionCalendars,
			SessionCalendar calendar, Member loggedIn) {
		Mockito.when(sessionCalendarRepoDummy.findAll()).thenReturn(listAllSessionCalendars);

		List<SessionCalendar> result = sessionCalendarFacade.getAllSessionCalendars();
		assertEquals(listAllSessionCalendars, result);

		Mockito.verify(sessionCalendarRepoDummy).findAll();
	}

	@ParameterizedTest
	@MethodSource("getAllSessionCalendarsFixture")
	public void addSessionCalendar_AuthorizedMember__AddsSessionCalendarCorrectly(List<SessionCalendar> listAllSessionCalendars,
			SessionCalendar calendar, Member loggedIn) {
		Mockito.when(sessionCalendarRepoDummy.findAll()).thenReturn(listAllSessionCalendars);
		Mockito.doNothing().when(sessionCalendarRepoDummy).insert(calendar);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).
				thenReturn(loggedIn);
		
		try {
			loggedIn.setMemberType(MemberType.HEADADMIN);
			sessionCalendarFacade.addSessionCalendar(calendar);
			listAllSessionCalendars.add(calendar);
			List<SessionCalendar> result = sessionCalendarFacade.getAllSessionCalendars();
			assertEquals(listAllSessionCalendars, result);
		} catch (UserNotAuthorizedException e) {
			e.printStackTrace();
		}

		Mockito.verify(sessionCalendarRepoDummy).findAll();
		Mockito.verify(sessionCalendarRepoDummy).insert(calendar);
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();

	}
	
	@ParameterizedTest
	@MethodSource("getAllSessionCalendarsFixture")
	public void addSessionCalendar_NotAuthorizedMember_ThrowsUserNotAuthorizedException(List<SessionCalendar> listAllSessionCalendars,
			SessionCalendar calendar, Member loggedIn) {
		Mockito.when(sessionCalendarRepoDummy.findAll()).thenReturn(listAllSessionCalendars);
		Mockito.doNothing().when(sessionCalendarRepoDummy).insert(calendar);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).
				thenReturn(loggedIn);
		
		loggedIn.setMemberType(MemberType.USER);
		assertThrows(UserNotAuthorizedException.class, () -> sessionCalendarFacade.addSessionCalendar(calendar));

		Mockito.verify(sessionCalendarRepoDummy).findAll();
		Mockito.verify(sessionCalendarRepoDummy).insert(calendar);
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();

	}

	private static Stream<Arguments> editSessionCalendarFixture() {
		return Stream.of(Arguments.of(new SessionCalendar(LocalDate.of(2020, 9, 21), LocalDate.of(2021, 9, 20)),
									LocalDate.of(2020, 9, 20), 
									LocalDate.of(2021, 9, 21), 
									new ArrayList<SessionCalendar>(List.of(new SessionCalendar(LocalDate.of(2020, 9, 20), LocalDate.of(2021, 9, 21)))),
									new Member())
		);
	}

	@ParameterizedTest
	@MethodSource("editSessionCalendarFixture")
	public void editSessionCalendar_AuthorizedMember_EditsSessionCalendarCorrectly(SessionCalendar pre, LocalDate start, LocalDate end,
			List<SessionCalendar> postList, Member loggedIn) {
		Mockito.when(sessionCalendarRepoDummy.findAll()).thenReturn(postList);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).
				thenReturn(loggedIn);
		
		try {
			loggedIn.setMemberType(MemberType.HEADADMIN);
			sessionCalendarFacade.editSessionCalendar(pre, start, end);
			List<SessionCalendar> result = sessionCalendarFacade.getAllSessionCalendars();
			assertEquals(postList, result);
		} catch (UserNotAuthorizedException e) {
			e.printStackTrace();
		}

		Mockito.verify(sessionCalendarRepoDummy).findAll();
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
	}
	
	@ParameterizedTest
	@MethodSource("editSessionCalendarFixture")
	public void editSessionCalendar_NotAuthorizedMember_ThrowsUserNotAuthorizedException(SessionCalendar pre, LocalDate start, LocalDate end,
			List<SessionCalendar> postList, Member loggedIn) {
		Mockito.when(sessionCalendarRepoDummy.findAll()).thenReturn(postList);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).
				thenReturn(loggedIn);
		
		loggedIn.setMemberType(MemberType.HEADADMIN);
		assertThrows(UserNotAuthorizedException.class, () -> sessionCalendarFacade.editSessionCalendar(pre, start, end));

		Mockito.verify(sessionCalendarRepoDummy).findAll();
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
	}

	// SessionRepo and SessionCalendar tests
	private static Stream<Arguments> getSessionByTitleFixture() {
		return Stream.of(Arguments.of("title", new Session()));
	}

	@ParameterizedTest
	@MethodSource("getSessionByTitleFixture")
	public void getSessionByTitle_GetsCorrectSession(String title, Session expected) {
		Mockito.when(sessionRepoDummy.getSessionByTitle(title)).thenReturn(expected);

		assertEquals(expected, sessionCalendarFacade.getSessionByTitle(title));

		Mockito.verify(sessionRepoDummy).getSessionByTitle(title);
	}

	private static Stream<Arguments> addSessionFixture() {
		return Stream.of(
				Arguments.of(new Session(), new HashSet<Session>(Arrays.asList(new Session())), new Member())
		);
	}

	@ParameterizedTest
	@MethodSource("addSessionFixture")
	public void addSession_AuthorizedMember_AddsSessionCorrectly(Session session, Set<Session> listSessions, Member loggedIn) {
		Mockito.doNothing().when(sessionCalendarDummy).addSession(session);
		Mockito.doNothing().when(sessionRepoDummy).insert(session);
		Mockito.when(sessionCalendarDummy.getSessions()).thenReturn(listSessions);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).
				thenReturn(loggedIn);
		
		try {
			loggedIn.setMemberType(MemberType.HEADADMIN);
			sessionCalendarFacade.addSession(session);
			listSessions.add(session);
			Set<Session> result = sessionCalendarFacade.getAllSessions();
			assertEquals(listSessions, result);
		} catch (UserNotAuthorizedException e) {
			e.printStackTrace();
		}

		Mockito.verify(sessionCalendarDummy).addSession(session);
		Mockito.verify(sessionRepoDummy).insert(session);
		Mockito.verify(sessionCalendarDummy).getSessions();
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
	}
	
	@ParameterizedTest
	@MethodSource("addSessionFixture")
	public void addSession_NotAuthorizedMember_ThrowsUserNotAuthorizedException(Session session, Set<Session> listSessions, Member loggedIn) {
		Mockito.doNothing().when(sessionCalendarDummy).addSession(session);
		Mockito.doNothing().when(sessionRepoDummy).insert(session);
		Mockito.when(sessionCalendarDummy.getSessions()).thenReturn(listSessions);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).
				thenReturn(loggedIn);
		
		loggedIn.setMemberType(MemberType.USER);
		assertThrows(UserNotAuthorizedException.class,() -> sessionCalendarFacade.addSession(session));

		Mockito.verify(sessionCalendarDummy).addSession(session);
		Mockito.verify(sessionRepoDummy).insert(session);
		Mockito.verify(sessionCalendarDummy).getSessions();
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
	}

	@ParameterizedTest
	@MethodSource("addSessionFixture")
	public void deleteSession_AuthorizedMember_DeletesSessionCorrectly(Session session, Set<Session> listSessions, Member loggedIn) {
		Mockito.doNothing().when(sessionCalendarDummy).deleteSession(session);
		Mockito.doNothing().when(sessionRepoDummy).delete(session);
		Mockito.when(sessionCalendarDummy.getSessions()).thenReturn(listSessions);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).
				thenReturn(loggedIn);
		
		try {
			loggedIn.setMemberType(MemberType.HEADADMIN);
			sessionCalendarFacade.deleteSession(session);
			listSessions.remove(session);
			Set<Session> result = sessionCalendarFacade.getAllSessions();
			assertEquals(listSessions, result);
		} catch (UserNotAuthorizedException e) {
			e.printStackTrace();
		}
		
		Mockito.verify(sessionCalendarDummy).deleteSession(session);
		Mockito.verify(sessionRepoDummy).delete(session);
		Mockito.verify(sessionCalendarDummy).getSessions();
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
	}
	
	@ParameterizedTest
	@MethodSource("addSessionFixture")
	public void deleteSession_NotAuthorizedMember_ThrowsUserNotAuthorizedException(Session session, Set<Session> listSessions, Member loggedIn) {
		Mockito.doNothing().when(sessionCalendarDummy).deleteSession(session);
		Mockito.doNothing().when(sessionRepoDummy).delete(session);
		Mockito.when(sessionCalendarDummy.getSessions()).thenReturn(listSessions);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).
				thenReturn(loggedIn);
		
		loggedIn.setMemberType(MemberType.USER);
		assertThrows(UserNotAuthorizedException.class, () -> sessionCalendarFacade.deleteSession(session));
		
		Mockito.verify(sessionCalendarDummy).deleteSession(session);
		Mockito.verify(sessionRepoDummy).delete(session);
		Mockito.verify(sessionCalendarDummy).getSessions();
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
	}

	private static Stream<Arguments> editSessionFixture() {
		return Stream.of(Arguments.of(new Session(),
				new Session(new Member("JohnDoe", "John", "Doe", MemberType.HEADADMIN), "title", "description", "name",
						LocalDateTime.of(2020, 5, 5, 18, 0), LocalDateTime.of(2020, 5, 5, 19, 0), "GSCHB3.016", 30),
				new HashSet<Session>(Arrays.asList(new Session())), new Member()));
	}

	@ParameterizedTest
	@MethodSource("editSessionFixture")
	public void editSession_AuthorizedMember_EditsSessionCorrectly(Session session, Session newSession, Set<Session> listSessions, Member loggedIn) {
		Mockito.doNothing().when(sessionCalendarDummy).addSession(session);
		Mockito.doNothing().when(sessionCalendarDummy).deleteSession(session);
		Mockito.when(sessionRepoDummy.update(session)).thenReturn(newSession);
		Mockito.when(sessionCalendarDummy.getSessions()).thenReturn(listSessions);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).
				thenReturn(loggedIn);
		
		try {
			loggedIn.setMemberType(MemberType.HEADADMIN);
			sessionCalendarFacade.editSession(session, newSession);
			listSessions.remove(session);
			listSessions.add(newSession);
			Set<Session> result = sessionCalendarFacade.getAllSessions();
			assertEquals(listSessions, result);
		} catch (UserNotAuthorizedException e) {
			e.printStackTrace();
		}

		Mockito.verify(sessionCalendarDummy).addSession(session);
		Mockito.verify(sessionCalendarDummy).deleteSession(session);
		Mockito.verify(sessionRepoDummy).update(session);
		Mockito.verify(sessionCalendarDummy).getSessions();
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
	}
	
	@ParameterizedTest
	@MethodSource("editSessionFixture")
	public void editSession_NotAuthorizedMember_ThrowsUserNotAuthorizedException(Session session, Session newSession, Set<Session> listSessions, Member loggedIn) {
		Mockito.doNothing().when(sessionCalendarDummy).addSession(session);
		Mockito.doNothing().when(sessionCalendarDummy).deleteSession(session);
		Mockito.when(sessionRepoDummy.update(session)).thenReturn(newSession);
		Mockito.when(sessionCalendarDummy.getSessions()).thenReturn(listSessions);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).
				thenReturn(loggedIn);
		
		loggedIn.setMemberType(MemberType.USER);
		assertThrows(UserNotAuthorizedException.class, () -> sessionCalendarFacade.editSession(session, newSession));
			
		Mockito.verify(sessionCalendarDummy).addSession(session);
		Mockito.verify(sessionCalendarDummy).deleteSession(session);
		Mockito.verify(sessionRepoDummy).update(session);
		Mockito.verify(sessionCalendarDummy).getSessions();
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
	}

}
