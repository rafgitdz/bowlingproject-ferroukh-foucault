package application.service.league;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import domain.model.exception.LeagueException;
import domain.model.exception.TeamException;
import domain.model.player.Player;
import domain.model.player.RepositoryPlayer;
import domain.model.team.RepositoryTeam;
import domain.model.team.Team;
import domain.model.team.challenge.Challenge;
import domain.model.team.league.League;
import domain.model.team.league.LeagueFactoryLocal;
import domain.model.team.league.LeagueStatus;
import domain.model.team.league.RepositoryLeague;

@Stateless
@WebService(endpointInterface = "application.service.league.LeagueServiceRemote", serviceName = "leagueService")
public class LeagueService implements LeagueServiceRemote {

	private static final String UNKNOWN_LEAGUE = "Unknown league: ";

	private static final String ROUND_DONT_EXIST = "The round don't exists between ";

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
		return (Team[]) league.getTeams().toArray(
				new Team[league.getTeams().size()]);
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
	public String[] getRanking(String leagueName) {

		League league = loadLeague(leagueName);
		league = leagueFactory.rebuildLeague(league);
		List<Team> teamsRank = league.getRanking();

		String[] teams = new String[teamsRank.size()];
		for (int i = 0; i < teams.length; i++) {
			teams[i] = teamsRank.get(i).getName();
		}
		return teams;
	}

	@Override
	public int getScore(String teamName) {

		Team team = repositoryTeam.load(teamName);
		if (team == null)
			throw new TeamException("Unknown Team : " + teamName);

		League league = team.getLeague();
		leagueFactory.rebuildLeague(league);
		return league.getScore(team);
	}

	private League loadLeague(String leagueName) {

		League league = repositoryLeague.load(leagueName);
		if (league == null)
			throw new LeagueException(UNKNOWN_LEAGUE + leagueName);

		return league;
	}

	@Override
	public String[] getLeagues() {

		List<League> leaguesList = repositoryLeague.loadAll();
		String[] leagues = new String[leaguesList.size()];
		for (int i = 0; i < leagues.length; i++) {
			leagues[i] = leaguesList.get(i).getName();
		}
		return leagues;
	}

	@Override
	public String[] getTeamsLeftSideSchedule(String leagueName, int round) {

		League league = loadAndControlLeague(leagueName);
		List<Challenge> challenges = league.getSchedule().getRoundSchedule(
				round);
		String[] teamsLeftSide = new String[challenges.size()];

		for (int i = 0; i < challenges.size(); ++i)
			teamsLeftSide[i] = challenges.get(i).getFirstTeam().getName();

		return teamsLeftSide;
	}

	@Override
	public String[] getTeamsRightSideSchedule(String leagueName, int round) {

		League league = loadAndControlLeague(leagueName);
		List<Challenge> challenges = league.getSchedule().getRoundSchedule(
				round);
		String[] teamsRightSide = new String[challenges.size()];

		for (int i = 0; i < challenges.size(); ++i)
			teamsRightSide[i] = challenges.get(i).getSecondTeam().getName();

		return teamsRightSide;
	}

	@Override
	public String getScoreChallenge(String leagueName, int round, String team1,
			String team2) {

		League league = loadAndControlLeague(leagueName);
		List<Challenge> challenges = league.getSchedule().getRoundSchedule(
				round);
		
		for (Challenge c : challenges) {

			if (c.getFirstTeam().equals(team1)
					&& c.getSecondTeam().equals(team2))

				return c.getScoreTeam1() + " - " + c.getScoreTeam2();
		}
		throw new LeagueException(ROUND_DONT_EXIST + team1 + "AND" + team2);
	}

	@Override
	public int getNumberRounds(String leagueName) {
		
		League league = loadAndControlLeague(leagueName);
		return league.getSchedule().getNumberRounds();
	}
	
	private League loadAndControlLeague(String leagueName) {
		
		League league = loadLeague(leagueName);
		leagueFactory.rebuildLeague(league);
		if (league == null)
			throw new LeagueException(UNKNOWN_LEAGUE + leagueName);
		return league;
	}
}
