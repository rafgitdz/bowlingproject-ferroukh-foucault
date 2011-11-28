package service.league;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import domain.model.league.League;
import domain.model.league.LeagueFactoryLocal;
import domain.model.league.RepositoryLeague;
import domain.model.team.RepositoryTeam;
import domain.model.team.Team;

@Stateful
public class LeagueService implements LeagueServiceRemote {

	League league;

	@EJB
	private RepositoryLeague eLPA;
	@EJB
	private RepositoryTeam eTPA;
	@EJB
	private LeagueFactoryLocal leagueFactory;

	@Override
	public String getName() {
		return league.getName();
	}

	@Override
	public League newLeague(String leagueName, List<String> namesTeam) {

		List<Team> teamList = new ArrayList<Team>();
		for (String t : namesTeam) {
			teamList.add(eTPA.load(t));
		}
		league = leagueFactory.newLeague(leagueName, teamList);
		return league;
	}

	@Override
	public League loadLeague(String name) {

		League league = eLPA.find(name);
		if (league == null)
			throw new LeagueException(League.LEAGUE_NOT_EXIST);
		return league;
	}

	@Override
	public void deleteLeague(String name) {

		if (eLPA.find(name) == null)
			throw new LeagueException(League.LEAGUE_NOT_EXIST);
		eLPA.delete(name);
	}

	@Override
	public void saveLeague(League league) {
		eLPA.save(league);
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
}
