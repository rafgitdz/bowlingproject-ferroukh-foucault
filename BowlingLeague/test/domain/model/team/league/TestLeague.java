package domain.model.team.league;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import domain.model.exception.LeagueException;
import domain.model.player.Player;
import domain.model.player.PlayerFactoryForTest;
import domain.model.team.Team;
import domain.model.team.TeamFactoryForTest;
import domain.model.team.league.Challenge;
import domain.model.team.league.League;
import domain.model.team.league.Schedule;

public class TestLeague {

	TeamFactoryForTest teamFactory = new TeamFactoryForTest();
	PlayerFactoryForTest playerFactory = new PlayerFactoryForTest();

	List<Team> teams;
	private League league;
	String name = "Barclay's";

	@Before
	public void setUp() {

		teams = new ArrayList<Team>();

		for (int i = 0; i < 4; i++) {

			List<Player> players = new ArrayList<Player>();
			for (int j = 0; j < 5; j++)
				players.add(playerFactory.newPlayer("Player" + i + j));
			Team t = teamFactory.newTeam("Team" + i);

			for (Player p : players)
				t.addPlayer(p);

			teams.add(t);
		}
		league = new League(name, teams);
		league.setSchedule(new Schedule());
		league.startLeague();
	}

	@Test
	public void testCreateLeague() {

		assertEquals(name, league.getName());
		for (int i = 0; i < teams.size(); i++) {
			assertEquals(league.getTeams().get(i).getName(), teams.get(i)
					.getName());
		}
	}

	@Test
	public void testGetSchedule() {

		Schedule schedule = league.getSchedule();
		for (Team t : teams) {
			assertEquals(teams.size() - 1, schedule
					.getTeamSchedule(t.getName()).size());
		}
	}

	@Test
	public void testGetCurrentRound() {

		int round = league.getCurrentRound();
		assertEquals(1, round);
	}

	@Test
	public void testGetTeamCurrentChallenge() {

		Challenge c1 = league.getCurrentChallenge(teams.get(0));

		Team opponent;
		if (c1.getFirstTeam().getName().equals(teams.get(0).getName()))
			opponent = c1.getSecondTeam();
		else
			opponent = c1.getFirstTeam();
		Challenge c2 = league.getCurrentChallenge(opponent);
		assertEquals(c1, c2);
	}

	@Test
	public void testPlay2Rounds() {

		playRound();
		league.nextRound();
		playRound();
		league.nextRound();
		playRound();
	}

	@Test(expected = LeagueException.class)
	public void nextRound_RoundNotOver() {
		league.nextRound();
	}

	@Test
	public void testGetTeamScore() {

		int totalScores = 0;
		int expectedTotal = teams.size() / 2;
		for (Team t : teams)
			assertEquals(0, league.getScore(t));
		playRound();
		league.nextRound();
		for (Team t : teams) {
			totalScores += league.getScore(t);
		}
		assertEquals(expectedTotal, totalScores);
	}

	private void playRound() {

		for (int i = 0; i < 10; ++i) {
			for (int teamIdx = 0; teamIdx < teams.size(); ++teamIdx) {
				Team t = teams.get(teamIdx);
				for (Player p : t.getPlayers()) {
					p.roll(teamIdx);
					p.roll(teamIdx);
				}
			}
		}
	}

}
