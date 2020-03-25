package test.domain.facades;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import main.domain.facades.MemberFacade;
import main.exceptions.UserNotAuthorizedException;
import persistence.MemberDao;
import persistence.MemberDaoJpa;

@ExtendWith(MockitoExtension.class)
public class MemberFacadeTest {

	@Mock
	private MemberDao memberRepoDummy;

	@InjectMocks
	private MemberFacade memberFacade;

	// Tests
	private static Stream<Arguments> addMember() {
		return Stream.of(Arguments.of("harm.de.weirdt", "Harm", "De Weirdt", MemberType.HEADADMIN));
	}

	@ParameterizedTest
	@MethodSource("addMember")
	public void getMemberByUserName_GetsMemberByCorrectUserName(String username, String firstName, String lastName,
			MemberType memberType) {
		Mockito.when(memberRepoDummy.getMemberByUsername(username))
				.thenReturn(new Member(username, firstName, lastName, memberType));

		Member member = memberFacade.getMemberByUsername(username);
		assertEquals(username, member.getUsername());

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
				new Member("username5", "fn5", "ln5", MemberType.USER)));
	}

	@ParameterizedTest
	@MethodSource("addMemberFixture")
	public void addMember_AddsMemberCorrectly(List<Member> listAllMembers, Member member) {
		Mockito.when(memberRepoDummy.findAll()).thenReturn(listAllMembers);
		Mockito.doNothing().when(memberRepoDummy).insert(member);

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
	}

	private static Stream<Arguments> editMemberFixture() {
		return Stream.of(Arguments.of(
				new ArrayList<Member>(List.of(new Member("username1", "fn1", "ln1", MemberType.USER),
						new Member("username2", "fn2", "ln2", MemberType.USER),
						new Member("username3", "fn3", "ln3", MemberType.USER),
						new Member("username4", "fn4", "ln4", MemberType.USER))),
				new Member("username1", "fn1", "ln1", MemberType.USER),
				new Member("username5", "fn5", "ln5", MemberType.USER)));
	}

	@ParameterizedTest
	@MethodSource("editMemberFixture")
	public void editMember_EditsMemberCorrectly(List<Member> listAllMembers, Member member, Member newMember) {
		Mockito.when(memberRepoDummy.findAll()).thenReturn(listAllMembers);
		Mockito.doNothing().when(memberRepoDummy).insert(member);
		Mockito.doNothing().when(memberRepoDummy).delete(member);

		try {
			memberFacade.editMember(member, newMember);
		} catch (UserNotAuthorizedException e) {
			e.printStackTrace();
		}

		listAllMembers.remove(member);
		listAllMembers.add(newMember);
		List<Member> result = memberFacade.getAllMembers();
		assertEquals(listAllMembers, result);

		Mockito.verify(memberRepoDummy).findAll();
		Mockito.verify(memberRepoDummy).insert(member);
		Mockito.verify(memberRepoDummy).delete(member);
	}

	private static Stream<Arguments> deleteMemberFixture() {
		return Stream.of(Arguments.of(
				new ArrayList<Member>(List.of(new Member("username1", "fn1", "ln1", MemberType.USER),
						new Member("username2", "fn2", "ln2", MemberType.USER),
						new Member("username3", "fn3", "ln3", MemberType.USER),
						new Member("username4", "fn4", "ln4", MemberType.USER))),
				new Member("username1", "fn1", "ln1", MemberType.USER)));
	}

	@ParameterizedTest
	@MethodSource("deleteMemberFixture")
	public void deleteMember_DeletesMemberCorrectly(List<Member> listAllMembers, Member member) {
		Mockito.when(memberRepoDummy.findAll()).thenReturn(listAllMembers);
		Mockito.doNothing().when(memberRepoDummy).delete(member);

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
	}

}
