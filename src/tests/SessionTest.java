package tests;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import domain.Location;
import domain.Member;
import domain.MemberType;
import domain.Session;

class SessionTest {

	static Member exampleHeadAdmin;
	static LocalDateTime exampleStartDateTime;
	static LocalDateTime exampleEndDateTime;
	static Location exampleLocation;

	@BeforeAll
	static void beforeAll() {
		exampleHeadAdmin = new Member("JohnDoe", "John", "Doe", MemberType.HEADADMIN);
		exampleStartDateTime = LocalDateTime.of(2020, 5, 5, 18, 0);
		exampleEndDateTime = LocalDateTime.of(2020, 5, 5, 19, 0);
		exampleLocation = new Location("GSCHBAUD", 50);
	}

	@ParameterizedTest
	@NullAndEmptySource
	void createSession_EmptyOrNullTitle_ThrowsIllegalArgumentException(String title) {
		assertThrows(IllegalArgumentException.class, () -> new Session(exampleHeadAdmin, title, "Example Speaker",
				exampleStartDateTime, exampleEndDateTime, exampleLocation));
	}

	@ParameterizedTest
	@NullSource
	void createSession_NullLocation_ThrowsIllegalArgumentException(Location location) {
		assertThrows(IllegalArgumentException.class, () -> new Session(exampleHeadAdmin, "Example Event",
				"Example Speaker", exampleStartDateTime, exampleEndDateTime, location));
	}

	@ParameterizedTest
	@NullSource
	void createSession_NullStartDateTime_ThrowsIllegalArgumentException(LocalDateTime startDateTime) {
		assertThrows(IllegalArgumentException.class, () -> new Session(exampleHeadAdmin, "Example Event",
				"Example Speaker", startDateTime, exampleEndDateTime, exampleLocation));
	}

	@ParameterizedTest
	@NullSource
	void createSession_NullEndDateTime_ThrowsIllegalArgumentException(LocalDateTime endDateTime) {
		assertThrows(IllegalArgumentException.class, () -> new Session(exampleHeadAdmin, "Example Event",
				"Example Speaker", exampleStartDateTime, endDateTime, exampleLocation));
	}

	@Test
	void createSession_StartDateTimeInPast_ThrowsIllegalArgumentException() {
		LocalDateTime startDateTime = LocalDateTime.of(2000, 5, 5, 18, 0);
		assertThrows(IllegalArgumentException.class, () -> new Session(exampleHeadAdmin, "Example Event",
				"Example Speaker", startDateTime, exampleEndDateTime, exampleLocation));
	}

	@Test
	void createSession_EndDateTimeInPast_ThrowsIllegalArgumentException() {
		LocalDateTime endDateTime = LocalDateTime.of(2000, 5, 5, 18, 0);
		assertThrows(IllegalArgumentException.class, () -> new Session(exampleHeadAdmin, "Example Event",
				"Example Speaker", exampleStartDateTime, endDateTime, exampleLocation));
	}

	@Test
	void createSession_StartDateLessThan1DayInFuture_ThrowsIllegalArgumentException() {
		LocalDateTime startDateTime = LocalDateTime.now().plusHours(23).plusMinutes(59);
		LocalDateTime endDateTime = startDateTime.plusHours(2);
		assertThrows(IllegalArgumentException.class, () -> new Session(exampleHeadAdmin, "Example Event",
				"Example Speaker", startDateTime, endDateTime, exampleLocation));
	}

	@Test
	void createSession_StartDateMoreThan1DayInFuture_ThrowsIllegalArgumentException() {
		LocalDateTime startDateTime = LocalDateTime.now().plusHours(24).plusMinutes(1);
		LocalDateTime endDateTime = startDateTime.plusHours(2);
		new Session(exampleHeadAdmin, "Example Event", "Example Speaker", startDateTime, endDateTime, exampleLocation);
	}

	@Test
	void createSession_StartAndEndWithinLessThan30Minutes_ThrowsIllegalArgumentException() {
		LocalDateTime endDateTime = LocalDateTime.of(2020, 5, 5, 18, 29);
		assertThrows(IllegalArgumentException.class, () -> new Session(exampleHeadAdmin, "Example Event",
				"Example Speaker", exampleStartDateTime, endDateTime, exampleLocation));
	}

	@Test
	void createSession_StartAndEndWithinExactly30Minutes_CreatesSession() {
		new Session(exampleHeadAdmin, "Example Title", "Example Speaker", exampleStartDateTime,
				LocalDateTime.of(2020, 5, 5, 18, 30), exampleLocation);
	}

}
