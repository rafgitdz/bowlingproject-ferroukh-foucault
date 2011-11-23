package domain.model.league;

import java.util.ArrayList;
import java.util.List;

import domain.model.team.Team;



public class Schedule {

	private int rounds;
	private int challengesPerRound;
	private Challenge[][] schedule;

	public Schedule(List<Team> teams) {

		rounds = League.LEAGUE_SIZE - 1;
		challengesPerRound = League.LEAGUE_SIZE / 2;
		schedule = new Challenge[rounds][challengesPerRound];
		buildSchedule(teams);

	}

	private void buildSchedule(List<Team> teams) {
		for (int i = 0; i < rounds; i++) {
			for (int j = 0; j < challengesPerRound; j++) {
				schedule[i][j] = new Challenge(teams.get(j), teams.get(j
						+ challengesPerRound));
			}
			teams.add(teams.get(1));
			teams.remove(1);
		}
	}
	
	public List<Challenge> getTeamSchedule(String teamName) {
		List<Challenge> challenges = new ArrayList<Challenge>(rounds);
		for (int i = 0; i < rounds; ++i)
			for (int j = 0; j < challengesPerRound; ++j) {
				if (schedule[i][j].getFirstTeam().getName() == teamName
						|| schedule[i][j].getSecondTeam().getName() == teamName) {
					challenges.add(schedule[i][j]);
					break;
				}
			}
		return challenges;
	}

	public List<Challenge> getRoundSchedule(int roundNumber) {

		List<Challenge> challenges = new ArrayList<Challenge>(
				challengesPerRound);
		for (int i = 0; i < challengesPerRound; ++i)
			challenges.add(schedule[roundNumber - 1][i]);
		return challenges;
	}
}