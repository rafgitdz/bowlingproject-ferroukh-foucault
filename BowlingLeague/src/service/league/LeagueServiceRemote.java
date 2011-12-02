package service.league;

import java.util.List;

import javax.ejb.Remote;

import domain.model.league.League;
import domain.model.team.Team;

@Remote
public interface LeagueServiceRemote {

	public League newLeague(String leagueName, List<String> namesTeam);

	public League loadLeague(String name);

	public void deleteLeague(String name);

	public void saveLeague(League league);
	
	public void addTeam(String name, String nameTeam);
	
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
