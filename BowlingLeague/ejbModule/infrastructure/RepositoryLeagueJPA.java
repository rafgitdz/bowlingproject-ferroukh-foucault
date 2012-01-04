package infrastructure;

import java.util.List;

import javax.ejb.Stateful;

import domain.model.team.Team;
import domain.model.team.league.League;
import domain.model.team.league.RepositoryLeague;

@Stateful
public class RepositoryLeagueJPA extends RepositoryGenericJPA<League, String>
		implements RepositoryLeague {

	@Override
	public List<Team> getTeams(String leagueName) {
		League league = em.find(League.class, leagueName);
		
		List<Team> teams = league.getTeams();
		teams.size();
		return teams;
	}
}
