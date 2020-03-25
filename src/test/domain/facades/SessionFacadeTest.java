package test.domain.facades;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import main.domain.Announcement;
import main.domain.Member;
import main.domain.MemberType;
import main.domain.Session;
import main.domain.facades.LoggedInMemberManager;
import main.domain.facades.SessionFacade;
import main.exceptions.UserNotAuthorizedException;
import persistence.GenericDaoJpa;

@ExtendWith(MockitoExtension.class)
public class SessionFacadeTest {

	@Mock
	private GenericDaoJpa<Announcement> announcementRepoDummy;
	
	@Mock
	private Session sessionDummy;
	
	@Mock
	private LoggedInMemberManager loggedInMemberManagerDummy;
	
	@InjectMocks
	private SessionFacade sessionFacade;
	
	private static Stream<Arguments> addAnnouncementFixture() {
		return Stream.of(
				Arguments.of(new Member(),
						new Announcement(),
						new HashSet<Announcement>(Arrays.asList(new Announcement())))
		);
	}
	
	@ParameterizedTest
	@MethodSource("addAnnouncementFixture")
	public void addAnnouncement_AuthorizedUser_AddsAnnouncementCorrectly(Member loggedIn, Announcement a, Set<Announcement> aSet) {
		Mockito.when(sessionDummy.getAnnouncements()).thenReturn(aSet);
		Mockito.doNothing().when(announcementRepoDummy).insert(a);
		Mockito.doNothing().when(sessionDummy).addAnnouncement(a);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).thenReturn(loggedIn);
		
		loggedIn.setMemberType(MemberType.HEADADMIN);
		
		try {
			sessionFacade.addAnnouncement(a, sessionDummy);
		} catch(UserNotAuthorizedException e) {
			e.printStackTrace();
		}
		
		aSet.add(a);
		assertEquals(aSet, sessionDummy.getAnnouncements());
		
		Mockito.verify(sessionDummy).getAnnouncements();
		Mockito.verify(sessionDummy).addAnnouncement(a);
		Mockito.verify(announcementRepoDummy).insert(a);
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
		
	}
	
	@ParameterizedTest
	@MethodSource("addAnnouncementFixture")
	public void addAnnouncement_NotAuthorizedUser_ThrowsUserNotAuthorizedMemberException(Member loggedIn, Announcement a, Set<Announcement> aSet) {
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).thenReturn(loggedIn);
		
		loggedIn.setMemberType(MemberType.USER);
		
		assertThrows(UserNotAuthorizedException.class, () -> sessionFacade.addAnnouncement(a, sessionDummy));
		
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
		
	}
	
	@ParameterizedTest
	@MethodSource("addAnnouncementFixture")
	public void removeAnnouncement_AuthorizedUser_RemovesAnnouncementCorrectly(Member loggedIn, Announcement a, Set<Announcement> aSet) {
		Mockito.when(sessionDummy.getAnnouncements()).thenReturn(aSet);
		Mockito.doNothing().when(announcementRepoDummy).delete(a);
		Mockito.doNothing().when(sessionDummy).removeAnnouncement(a);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).thenReturn(loggedIn);
		
		loggedIn.setMemberType(MemberType.HEADADMIN);
		
		try {
			sessionFacade.removeAnnouncement(a, sessionDummy);
		} catch(UserNotAuthorizedException e) {
			e.printStackTrace();
		}
		
		aSet.remove(a);
		assertEquals(aSet, sessionDummy.getAnnouncements());
		
		Mockito.verify(sessionDummy).getAnnouncements();
		Mockito.verify(sessionDummy).removeAnnouncement(a);
		Mockito.verify(announcementRepoDummy).delete(a);
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
		
	}
	
	@ParameterizedTest
	@MethodSource("addAnnouncementFixture")
	public void removeAnnouncement_NotAuthorizedUser_ThrowsUserNotAuthorizedMemberException(Member loggedIn, Announcement a, Set<Announcement> aSet) {
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).thenReturn(loggedIn);
		
		loggedIn.setMemberType(MemberType.USER);
		
		assertThrows(UserNotAuthorizedException.class, () -> sessionFacade.removeAnnouncement(a, sessionDummy));
		
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
		
	}
	
	private static Stream<Arguments> editAnnouncementFixture() {
		return Stream.of(
				Arguments.of(new Member(),
						new Announcement(),
						new Announcement(new Member(), "text", "title"),
						new HashSet<Announcement>(Arrays.asList(new Announcement())))
		);
	}
	
	@ParameterizedTest
	@MethodSource("editAnnouncementFixture")
	public void editAnnouncement_AuthorizedUser_editsAnnouncementCorrectly(Member loggedIn, Announcement a, Announcement a2, Set<Announcement> aSet) {
		Mockito.when(sessionDummy.getAnnouncements()).thenReturn(aSet);
		Mockito.when(announcementRepoDummy.update(a)).thenReturn(a2);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).thenReturn(loggedIn);
		
		loggedIn.setMemberType(MemberType.HEADADMIN);
		
		try {
			sessionFacade.editAnnouncement(a, a2, sessionDummy);
		} catch(UserNotAuthorizedException e) {
			e.printStackTrace();
		}
		
		aSet.remove(a);
		aSet.add(a2);
		assertEquals(aSet, sessionDummy.getAnnouncements());
		
		Mockito.verify(sessionDummy).getAnnouncements();
		Mockito.verify(announcementRepoDummy).update(a);
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
		
	}
	
	@ParameterizedTest
	@MethodSource("editAnnouncementFixture")
	public void editAnnouncement_NotAuthorizedUser_ThrowsUserNotAuthorizedMemberException(Member loggedIn, Announcement a, Announcement a2, Set<Announcement> aSet) {
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).thenReturn(loggedIn);
		
		loggedIn.setMemberType(MemberType.USER);
		
		assertThrows(UserNotAuthorizedException.class, () -> sessionFacade.editAnnouncement(a, a2, sessionDummy));
		
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
		
	}
	
}
