package test.domain.facades;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import main.domain.Member;
import main.domain.MemberType;
import main.domain.facades.LoggedInMemberManager;
import main.domain.facades.MemberFacade;
import main.exceptions.MustBeAtLeastOneHeadAdminException;
import main.exceptions.UserNotAuthorizedException;
import persistence.MemberDao;

@ExtendWith(MockitoExtension.class)
public class MemberFacadeTest {

	@Mock
	private MemberDao memberRepoDummy;
	
	@Mock
	private LoggedInMemberManager loggedInMemberManagerDummy;

	@InjectMocks
	private MemberFacade memberFacade;

	// Tests
	private static Stream<Arguments> addMember() {
		return Stream.of(Arguments.of("harm.de.weirdt", new Member("harm.de.weirdt", "Harm", "De Weirdt", MemberType.HEADADMIN)));
	}

	@ParameterizedTest
	@MethodSource("addMember")
	public void getMemberByUserName_GetsMemberByCorrectUserName(String username, Member member) {
		Mockito.when(memberRepoDummy.getMemberByUsername(username))
				.thenReturn(member);
		
		assertEquals(member, memberFacade.getMemberByUsername(username));

		Mockito.verify(memberRepoDummy).getMemberByUsername(username);

		/*
		 * TODO: Mock loggedInMember here somewhere?
		 */
	}

	private static Stream<Arguments> getAllMembersFixture() {
		return Stream
				.of(Arguments.of(new ArrayList<Member>(List.of(new Member("username1", "fn1", "ln1", MemberType.USER),
						new Member("username2", "fn2", "ln2", MemberType.USER),
						new Member("username3", "fn3", "ln3", MemberType.USER),
						new Member("username4", "fn4", "ln4", MemberType.USER),
						new Member("username5", "fn5", "ln5", MemberType.USER)))));
	}

	@ParameterizedTest
	@MethodSource("getAllMembersFixture")
	public void getAllMembers_GetsAllMembersCorrectly(List<Member> listAllMembers) {
		Mockito.when(memberRepoDummy.findAll()).thenReturn(listAllMembers);

		List<Member> result = memberFacade.getAllMembers();
		assertEquals(listAllMembers, result);

		Mockito.verify(memberRepoDummy).findAll();
	}

	private static Stream<Arguments> addMemberFixture() {
		return Stream.of(Arguments.of(
				new ArrayList<Member>(List.of(new Member("username1", "fn1", "ln1", MemberType.USER),
						new Member("username2", "fn2", "ln2", MemberType.USER),
						new Member("username3", "fn3", "ln3", MemberType.USER),
						new Member("username4", "fn4", "ln4", MemberType.USER))),
				new Member("username5", "fn5", "ln5", MemberType.USER),
				new Member()));
	}

	@ParameterizedTest
	@MethodSource("addMemberFixture")
	public void addMember_AuthorizedMember_AddsMemberCorrectly(List<Member> listAllMembers, Member member, Member loggedIn) {
		Mockito.when(memberRepoDummy.findAll()).thenReturn(listAllMembers);
		Mockito.doNothing().when(memberRepoDummy).insert(member);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).
				thenReturn(loggedIn);
		
		loggedIn.setMemberType(MemberType.HEADADMIN);
		
		try {
			memberFacade.addMember(member);
		} catch (UserNotAuthorizedException e) {
			e.printStackTrace();
		}

		listAllMembers.add(member);
		List<Member> result = memberFacade.getAllMembers();
		assertEquals(listAllMembers, result);

		Mockito.verify(memberRepoDummy).findAll();
		Mockito.verify(memberRepoDummy).insert(member);
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
	}
	
	@ParameterizedTest
	@MethodSource("addMemberFixture")
	public void addMember_AuthorizedMember_ThrowsUserNotAuthorizedException(List<Member> listAllMembers, Member member, Member loggedIn) {
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).
				thenReturn(loggedIn);
		
		loggedIn.setMemberType(MemberType.USER);
		
		assertThrows(UserNotAuthorizedException.class, () -> memberFacade.addMember(member));
		
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
	}

	private static Stream<Arguments> editMemberFixture() {
		return Stream.of(Arguments.of(
				new ArrayList<Member>(List.of(new Member("username1", "fn1", "ln1", MemberType.USER),
						new Member("username2", "fn2", "ln2", MemberType.USER),
						new Member("username3", "fn3", "ln3", MemberType.USER),
						new Member("username4", "fn4", "ln4", MemberType.HEADADMIN))),
				new Member("username1", "fn1", "ln1", MemberType.USER),
				new Member("username5", "fn5", "ln5", MemberType.USER),
				"pass",
				new Member())
		);
	}

	@ParameterizedTest
	@MethodSource("editMemberFixture")
	public void editMember_AuthorizedMember_EditsMemberCorrectly(List<Member> listAllMembers, Member member, Member newMember, String password, Member loggedIn) {
		Mockito.when(memberRepoDummy.findAll()).thenReturn(listAllMembers);
		Mockito.when(memberRepoDummy.update(member)).thenReturn(newMember);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).thenReturn(loggedIn);

		loggedIn.setMemberType(MemberType.HEADADMIN);
		
		try {
			memberFacade.editMember(member, newMember, password);
		} catch (UserNotAuthorizedException e) {
			e.printStackTrace();
		} catch (MustBeAtLeastOneHeadAdminException e) {
			e.printStackTrace();
		}

		listAllMembers.remove(member);
		listAllMembers.add(newMember);
		List<Member> result = memberFacade.getAllMembers();
		assertEquals(listAllMembers, result);

		Mockito.verify(memberRepoDummy).findAll();
		Mockito.verify(memberRepoDummy).update(member);
		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
	}
	
	@ParameterizedTest
	@MethodSource("editMemberFixture")
	public void editMember_NotAuthorizedMember_ThrowsUserNotAuthorizedException(List<Member> listAllMembers, Member member, Member newMember, String password, Member loggedIn) {
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).
				thenReturn(loggedIn);

		loggedIn.setMemberType(MemberType.USER);
		
		assertThrows(UserNotAuthorizedException.class, () -> memberFacade.editMember(member, newMember, password));

		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
	}

	private static Stream<Arguments> deleteMemberFixture() {
		return Stream.of(Arguments.of(
				new ArrayList<Member>(List.of(new Member("username1", "fn1", "ln1", MemberType.USER),
						new Member("username2", "fn2", "ln2", MemberType.USER),
						new Member("username3", "fn3", "ln3", MemberType.USER),
						new Member("username4", "fn4", "ln4", MemberType.USER))),
				new Member("username1", "fn1", "ln1", MemberType.USER),
				new Member()));
	}

	@ParameterizedTest
	@MethodSource("deleteMemberFixture")
	public void deleteMember_AuthorizedMember_DeletesMemberCorrectly(List<Member> listAllMembers, Member member, Member loggedIn) {
		Mockito.when(memberRepoDummy.findAll()).thenReturn(listAllMembers);
		Mockito.doNothing().when(memberRepoDummy).delete(member);
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).
				thenReturn(loggedIn);

		loggedIn.setMemberType(MemberType.HEADADMIN);
		
		try {
			memberFacade.deleteUser(member);
		} catch (UserNotAuthorizedException e) {
			e.printStackTrace();
		}

		listAllMembers.remove(member);
		List<Member> result = memberFacade.getAllMembers();
		assertEquals(listAllMembers, result);

		Mockito.verify(memberRepoDummy).findAll();
		Mockito.verify(memberRepoDummy).delete(member);
		Mockito.verify(loggedInMemberManagerDummy, Mockito.times(2)).getLoggedInMember();
	}
	
	@ParameterizedTest
	@MethodSource("deleteMemberFixture")
	public void deleteMember_NotAuthorizedMember_ThrowsUserNotAuthorizedException(List<Member> listAllMembers, Member member, Member loggedIn) {
		Mockito.when(loggedInMemberManagerDummy.getLoggedInMember()).
				thenReturn(loggedIn);

		loggedIn.setMemberType(MemberType.USER);
		
		assertThrows(UserNotAuthorizedException.class, () -> memberFacade.deleteUser(member));

		Mockito.verify(loggedInMemberManagerDummy).getLoggedInMember();
	}

}
