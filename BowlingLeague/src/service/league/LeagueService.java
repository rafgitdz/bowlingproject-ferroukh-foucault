package service.league;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import domain.model.league.League;
import domain.model.league.LeagueFactoryLocal;
import domain.model.league.Schedule;
import domain.model.team.Team;

@Stateful
public class LeagueService implements LeagueServiceRemote {

	League league;
	
	@EJB
	private LeagueFactoryLocal leagueFactory;
	
	@Override
	public void createLeague(String name, List<Team> teams) {
		league = leagueFactory.newLeague(name, teams);
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

	@Override
	public Map<Integer, Integer> getScoreChallenge(Team team1, Team team2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getScore(Team team) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		return league.getName();
	}
}
