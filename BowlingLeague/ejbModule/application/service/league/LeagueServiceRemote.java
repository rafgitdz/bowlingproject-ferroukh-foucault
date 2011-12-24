package application.service.league;

import java.util.List;

import javax.ejb.Remote;

import domain.model.team.Team;
import domain.model.team.league.League;

@Remote
public interface LeagueServiceRemote {
	
	public League loadLeague(String name);

	public void deleteLeague(String name);

	public void saveLeague(League league);
	
	public void startLeague(String name, List<String> namesTeam);
	
	public List<Team> getTeams(List<String> namesTeam);

	// public void goToNextRound(int round);
	//
	// public Map<Integer, Integer> getScoreChallenge(Team team1, Team team2);
	//
	// public int getScore(Team team);
	//
	// public List<Team> getRanking();
	//
	// public int getRankingTeam(Team team);
	//
	// public Team getWinnerChallenge(int round, Team team1, Team team2);
	//
	// public Schedule getSchedule(Team team);
	//
	// public Schedule getSchedule(int round);
}
