package persistence;

import javax.persistence.EntityNotFoundException;
import main.domain.Session;


public interface SessionDao extends GenericDao<Session> {
	public Session getSessionByTitle(String title) throws EntityNotFoundException;
}
