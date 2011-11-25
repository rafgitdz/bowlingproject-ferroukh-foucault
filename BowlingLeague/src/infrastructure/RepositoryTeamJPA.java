package infrastructure;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import domain.model.team.RepositoryTeam;
import domain.model.team.Team;

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
	public void delete(String name) {
		em.remove(em.merge(name));
	}

	@Override
	public void clearAll() {
		em.clear();
	}
	
	
}
