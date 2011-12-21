package service.league;

import java.util.ArrayList;
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
	public void saveLeague(League league) {
		repositoryLeague.save(league);
	}

	@Override
	public League loadLeague(String nameLeague) {

		return leagueFactory.buildLeague(nameLeague);
	}

	@Override
	public void deleteLeague(String name) {

		if (repositoryLeague.find(name) == null)
			throw new LeagueException(League.LEAGUE_NOT_EXIST);
		repositoryLeague.delete(name);
	}

	@Override
	public void startLeague(String name, List<String> namesTeam) {

		List<Team> teamList = new ArrayList<Team>();
		for (String t : namesTeam) {
			teamList.add(repositoryTeam.load(t));
		}
		//TODO add the teams to the league here
		leagueFactory.startLeague(name);
	}

	@Override
	public List<Team> getTeams(List<String> namesTeam) {

		List<Team> teamList = new ArrayList<Team>();
		for (String t : namesTeam) {
			teamList.add(repositoryTeam.load(t));
		}
		return teamList;
	}
}
