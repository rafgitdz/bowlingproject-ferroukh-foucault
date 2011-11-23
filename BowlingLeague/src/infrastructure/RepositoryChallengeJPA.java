package infrastructure;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import domain.model.league.Challenge;
import domain.model.league.RepositoryChallenge;

@Stateful
public class RepositoryChallengeJPA implements RepositoryChallenge {

	@PersistenceContext(unitName = "BowlingLeaguePU")
	public EntityManager em;

	@Override
	public Challenge save(Challenge challenge) {
		if (em.find(Challenge.class, challenge.getId()) == null)
			em.persist(challenge);
		else
			em.merge(challenge);
		return challenge;
	}

	@Override
	public Challenge load(int id) {
		return em.find(Challenge.class, id);
	}

	@Override
	public void delete(int id) {
		em.remove(em.merge(id));
	}

}
