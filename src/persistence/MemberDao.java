package persistence;

import javax.persistence.EntityNotFoundException;

import main.domain.Member;

public interface MemberDao extends GenericDao<Member> {
	public Member getMemberByUsername(String username) throws EntityNotFoundException;
}
