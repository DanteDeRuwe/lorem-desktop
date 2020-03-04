package persistence;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

import main.domain.Member;
import main.domain.MemberType;
import main.domain.Session;


public class SessionDao implements Dao<Session> {
	private static final String PERSISTENCE_UNIT_NAME = "projectPU";
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	@Override
	public Session findById(long id) {
		EntityManager em = factory.createEntityManager();
		Session session = em.find(Session.class, id);
		if (session == null) {
			throw new EntityNotFoundException("Can't find session for id: " + id);
		}
		em.close();
		return session;
	}
	@Override
	public List<Session> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void save(Session session) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(Session session, String[] params) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(Session session) {
		// TODO Auto-generated method stub
		
	}
	
	//just for testing, remove later
	public void addTestSession() {
		EntityManager em = factory.createEntityManager();
    	em.getTransaction().begin();
        Session session = new Session(new Member("testUsername", "john", "doe", MemberType.USER), "testTitle", "Ms. Speaker", LocalDateTime.of(2020,4,1,12,0), LocalDateTime.of(2020,4,1,16,0), "testLocation", 20);
        
        em.persist(session);
        em.getTransaction().commit();

        em.close();
	}
}
