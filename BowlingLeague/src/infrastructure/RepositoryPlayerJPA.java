package infrastructure;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import domain.model.duel.Player;
import domain.model.duel.RepositoryPlayer;

@Stateful
public class RepositoryPlayerJPA implements RepositoryPlayer {

	@PersistenceContext(unitName = "BowlingLeaguePU")
	public EntityManager em;

	@Override
	public Player save(Player player) {

		if (em.find(Player.class, player.getName()) == null)
			em.persist(player);
		else
			em.merge(player);
		return player;
	}

	@Override
	public Player load(String name) {
		return em.find(Player.class, name);
	}

	@Override
	public void delete(Player player) {
		em.remove(em.merge(player));
	}

}
