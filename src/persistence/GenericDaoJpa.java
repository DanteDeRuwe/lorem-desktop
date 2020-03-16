package persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GenericDaoJpa<T> implements GenericDao<T> {
	
	private static final String PERSISTENCE_UNIT_NAME = "projectPU";
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	protected static final EntityManager em = emf.createEntityManager();
	
	private final Class<T> type;
	
	public GenericDaoJpa(Class<T> type) {
		this.type = type;
	}
	
	public static void closePersistency() {
		em.close();
		emf.close();
	}
	
	public static void startTransaction() {
		em.getTransaction().begin();
	}
	
	public static void commitTransaction() {
		em.getTransaction().commit();
	}
	
	public static void rollbackTransaction() {
		em.getTransaction().rollback();
	}
	
	@Override
	public List<T> findAll() {
		return em.createQuery("select entity from " + type.getName() + " entity",type).getResultList();
	}

	@Override
	public T get(Long id) {
		T entity = em.find(type, id);
		return entity;
	}

	@Override
	public void update(T object) {
		startTransaction();
		em.merge(object);
		commitTransaction();
	}

	@Override
	public void delete(T object) {
		startTransaction();
		em.remove(em.merge(object));
		commitTransaction();
	}

	@Override
	public void insert(T object) {
		startTransaction();
		em.persist(object);
		commitTransaction();
	}

	@Override
	public boolean exists(Long id) {
		T entity = em.find(type, id);
		return entity != null;
	}

}