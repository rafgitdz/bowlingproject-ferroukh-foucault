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
import domain.model.team.challenge.Challenge;

public class TestLeague {

	TeamFactoryForTest teamFactory = new TeamFactoryForTest();
	PlayerFactoryForTest playerFactory = new PlayerFactoryForTest();
	RepositoryLeagueForTest repositoryLeague = new RepositoryLeagueForTest();
	LeagueFactoryForTest leagueFactory = new LeagueFactoryForTest(repositoryLeague);
	
	
	List<Team> teams;
	League league;
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
		league = leagueFactory.newLeague(name);
		for (Team t : teams)
			league.addTeam(t);
		repositoryLeague.save(league);
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
	public void testPlay3Rounds() {

		playRound();
		league.nextRound();
		playRound();
		league.nextRound();
		playRound();
	}

	@Test(expected = LeagueException.class)
	public void testLeagueOver() {

		playRound();
		league.nextRound();
		playRound();
		league.nextRound();
		playRound();
		league.nextRound();
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

		for (Challenge c : league.getCurrentRoundChallenges()) {
			for (int i = 0; i < 10; ++i) {
				for (Player p : c.getFirstTeam().getPlayers()) {
					p.roll(4);
					p.roll(2);
				}
				for (Player p : c.getSecondTeam().getPlayers()) {
					p.roll(4);
					p.roll(4);
				}
			}
		}
	}

}
