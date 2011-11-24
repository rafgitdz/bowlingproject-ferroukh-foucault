package domain.model.league;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.IndexColumn;

import domain.model.team.Team;

@Entity
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = Challenge.class)
	@IndexColumn(base = 0, name = "ChallengeIndex")
	private Challenge[][] schedule;
	private int rounds;
	private int challengesPerRound;

	protected Schedule(List<Team> teams) {

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}