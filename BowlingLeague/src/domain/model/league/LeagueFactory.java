package domain.model.league;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import domain.model.team.Team;
import domain.model.team.TeamException;

@Stateless
public class LeagueFactory implements LeagueFactoryLocal {

	private final static String ERROR_TEAM_NUMBER_ODD = "You must have an even number of teams to create a league";
	private static final String ERROR_TEAM_NUMBER_NULL = "You have to select at least two teams to create a league";

	@EJB
	private RepositoryLeague eLPA;

	@Override
	public League newLeague(String name, List<Team> teams) {

		// if (teams.size() % 2 == 1)
		// throw new TeamException(ERROR_TEAM_NUMBER_ODD);
		// if (teams.size() == 0)
		// throw new TeamException(ERROR_TEAM_NUMBER_NULL);

		League league = new League();
		league.setName(name);
		league.setSchedule(new Schedule());
		eLPA.save(league);

		// league.setTeams(teams);
		// league.startLeague();
		// eLPA.update(league);
		return league;
	}

	@Override
	public void updateLeague(String leagueName, Team team) {

		League league = eLPA.load(leagueName);
		league.addTeam(team);
		eLPA.update(league);
	}

	@Override
	public void StartLeague(String leagueName, List<Team> teams) {

		if (teams.size() % 2 == 1)
			throw new TeamException(ERROR_TEAM_NUMBER_ODD);
		if (teams.size() == 0)
			throw new TeamException(ERROR_TEAM_NUMBER_NULL);

		League league = eLPA.load(leagueName);
		league.startLeague();
		eLPA.update(league);
	}
}
