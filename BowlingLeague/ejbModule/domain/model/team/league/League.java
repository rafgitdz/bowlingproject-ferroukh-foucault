package domain.model.team.league;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.IndexColumn;

import domain.model.exception.LeagueException;
import domain.model.exception.TeamException;
import domain.model.team.Team;
import domain.model.team.challenge.Challenge;

@Entity
public class League implements Serializable {

	private static final long serialVersionUID = 5625666263840044089L;

	private static final String ERROR_TEAM_NUMBER_ODD = "You must have an even number of teams to start a league";
	private static final String ERROR_TEAM_NUMBER_NULL = "You must have at least two teams to start a league";
	private static final String ERROR_TEAM_NOT_FULL = "The team {0} is not full, you cannot start the league";
	private static final String ERROR_LEAGUE_IN_PROGRESS = "The league is in progress, you cannot remove the team";
	private static final String ERROR_TEAM_NOT_IN_LEAGUE = "The team {0} doesn't belong to this league";
	private static final String ERROR_LEAGUE_OVER = "The league is over!";

	@Id
	private String name;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "League_Id")
	@IndexColumn(name = "TeamIndex")
	private List<Team> teams;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "schedule_Id")
	private Schedule schedule;

	private int currentRound;

	private LeagueStatus leagueStatus;

	League() {
		leagueStatus = LeagueStatus.Building;
		currentRound = 1;
	}

	League(String name, List<Team> teams) {
		leagueStatus = LeagueStatus.Building;
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
		if (getStatus() == LeagueStatus.Over)
			throw new LeagueException(ERROR_LEAGUE_OVER);
		if (currentRound == teams.size() - 1)
			this.leagueStatus = LeagueStatus.Over;
		else {
			List<Challenge> challenges = schedule
					.getRoundSchedule(getCurrentRound());
			for (Challenge c : challenges)
				if (c.isOver())
					c.setWinner();
				else
					throw new LeagueException(
							"Cannot go to next round, current round's challenges are not over");

			startRound(++currentRound);
		}
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
		if (teams.size() < 2)
			throw new LeagueException(ERROR_TEAM_NUMBER_NULL);
		if (teams.size() % 2 == 1)
			throw new LeagueException(ERROR_TEAM_NUMBER_ODD);
		for (Team t : teams) {
			if (t.getPlayers().size() < Team.TEAM_SIZE)
				throw new LeagueException(String.format(ERROR_TEAM_NOT_FULL,
						t.getName()));
		}

		schedule.buildSchedule(teams);
		startRound(currentRound);
	}

	public void removeTeam(String teamName) {
		if (leagueStatus == LeagueStatus.InProgress)
			throw new TeamException(ERROR_LEAGUE_IN_PROGRESS);
		int teamIndex = -1;
		for (int i = 0; i < teams.size(); i++)
			if (teams.get(i).getName().equals(teamName))
				teamIndex = i;
		if (teamIndex == -1)
			throw new LeagueException(String.format(ERROR_TEAM_NOT_IN_LEAGUE,
					teamName));

		teams.remove(teamIndex);
	}

	public LeagueStatus getStatus() {
		return this.leagueStatus;
	}
}