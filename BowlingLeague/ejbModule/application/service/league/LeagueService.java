package application.service.league;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import domain.model.exception.LeagueException;
import domain.model.team.RepositoryTeam;
import domain.model.team.Team;
import domain.model.team.league.League;
import domain.model.team.league.LeagueFactoryLocal;
import domain.model.team.league.RepositoryLeague;

@Stateless
public class LeagueService implements LeagueServiceRemote {

	@EJB
	private RepositoryLeague repositoryLeague;
	@EJB
	private RepositoryTeam repositoryTeam;
	
	@EJB
	private LeagueFactoryLocal leagueFactory;


	@Override
	public void deleteLeague(String name) {

		League league = repositoryLeague.load(name);
		if (league == null)
			throw new LeagueException(League.LEAGUE_NOT_EXIST);
		
		for (Team t : league.getTeams()) {
			t.setLeague(null);
			repositoryTeam.update(t);
		}
		repositoryLeague.delete(name);
	}

	@Override
	public void startLeague(String name) {
		
		leagueFactory.startLeague(name);
	}

	@Override
	public List<Team> getTeams(String leagueName) {

		League league = repositoryLeague.load(leagueName);
		if (league == null)
			 throw new LeagueException(League.LEAGUE_NOT_EXIST);
		return league.getTeams();
	}
}
