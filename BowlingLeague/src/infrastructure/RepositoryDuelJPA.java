package infrastructure;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import domain.model.league.Duel;
import domain.model.league.RepositoryDuel;

public class RepositoryDuelJPA implements RepositoryDuel {

	@PersistenceContext(unitName = "BowlingLeaguePU")
	public EntityManager em;

	@Override
	public Duel save(Duel duel) {
		
		if (em.find(Duel.class, duel.getId()) == null)
			em.persist(duel);
		else
			em.merge(duel);
		return duel;
	}

	@Override
	public Duel load(int id) {
		return em.find(Duel.class, id);
	}

	@Override
	public void delete(int id) {
		em.remove(em.merge(id));
	}
}
