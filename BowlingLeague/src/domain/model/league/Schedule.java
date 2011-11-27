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
	private List<LeagueRound> schedule;
	private int rounds;
	private int challengesPerRound;

	protected Schedule(List<Team> teams) {

		rounds = teams.size() - 1;
		challengesPerRound = teams.size() / 2;
		schedule = new ArrayList<LeagueRound>(rounds);
		buildSchedule(teams);
	}

	private void buildSchedule(List<Team> teams) {

		for (int i = 0; i < rounds; i++) {
			List<Challenge> roundChallenges = new ArrayList<Challenge>(
					challengesPerRound);
			for (int j = 0; j < challengesPerRound; j++) {
				roundChallenges.add(new Challenge(teams.get(j), teams.get(j
						+ challengesPerRound)));
			}
			schedule.add(new LeagueRound(roundChallenges));
			teams.add(teams.get(1));
			teams.remove(1);
		}
	}

	public List<Challenge> getTeamSchedule(String teamName) {

		List<Challenge> challenges = new ArrayList<Challenge>(rounds);
		for (LeagueRound round : schedule)
			for (Challenge c : round.getChallenges()) {

				if (c.getFirstTeam().getName().equals(teamName)
						|| c.getSecondTeam().getName().equals(teamName)) {
					challenges.add(c);
					break;
				}
			}
		return challenges;
	}

	public List<Challenge> getRoundSchedule(int roundNumber) {

		return schedule.get(roundNumber - 1).getChallenges();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}