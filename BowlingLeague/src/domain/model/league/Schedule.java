package domain.model.league;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.IndexColumn;

import domain.model.team.Team;

@Entity
public class Schedule implements Serializable {

	private static final long serialVersionUID = -5883474850303639189L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = LeagueRound.class, fetch = FetchType.EAGER)
	@IndexColumn(base = 0, name = "leagueRoundIndex")
	private List<LeagueRound> scheduleRounds;

	private int rounds;
	private int challengesPerRound;

	protected Schedule() {
	}

	protected void buildSchedule(List<Team> teams) {

		rounds = teams.size() - 1;
		challengesPerRound = teams.size() / 2;
		scheduleRounds = new ArrayList<LeagueRound>(rounds);

		for (int i = 0; i < rounds; i++) {
			List<Challenge> roundChallenges = new ArrayList<Challenge>(
					challengesPerRound);
			for (int j = 0; j < challengesPerRound; j++) {
				roundChallenges.add(new Challenge(teams.get(j), teams.get(j
						+ challengesPerRound)));
			}
			scheduleRounds.add(new LeagueRound(roundChallenges));
			teams.add(teams.get(1));
			teams.remove(1);
		}
	}

	public List<Challenge> getTeamSchedule(String teamName) {

		List<Challenge> challenges = new ArrayList<Challenge>(rounds);
		for (LeagueRound round : scheduleRounds)
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
		return scheduleRounds.get(roundNumber - 1).getChallenges();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<LeagueRound> getSchedule() {
		return scheduleRounds;
	}

	public void setSchedule(List<LeagueRound> schedule) {
		this.scheduleRounds = schedule;
	}

	public void build() {
		rounds = 0;
		challengesPerRound = 0;
		scheduleRounds = new ArrayList<LeagueRound>();
	}
}