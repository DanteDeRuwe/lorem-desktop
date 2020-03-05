package persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmfHelper {
	private static final String PERSISTENCE_UNIT_NAME = "projectPU";
	private static EntityManagerFactory emf;
	
	
	public static EntityManagerFactory getEmf() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		return emf;
	}
}
