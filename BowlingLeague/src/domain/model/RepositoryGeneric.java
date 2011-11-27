package domain.model;

import java.util.List;

public interface RepositoryGeneric<T, TId> {

	public T load(TId id);

	public T save(T entity);

	public void delete(TId id);

	public List<T> loadAll();

}
