package application.service.league;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import domain.model.exception.LeagueException;
import domain.model.player.Player;
import domain.model.player.RepositoryPlayer;
import domain.model.team.RepositoryTeam;
import domain.model.team.Team;
import domain.model.team.league.League;
import domain.model.team.league.LeagueFactoryLocal;
import domain.model.team.league.LeagueStatus;
import domain.model.team.league.RepositoryLeague;

@Stateless
@WebService(endpointInterface = "application.service.league.LeagueServiceRemote", serviceName = "leagueService")
public class LeagueService implements LeagueServiceRemote {

	private static final String UNKNOWN_LEAGUE = "Unknown league: ";

	@EJB
	private LeagueFactoryLocal leagueFactory;
	@EJB
	private RepositoryLeague repositoryLeague;
	@EJB
	private RepositoryTeam repositoryTeam;
	@EJB
	private RepositoryPlayer repositoryPlayer;

	@Override
	public void deleteLeague(String leagueName) {

		League league = loadLeague(leagueName);

		for (Team t : league.getTeams()) {
			t.setLeague(null);
			repositoryTeam.update(t);
		}
		repositoryLeague.delete(leagueName);
	}

	@Override
	public void startLeague(String leagueName) {

		League league = loadLeague(leagueName);
		league = leagueFactory.rebuildLeague(league);
		league.startLeague();
		repositoryLeague.update(league);

		// We update the players to persist the duels
		for (Team t : league.getTeams()) {
			for (Player p : t.getPlayers()) {
				repositoryPlayer.update(p);
			}
		}
	}

	@Override
	public Team[] getTeams(String leagueName) {

		League league = loadLeague(leagueName);
		return (Team[]) league.getTeams().toArray();
	}

	@Override
	public LeagueStatus getLeagueStatus(String leagueName) {
		League league = loadLeague(leagueName);
		return league.getStatus();
	}

	@Override
	public void goNextRound(String leagueName) {
		League league = loadLeague(leagueName);

		league = leagueFactory.rebuildLeague(league);
		league.goNextRound();

		repositoryLeague.update(league);

		for (Team team : league.getTeams())
			for (Player player : team.getPlayers())
				repositoryPlayer.update(player);
	}

	@Override
	public boolean isCurrentRoundOver(String leagueName) {
		League league = loadLeague(leagueName);
		league = leagueFactory.rebuildLeague(league);
		return league.isCurrentRoundOver();
	}

	@Override
	public Team[] getRanking(String leagueName) {
		League league = loadLeague(leagueName);
		league = leagueFactory.rebuildLeague(league);

		return (Team[]) league.getRanking().toArray();
	}

	private League loadLeague(String leagueName) {

		League league = repositoryLeague.load(leagueName);
		if (league == null)
			throw new LeagueException(UNKNOWN_LEAGUE + leagueName);

		return league;
	}
}
