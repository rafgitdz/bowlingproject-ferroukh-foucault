package domain.model.team.league;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import domain.model.exception.LeagueException;
import domain.model.exception.TeamException;
import domain.model.team.Team;

@Stateless
public class LeagueFactory implements LeagueFactoryLocal {

	private final static String ERROR_TEAM_NUMBER_ODD = "You must have an even number of teams to create a league";
	private static final String ERROR_TEAM_NUMBER_NULL = "You have to select at least two teams to create a league";

	@EJB
	private RepositoryLeague repositoryLeague;

	@Override
	public League newLeague(String name) {

		League league = new League();
		league.setName(name);
		league.setSchedule(new Schedule());
		league.setTeams(new ArrayList<Team>());
		return league;
	}

	@Override
	public League buildLeague(String nameLeague) {

		League league = repositoryLeague.load(nameLeague);
		if (league == null) 
			throw new LeagueException(League.LEAGUE_NOT_EXIST);
		
		league.setTeams(new ArrayList<Team>());
		List<Team> listTeam = repositoryLeague.query(nameLeague);

		for (Team t : listTeam)
			league.addTeam(t);
		return league;
	}
	
	@Override
	public void startLeague(String leagueName) {
		League league = repositoryLeague.load(leagueName);
		
		if (league.getTeams().size() % 2 == 1)
			throw new TeamException(ERROR_TEAM_NUMBER_ODD);
		if (league.getTeams().size() == 0)
			throw new TeamException(ERROR_TEAM_NUMBER_NULL);

		
		league.startLeague();
		repositoryLeague.update(league);
	}
}
