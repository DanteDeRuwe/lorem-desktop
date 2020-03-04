package persistence;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import main.domain.Session;

public class SessionDaoJpa extends GenericDaoJpa<Session> implements SessionDao{
	public SessionDaoJpa() {
		super(Session.class);
	}

	@Override
	public Session getSessionByTitle(String title) throws EntityNotFoundException {
		try {return em.createNamedQuery("Session.findByTitle", Session.class)
				.setParameter("sessionTitle", title)
				.getSingleResult();
		} catch (NoResultException ex) {
			throw new EntityNotFoundException();
		}
	}
}
