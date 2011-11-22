package infrastructure;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import domain.model.challenge.Team;
import domain.model.challenge.RepositoryTeam;

@Stateful
public class RepositoryTeamJPA implements RepositoryTeam {

	@PersistenceContext(unitName = "BowlingLeaguePU")
	public EntityManager em;

	@Override
	public Team save(Team team) {
		if (em.find(Team.class, team.getName()) == null)
			em.persist(team);
		else
			em.merge(team);
		return team;
	}

	@Override
	public Team load(String name) {
		return em.find(Team.class, name);
	}

	@Override
	public void delete(Team team) {
		em.remove(em.merge(team.getName()));
	}

}
