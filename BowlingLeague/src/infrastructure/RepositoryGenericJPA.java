package infrastructure;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import domain.model.RepositoryGeneric;

public class RepositoryGenericJPA<T, TId> implements RepositoryGeneric<T, TId> {

	@PersistenceContext(unitName = "BowlingLeaguePU")
	protected EntityManager em;
	private Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public RepositoryGenericJPA() {
		
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public T load(TId id) {
		return em.find(persistentClass, id);
	}

	@Override
	public void delete(TId id) {
		
		T entity = em.find(persistentClass, id);
		em.remove(entity);
	}

	@Override
	public List<T> loadAll() {
		@SuppressWarnings("unchecked")
		List<T> entities = em.createQuery("SELECT FROM" + persistentClass.getName()).getResultList();
		return entities;
	}
	
	@Override
	public T save(T entity) {
		entity = em.merge(entity);
		em.persist(entity);
		
		return entity;
	}


}
