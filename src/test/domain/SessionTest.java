package test.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import main.domain.Member;
import main.domain.MemberType;
import main.domain.Session;
import main.domain.SessionStatus;

public class SessionTest {

	private static Session session;
	private static Member exampleHeadAdmin;
	private static String exampleTitle;
	private static String exampleDescription;
	private static String exampleSpeakerName;
	private static LocalDateTime exampleStartDateTime;
	private static LocalDateTime exampleEndDateTime;
	private static String exampleLocation;
	private static int exampleCapacity;

	@BeforeAll
	public static void beforeAll() {
		exampleHeadAdmin = new Member("JohnDoe", "John", "Doe", MemberType.HEADADMIN);
		exampleTitle = "title";
		exampleDescription = "description";
		exampleSpeakerName = "name";
		exampleStartDateTime = LocalDateTime.of(2020, 5, 5, 18, 0);
		exampleEndDateTime = LocalDateTime.of(2020, 5, 5, 19, 0);
		exampleLocation = "GSCHB3.016";
		exampleCapacity = 30;
	}
	
	@BeforeEach
	public void beforeEach() {
		session = new Session(exampleHeadAdmin, exampleTitle, exampleDescription, exampleSpeakerName, 
				exampleStartDateTime, exampleEndDateTime, exampleLocation, exampleCapacity);
	}
	
	//Constructor tests
	@ParameterizedTest
	@NullAndEmptySource
	public void createSession_EmptyOrNullTitle_ThrowsIllegalArgumentException(String title) {
		assertThrows(
				IllegalArgumentException.class,
				() -> new Session(
						exampleHeadAdmin, title, exampleDescription, exampleSpeakerName,
						exampleStartDateTime, exampleEndDateTime, exampleLocation, exampleCapacity
				)
		);
	}

	@ParameterizedTest
	@NullSource
	public void createSession_NullLocation_ThrowsIllegalArgumentException(String location) {
		assertThrows(
				IllegalArgumentException.class, () -> new Session(
						exampleHeadAdmin, exampleTitle, exampleDescription,
						exampleSpeakerName, exampleStartDateTime, exampleEndDateTime, location, exampleCapacity
				)
		);
	}

	@ParameterizedTest
	@NullSource
	public void createSession_NullStartDateTime_ThrowsIllegalArgumentException(LocalDateTime startDateTime) {
		assertThrows(
				IllegalArgumentException.class, () -> new Session(
						exampleHeadAdmin, exampleTitle, exampleDescription,
						exampleSpeakerName, startDateTime, exampleEndDateTime, exampleLocation, exampleCapacity
				)
		);
	}

	@ParameterizedTest
	@NullSource
	public void createSession_NullEndDateTime_ThrowsIllegalArgumentException(LocalDateTime endDateTime) {
		assertThrows(
				IllegalArgumentException.class, () -> new Session(
						exampleHeadAdmin, exampleTitle, exampleDescription,
						exampleSpeakerName, exampleStartDateTime, endDateTime, exampleLocation, exampleCapacity
				)
		);
	}

	@Test
	public void createSession_StartDateTimeInPast_ThrowsIllegalArgumentException() {
		LocalDateTime startDateTime = LocalDateTime.of(2000, 5, 5, 18, 0);
		assertThrows(
				IllegalArgumentException.class, () -> new Session(
						exampleHeadAdmin, exampleTitle, exampleDescription,
						exampleSpeakerName, startDateTime, exampleEndDateTime, exampleLocation, exampleCapacity
				)
		);
	}

	@Test
	public void createSession_EndDateTimeInPast_ThrowsIllegalArgumentException() {
		LocalDateTime endDateTime = LocalDateTime.of(2000, 5, 5, 18, 0);
		assertThrows(
				IllegalArgumentException.class, () -> new Session(
						exampleHeadAdmin, exampleTitle, exampleDescription,
						exampleSpeakerName, exampleStartDateTime, endDateTime, exampleLocation, exampleCapacity
				)
		);
	}

	@Test
	public void createSession_StartDateLessThan1DayInFuture_ThrowsIllegalArgumentException() {
		LocalDateTime startDateTime = LocalDateTime.now().plusHours(23).plusMinutes(59);
		LocalDateTime endDateTime = startDateTime.plusHours(2);
		assertThrows(
				IllegalArgumentException.class, () -> new Session(
						exampleHeadAdmin, exampleTitle, exampleDescription,
						exampleSpeakerName, startDateTime, endDateTime, exampleLocation, exampleCapacity
				)
		);
	}

	@Test
	public void createSession_StartDateMoreThan1DayInFuture_ThrowsIllegalArgumentException() {
		LocalDateTime startDateTime = LocalDateTime.now().plusHours(24).plusMinutes(1);
		LocalDateTime endDateTime = startDateTime.plusHours(2);
		new Session(
				exampleHeadAdmin, exampleTitle, exampleDescription, exampleSpeakerName, startDateTime, endDateTime,
				exampleLocation,
				exampleCapacity
		);
	}

	@Test
	public void createSession_StartAndEndWithinLessThan30Minutes_ThrowsIllegalArgumentException() {
		LocalDateTime endDateTime = LocalDateTime.of(2020, 5, 5, 18, 29);
		assertThrows(
				IllegalArgumentException.class, () -> new Session(
						exampleHeadAdmin, exampleTitle, exampleDescription,
						exampleSpeakerName, exampleStartDateTime, endDateTime, exampleLocation, exampleCapacity
				)
		);
	}

	@Test
	public void createSession_StartAndEndWithinExactly30Minutes_CreatesSession() {
		new Session(
				exampleHeadAdmin, exampleTitle, exampleDescription, exampleSpeakerName, exampleStartDateTime,
				LocalDateTime.of(2020, 5, 5, 18, 30), exampleLocation, exampleCapacity
		);
	}
	
	//Title tests
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {" ", "\n", "\t", "     "})
	public void setTitle_NullOrEmptyTitle_ThrowsIllegalArgumentException(String title) {
		assertThrows(IllegalArgumentException.class, () -> session.setTitle(title));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"title 1", "title 2", "title 3"})
	public void setTitle_CorrectTitle_SetsTitle(String title) {
		session.setTitle(title);
		assertEquals(title, session.getTitle());
	}
	
	//SpeakerName tests
	@ParameterizedTest
	@ValueSource(strings = {"speakerName 1", "speakerName 2", "speakerName 3"})
	public void setSpeakerName_CorrectSpeakerName_SetsSpeakerName(String speakerName) {
		session.setSpeakerName(speakerName);
		assertEquals(speakerName, session.getSpeakerName());
	}
	
	//Start & end tests
	@ParameterizedTest
	@NullSource
	public void setStart_IncorrectStart_ThrowsIllegalArgumentException(LocalDateTime value) {
		assertThrows(IllegalArgumentException.class, () -> session.setStart(value));
	}
	
	//End tests
	@ParameterizedTest
	@NullSource
	public void setEnd_IncorrectEnd_ThrowsIllegalArgumentException(LocalDateTime value) {
		assertThrows(IllegalArgumentException.class, () -> session.setEnd(value));
	}
	
	//Location tests
	@ParameterizedTest
	@NullSource
	public void setLocation_IncorrectLocation_ThrowsIllegalArgumentException(String value) {
		assertThrows(IllegalArgumentException.class, () -> session.setLocation(value));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"location 1", "location 2", "location 3"})
	public void setLocation_CorrectLocation_SetsLocation(String location) {
		session.setLocation(location);
		assertEquals(location, session.getLocation());
	}
	
	//Capacity tests
	@ParameterizedTest
	@ValueSource(ints = {15, 25, 30})
	public void setCapacity_CorrectCapacity_SetsCapacity(int value) {
		session.setCapacity(value);
		assertEquals(value, session.getCapacity());
	}
	
	//Description tests
	@ParameterizedTest
	@ValueSource(strings = {"description 1", "description 2", "description 3"})
	public void setDescription_CorrectDescription_SetsDescription(String description) {
		session.setDescription(description);
		assertEquals(description, session.getDescription());
	}
	
	//SessionStatus tests
	private static Stream<Arguments> addStatusFixture() {
		return Stream.of(
				Arguments.of(SessionStatus.OPEN)
		);
	}
	
	@ParameterizedTest
	@MethodSource("addStatusFixture")
	public void setSessionStatus_SetsSessionStatus(SessionStatus sessionStatus) {
		session.setSessionStatus(sessionStatus);
		assertEquals(sessionStatus, session.getSessionStatus());
	}
	
	//ExternalLink tests
	@ParameterizedTest
	@ValueSource(strings = {"http://externalLink 1", "http://externalLink 2", "http://externalLink 3"})
	public void setExternalLink_CorrectExternalLinkWithHttp_SetsExternalLink(String externalLink) {
		session.setExternalLink(externalLink);
		assertEquals(externalLink, session.getExternalLink());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"externalLink 1", "externalLink 2", "externalLink 3"})
	public void setExternalLink_CorrectExternalLinkWithOutHttp_SetsExternalLink(String externalLink) {
		session.setExternalLink(externalLink);
		assertEquals("http://" + externalLink, session.getExternalLink());
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {" ", "   ", "\n", "\t"})
	public void setExternalLink_CorrectExternalLinkNullAndEmpty_SetsExternalLink(String externalLink) {
		session.setExternalLink(externalLink);
		assertEquals("", session.getExternalLink());
	}
	
	//Type test
	@ParameterizedTest
	@ValueSource(strings = {"type 1", "type 2", "type 3"})
	public void setType_CorrectType_SetsType(String type) {
		session.setType(type);
		assertEquals(type, session.getType());
	}
	
	//Statistics tests
	private static Stream<Arguments> addFixture() {
		return Stream.of(
				Arguments.of(new HashSet<Member>(Arrays.asList(
						new Member(),
						new Member(),
						new Member()
				)))
		);
	}
	
	@ParameterizedTest
	@MethodSource("addFixture")
	public void countAttendees_ReturnsCorrectAmountOfAttendees(Set<Member> attendees) {
		session.setAttendees(attendees);
		assertEquals(attendees.size(), session.countAttendees());
	}
	
	@ParameterizedTest
	@MethodSource("addFixture")
	public void countRegistrees_ReturnsCorrectAmountOfRegistrees(Set<Member> registrees) {
		session.setRegistrees(registrees);
		assertEquals(registrees.size(), session.countRegistrees());
	}

}
