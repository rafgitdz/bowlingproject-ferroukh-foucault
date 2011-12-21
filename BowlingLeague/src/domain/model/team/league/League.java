package domain.model.team.league;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import domain.model.exception.LeagueException;
import domain.model.team.Team;
import domain.model.team.challenge.Challenge;

@Entity
public class League implements Serializable {

	private static final long serialVersionUID = 5625666263840044089L;

	public static final String LEAGUE_NOT_EXIST = "Unknown league !";

	@Id
	private String name;

//	@OneToMany(targetEntity = Team.class, fetch = FetchType.EAGER)
//	@IndexColumn(base = 0, name = "TeamIndex")
	@Transient
	private List<Team> teams;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "schedule_Id")
	private Schedule schedule;

	private int currentRound;

	League() {
		currentRound = 1;
	}

	League(String name, List<Team> teams) {

		this.name = name;
		this.teams = teams;
		currentRound = 1;
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
		if (currentRound == teams.size() - 1)
			throw new LeagueException("The league is over");

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
		List<Challenge> challenges = getSchedule(t);
		for (Challenge c : challenges) {
			if (!c.isOver()) {
				System.out.println("challenge not over");
				break;
			}
			if (c.isOver() && c.getWinner().getName().equals(t.getName()))
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

	public void setName(String name) {
		this.name = name;
	}

	protected void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public void addTeam(Team team) {
		teams.add(team);
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
		this.schedule.build();
	}

	public void startLeague() {
		schedule.buildSchedule(teams);
		startRound(currentRound);
	}
}