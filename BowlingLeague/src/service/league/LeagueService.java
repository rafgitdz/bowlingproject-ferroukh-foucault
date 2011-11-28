package service.league;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import domain.model.league.League;
import domain.model.league.LeagueFactoryLocal;
import domain.model.league.RepositoryLeague;
import domain.model.team.Team;

@Stateful
public class LeagueService implements LeagueServiceRemote {

	League league;

	@EJB
	private RepositoryLeague eLPA;
	@EJB
	private LeagueFactoryLocal leagueFactory;

	@Override
	public void createLeague(String name, List<Team> teams) {
		league = leagueFactory.newLeague(name, teams);
	}

	@Override
	public String getName() {
		return league.getName();
	}

	@Override
	public League newLeague(String leagueName, List<Team> teams) {
		league = eLPA.save(leagueFactory.newLeague(leagueName, teams), league.getName());
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
}
