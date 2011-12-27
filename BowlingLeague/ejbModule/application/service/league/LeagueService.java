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

	private static final String UNKNOWN_LEAGUE = "The league doesn't exist !";
	
	@EJB
	private LeagueFactoryLocal leagueFactory;
	@EJB
	private RepositoryLeague repositoryLeague;
	@EJB
	private RepositoryTeam repositoryTeam;


	@Override
	public void deleteLeague(String name) {

		League league = repositoryLeague.load(name);
		if (league == null)
			throw new LeagueException(UNKNOWN_LEAGUE);
		
		for (Team t : league.getTeams()) {
			t.setLeague(null);
			repositoryTeam.update(t);
		}
		repositoryLeague.delete(name);
	}

	@Override
	public void startLeague(String leagueName) {
		
		League league = repositoryLeague.load(leagueName);
		league = leagueFactory.rebuildLeague(league); 
		if (league == null)
			 throw new LeagueException(UNKNOWN_LEAGUE);
		league.startLeague();
		repositoryLeague.update(league);
	}

	@Override
	public List<Team> getTeams(String leagueName) {

		League league = repositoryLeague.load(leagueName);
		if (league == null)
			 throw new LeagueException(UNKNOWN_LEAGUE);
		return league.getTeams();
	}
}
