package service.league;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import domain.model.exception.LeagueException;
import domain.model.league.League;
import domain.model.league.LeagueFactoryLocal;
import domain.model.league.RepositoryLeague;
import domain.model.team.RepositoryTeam;
import domain.model.team.Team;

@Stateless
public class LeagueService implements LeagueServiceRemote {
	
	@EJB
	private RepositoryLeague repositoryLeague;
	@EJB
	private RepositoryTeam eTPA;
	@EJB
	private LeagueFactoryLocal leagueFactory;

	@Override
	public League newLeague(String leagueName, List<String> namesTeam) {

		List<Team> teamList = new ArrayList<Team>();
		for (String t : namesTeam) {
			teamList.add(eTPA.load(t));
		}
		return leagueFactory.newLeague(leagueName, teamList);
	}

	@Override
	public League loadLeague(String name) {

		League league = repositoryLeague.find(name);
		if (league == null)
			throw new LeagueException(League.LEAGUE_NOT_EXIST);
		return league;
	}

	@Override
	public void deleteLeague(String name) {

		if (repositoryLeague.find(name) == null)
			throw new LeagueException(League.LEAGUE_NOT_EXIST);
		repositoryLeague.delete(name);
	}

	@Override
	public void saveLeague(League league) {
		repositoryLeague.save(league);
	}

	@Override
	public void addTeam(String name, String nameTeam) {
		leagueFactory.updateLeague(name, eTPA.load(nameTeam));
	}

	@Override
	public void startLeague(String name, List<String> namesTeam) {

		List<Team> teamList = new ArrayList<Team>();
		for (String t : namesTeam) {
			teamList.add(eTPA.load(t));
		}
		leagueFactory.StartLeague(name, teamList);
	}

	@Override
	public List<Team> getTeams(List<String> namesTeam) {

		List<Team> teamList = new ArrayList<Team>();
		for (String t : namesTeam) {
			teamList.add(eTPA.load(t));
		}
		return teamList;
	}
}
