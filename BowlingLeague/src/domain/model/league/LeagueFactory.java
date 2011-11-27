package domain.model.league;

import java.util.List;

import domain.model.team.Team;
import domain.model.team.TeamException;

public class LeagueFactory implements LeagueFactoryLocal {

	private final static String ERROR_TEAM_NUMBER_ODD = "You must have an even number of teams to create a league";
	private static final String ERROR_TEAM_NUMBER_NULL = "You have to select at least two teams to create a league";

	@Override
	public League newLeague(String name, List<Team> teams) {
		if (teams.size() % 2 == 1)
			throw new TeamException(ERROR_TEAM_NUMBER_ODD);
		if (teams.size() == 0)
			throw new TeamException(ERROR_TEAM_NUMBER_NULL);
		
		return new League(name, teams);
	}
}
