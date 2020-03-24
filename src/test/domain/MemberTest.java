package test.domain;

import static org.junit.jupiter.api.Assertions.*;

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
import org.junit.jupiter.params.provider.ValueSource;

import main.domain.Member;
import main.domain.MemberStatus;
import main.domain.MemberType;
import main.domain.Session;

public class MemberTest {

	private static Member exMember;
	private static String exUsername;
	private static String exFirstName;
	private static String exLastName;
	private static MemberType exMemberType;
	private static MemberStatus exMemberStatus;
	private static String exProfilePicPath;
	
	@BeforeAll
	public static void beforeAll() {
		exUsername = "username";
		exFirstName = "Arne";
		exLastName = "De Schrijver";
		exMemberType = MemberType.USER;
		exMemberStatus = MemberStatus.INACTIVE;
		exProfilePicPath = "proPicPath";
	}
	
	@BeforeEach
	public void beforeEach() {
		exMember = new Member(exUsername, exFirstName,
				exLastName, exMemberType);
	}
	
	//UserName tests
	@Test
	public void getUsername_GetsCorrectUsername() {
		assertEquals(exUsername, exMember.getUsername());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"username 1", "username 2", "username 3"})
	public void setUsername_SetsCorrectUsername(String value) {
		exMember.setUsername(value);
		assertEquals(value, exMember.getUsername());
	}
	
	//FirstName tests
	@Test
	public void getFirstName_GetsCorrectFirstName() {
		assertEquals(exFirstName, exMember.getFirstName());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"firstName 1", "firstName 2", "firstName 3"})
	public void setFirstName_SetsCorrectFirstName(String value) {
		exMember.setFirstName(value);
		assertEquals(value, exMember.getFirstName());
	}
	
	//LastName tests
	@Test
	public void getLastName_GetsCorrectLastName() {
		assertEquals(exLastName, exMember.getLastName());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"lastName 1", "lastName 2", "lastName 3"})
	public void setLastName_SetsCorrectLastName(String value) {
		exMember.setLastName(value);
		assertEquals(value, exMember.getLastName());
	}
	
	//FullName test
	@Test
	public void getFullName_GetsCorrectFullName() {
		assertEquals(exFirstName + " " + exLastName, exMember.getFullName());
	}
	
	//MemberStatus tests
	@Test
	public void getMemberStatus_GetsCorrectMemberStatus() {
		assertEquals(MemberStatus.ACTIVE, exMember.getMemberStatus());
	}
	
	@Test
	public void setMemberStatus_SetsCorrectMemberStatus() {
		exMember.setMemberStatus(exMemberStatus);
		assertEquals(exMemberStatus, exMember.getMemberStatus());
	}
	
	//MemberType test
	@Test
	public void setMemberType_SetsCorrectMemberType() {
		exMember.setMemberType(MemberType.ADMIN);
		assertEquals(MemberType.ADMIN, exMember.getMemberType());
	}
	
	//ProfilePicPath tests
	@Test
	public void getProfilePicPath_GetsCorrectProfilePicPath() {
		assertEquals("", exMember.getProfilePicPath());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"profilePicPath 1", "profilePicPath 2", "profilePicPath 3"})
	public void setProfilePicPath_SetsCorrectProfilePicPath(String value) {
		exMember.setProfilePicPath(value);
		assertEquals(value, exMember.getProfilePicPath());
	}
	
	//Statistics tests
	private static Stream<Arguments> addFixture() {
		return Stream.of(
				Arguments.of(new HashSet<Session>(Arrays.asList(
							new Session(),
							new Session(),
							new Session()
						)))
		);
	}
	
	@ParameterizedTest
	@MethodSource("addFixture")
	public void countAttendances_ReturnsCorrectAmountOfAttendances(Set<Session> attendances) {
		exMember.setAttendances(attendances);
		assertEquals(attendances.size(), exMember.countAttendances());
	}
	
	
	@ParameterizedTest
	@MethodSource("addFixture")
	public void countRegistrations_ReturnsCorrectAmountOfRegistrations(Set<Session> registrations) {
		exMember.setRegistrations(registrations);
		assertEquals(registrations.size(), exMember.countRegistrations());
	}
	
	private static Stream<Arguments> addMissedFixture() {
		return Stream.of(
				Arguments.of(new HashSet<Session>(Arrays.asList(
								new Session(),
								new Session(),
								new Session()
						)), 
							new HashSet<Session>(Arrays.asList(
								new Session(),
								new Session(),
								new Session(),
								new Session(),
								new Session()
						)))
		);
	}
	
	@ParameterizedTest
	@MethodSource("addMissedFixture")
	public void countMissedSessions_ReturnsCorrectAmountOfMissedSessions(Set<Session> attendances, Set<Session> registrations) {
		exMember.setAttendances(attendances);
		exMember.setRegistrations(registrations);
		assertEquals(registrations.size() - attendances.size(), exMember.countMissedSessions());
	}

}
