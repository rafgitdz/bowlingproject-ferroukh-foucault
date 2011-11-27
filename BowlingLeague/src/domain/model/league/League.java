package domain.model.league;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.IndexColumn;

import domain.model.team.Team;

@Entity
public class League {

	@Id
	private String name;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = Team.class)
	@IndexColumn(base = 0, name = "TeamIndex")
	private List<Team> teams;
	@OneToOne
	@JoinColumn(name = "Schedule_ID")
	private Schedule schedule;
	private int currentRound;

	protected League() {
	}

	protected League(String name, List<Team> teams) {

		this.name = name;
		this.teams = teams;
		schedule = new Schedule(teams);
		currentRound = 1;
		startRound(currentRound);
	}

	public int getSize() {
		return teams.size();
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
		for (Challenge c : getCurrentRoundChallenges())
			c.setDuels();
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

	public List<Team> getRanking() {

		Map<Team, Integer> scores = new HashMap<Team, Integer>();
		List<Team> ranking = new ArrayList<Team>();

		for (Team t : teams) {
			int score = getScore(t);
			int rank = 0;
			scores.put(t, score);
			for (int i = 0; i < ranking.size(); ++i) {
				if (scores.get(ranking.get(i)) <= score) {
					rank = i;
					break;
				}
			}
			ranking.add(rank, t);
		}

		return ranking;
	}

	public List<Challenge> getSchedule(Team t) {

		return getSchedule().getTeamSchedule(t.getName());
	}
}