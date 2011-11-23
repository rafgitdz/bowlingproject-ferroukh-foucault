package infrastructure;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import domain.model.game.Game;
import domain.model.game.RepositoryGame;

@Stateful
public class RepositoryGameJPA implements RepositoryGame {

	@PersistenceContext(unitName = "BowlingLeaguePU")
	public EntityManager em;

	public RepositoryGameJPA() {
	}

	@Override
	public Game save(Game obj) {

		if (em.find(Game.class, obj.getId()) == null) {
			em.persist(obj);
		} else {
			em.merge(obj);
		}

		return obj;
	}

	@Override
	public Game load(int id) {
		// return em.find((Class<T>) (((ParameterizedType)
		// getClass().getGenericSuperclass()).getActualTypeArguments()[0]), id);
		return em.find(Game.class, id);
	}

	@Override
	public void delete(Game game) {
		em.remove(em.merge(game));
	}
}
