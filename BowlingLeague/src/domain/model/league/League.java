package domain.model.league;

import java.util.List;

import domain.model.team.Challenge;
import domain.model.team.Team;

public class League {

	public static final int LEAGUE_SIZE = 20;
	private final static String ERROR_TEAM_NUMBER = "The league has to be composed of "
			+ LEAGUE_SIZE + " Teams.";
	private String name;
	private List<Team> teams;
	private Schedule schedule;
	private int currentRound;

	public League(String name, List<Team> teams) {
		
		this.name = name;
		if (teams.size() != LEAGUE_SIZE)
			throw new LeagueException(ERROR_TEAM_NUMBER);
		this.teams = teams;
		schedule = new Schedule(teams);
		currentRound = 1;
		startRound(currentRound);
	}

	public String getName() {
		
		return this.name;
	}

	public List<Team> getTeams() {
		
		return teams;
	}

	public Schedule getSchedule() {
		
		return schedule;
	}

	public int getCurrentRound() {
		
		return currentRound;
	}

	public void nextRound() {

		List<Challenge> challenges = schedule
				.getRoundSchedule(getCurrentRound());
		for (Challenge c : challenges)
			if (!c.isOver())
				throw new LeagueException(
						"Cannot go to next round, current round's challenge are not over");

		startRound(++currentRound);

	}

	private void startRound(int round) {
		
		for (Challenge c : getCurrentRoundChallenges()) {
			c.setDuels();
		}
	}

	public Challenge getCurrentChallenge(Team team) {
		
		for (Challenge c : getCurrentRoundChallenges())
			if (c.getFirstTeam().getName().equals(team.getName())
					|| c.getSecondTeam().getName().equals(team.getName()))
				return c;

		return null;
	}
	
	public List<Challenge> getCurrentRoundChallenges() {
		
		return schedule.getRoundSchedule(getCurrentRound());
	}

	public int getScore(Team t) {
		
		int score = 0;
		for (Challenge c : getSchedule(t)) {
			if (!c.isOver())
				break;
			if (c.getWinner().getName().equals(t.getName()))
				score++;
		}
		return score;
	}

	public List<Challenge> getSchedule(Team t) {
		
		return getSchedule().getTeamSchedule(t.getName());
	}

}