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
import persistence.MemberDao;
import persistence.MemberDaoJpa;

@ExtendWith(MockitoExtension.class)
public class MemberFacadeTest {

		@Mock
		private static MemberDao memberRepoDummy;
		
		@InjectMocks
		private static MemberFacade memberFacade;
		
		//Tests
		private static Stream<Arguments> addMember() {
			return Stream.of(
					Arguments.of("harm.de.weirdt", "Harm", "De Weirdt", MemberType.HEADADMIN)
			);
		}
		
		@ParameterizedTest
		@MethodSource("addMember")
		public void getMemberByUserName_GetsMemberByCorrectUserName(String username, String firstName, String lastName, MemberType memberType) {
			Mockito.when(memberRepoDummy.getMemberByUsername(username)).
					thenReturn(new Member(username, firstName, lastName, memberType));
			
			Member member = memberFacade.getMemberByUsername(username);
			assertEquals(username, member.getUsername());
			
			Mockito.verify(memberRepoDummy).getMemberByUsername(username);
		}
		
		private static Stream<Arguments> getAllMembersFixture() {
			return Stream.of(
					Arguments.of(new ArrayList<Member>(List.of(
							new Member("username1", "fn1", "ln1", MemberType.USER),
							new Member("username2", "fn2", "ln2", MemberType.USER),
							new Member("username3", "fn3", "ln3", MemberType.USER),
							new Member("username4", "fn4", "ln4", MemberType.USER),
							new Member("username5", "fn5", "ln5", MemberType.USER)
					)))
			);
		}
		
		@ParameterizedTest
		@MethodSource("getAllMembersFixture")
		public void getAllMembers_GetsAllMembersCorrectly(List<Member> listAllMembers) {
			Mockito.when(memberRepoDummy.findAll()).
					thenReturn(listAllMembers);
			
			List<Member> result = memberFacade.getAllMembers();
			assertEquals(listAllMembers, result);
			
			Mockito.verify(memberRepoDummy).findAll();
		}

}
