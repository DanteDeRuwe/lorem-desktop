package persistence;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import main.domain.Member;

public class MemberDaoJpa extends GenericDaoJpa<Member> implements MemberDao {

	public MemberDaoJpa() {
		super(Member.class);
	}

	@Override
	public Member getMemberByUsername(String username) throws EntityNotFoundException {
		try {
			return em.createNamedQuery("Member.findByUsername", Member.class).setParameter("username", username)
					.getSingleResult();
		} catch (NoResultException ex) {
			throw new EntityNotFoundException();
		}
	}

}
