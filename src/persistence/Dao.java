package persistence;

import java.util.List;


public interface Dao<T> {
    
    T findById(long id);
     
    List<T> getAll();
     
    void save(T t);
     
    void update(T t, String[] params);
     
    void delete(T t);
}