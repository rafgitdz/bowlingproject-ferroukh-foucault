package infrastructure;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.Query;

import domain.model.team.Team;
import domain.model.team.league.League;
import domain.model.team.league.RepositoryLeague;

@Stateful
public class RepositoryLeagueJPA extends RepositoryGenericJPA<League, String>
		implements RepositoryLeague {

	@SuppressWarnings("unchecked")
	@Override
	public List<Team> query(String leagueName) {

		Query query = em
				.createQuery("SELECT t from Team t where league_name = :name");
		query.setParameter("name", leagueName);

		return (List<Team>) query.getResultList();
	}
}
