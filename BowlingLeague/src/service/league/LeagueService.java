package service.league;

import java.util.List;

import domain.model.league.Schedule;
import domain.model.team.Team;

public class LeagueService implements LeagueServiceRemote {

	@Override
	public void createLeague(String name, List<Team> teams) {
	}

	@Override
	public void goToNextRound(int round) {
	}

	@Override
	public List<Team> getRanking() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRankingTeam(Team team) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Team getWinnerChallenge(int round, Team team1, Team team2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Schedule getSchedule(Team team) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Schedule getSchedule(int round) {
		// TODO Auto-generated method stub
		return null;
	}
}
