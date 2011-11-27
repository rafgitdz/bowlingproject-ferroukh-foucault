package service.league;

import java.util.List;

import domain.model.league.Schedule;
import domain.model.team.Team;

public interface LeagueServiceRemote {

	public void createLeague(String name, List<Team> teams);

	public void goToNextRound(int round);

	public List<Team> getRanking();

	public int getRankingTeam(Team team);

	public Team getWinnerChallenge(int round, Team team1, Team team2);
	
	public Schedule getSchedule(Team team);
	
	public Schedule getSchedule(int round);

}
