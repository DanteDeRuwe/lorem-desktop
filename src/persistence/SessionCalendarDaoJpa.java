package persistence;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import main.domain.SessionCalendar;

public class SessionCalendarDaoJpa extends GenericDaoJpa<SessionCalendar> implements SessionCalendarDao{

	public SessionCalendarDaoJpa() {
		super(SessionCalendar.class);
	}

	@Override
	public SessionCalendar getCurrentSessionCalendar() {
		try {return em.createNamedQuery("SessionCalendar.getCurrentSessionCalendar", SessionCalendar.class)
				.getSingleResult();
		} catch (NoResultException ex) {
			throw new EntityNotFoundException();
		}
	}

}
