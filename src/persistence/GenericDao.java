package persistence;

import java.util.List;

public interface GenericDao<T> {
	public List<T> findAll();

	public T get(Long id);

	public void update(T object);

	public void delete(T object);

	public void insert(T object);

	public boolean exists(Long id);
}