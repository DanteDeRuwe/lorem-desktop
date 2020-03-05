package persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class GenericDaoJpa<T> implements GenericDao<T> {
	
	private static final EntityManagerFactory emf = EmfHelper.getEmf();
	protected EntityManager em;
	
	private final Class<T> type;
	
	public GenericDaoJpa(Class<T> type) {
		this.type = type;
	}
	
	@Override
	public List<T> findAll() {
		startTransaction();
		List<T> list = em.createQuery("select entity from " + type.getName() + " entity",type).getResultList();
		finishTransaction();
		return list;
	}

	@Override
	public T get(Long id) {
		startTransaction();
		T entity = em.find(type, id);
		finishTransaction();
		return entity;
	}

	@Override
	public T update(T object) {
		startTransaction();
		T entity = em.merge(object);
		finishTransaction();
		return entity;
		
	}

	@Override
	public void delete(T object) {
		startTransaction();
		em.remove(em.merge(object));
		finishTransaction();
	}

	@Override
	public void insert(T object) {
		startTransaction();
		em.persist(object);
		finishTransaction();
	}

	@Override
	public boolean exists(Long id) {
		startTransaction();
		T entity = em.find(type, id);
		finishTransaction();
		return entity != null;
	}

	protected void startTransaction() {
		em = emf.createEntityManager();
		em.getTransaction().begin();
	}
	
	protected void finishTransaction() {
		em.getTransaction().commit();
		em.close();
	}
	
}
