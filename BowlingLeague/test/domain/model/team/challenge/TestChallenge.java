package domain.model.team.challenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import domain.model.exception.ChallengeException;
import domain.model.player.Player;
import domain.model.player.PlayerFactoryForTest;
import domain.model.team.Team;
import domain.model.team.TeamFactoryForTest;

public class TestChallenge {

	private TeamFactoryForTest teamFactory = new TeamFactoryForTest();
	private PlayerFactoryForTest playerFactory = new PlayerFactoryForTest();
	private ChallengeFactoryForTest challengeFactory = new ChallengeFactoryForTest();
	private Challenge challenge;
	private Team firstTeam;
	private Team secondTeam;

	@Before
	public void setUp() {

		firstTeam = buildTeam("Cottagers");
		secondTeam = buildTeam("Citizens");

		challenge = challengeFactory.newChallenge(firstTeam, secondTeam);
		challenge.setDuels();
	}

	@Test
	public void testChallengeTeamWinner() {

		for (int i = 0; i < 10; i++)
			playRoll(4, 4, 5, 4);

		Team expected = secondTeam;
		assertEquals(expected, challenge.getWinner());
	}
	
	@Test
	public void testChallengeNotOver() {
		assertFalse(challenge.isOver());
	}

	@Test
	public void testCurrentScoreChallenge() {

		for (int i = 0; i < 10 ; ++i)
			playRoll(4, 5, 3, 3);

		int expected1 = 5;
		assertEquals(expected1, challenge.getScoreFirstTeam());
		int expected2 = 0;
		assertEquals(expected2, challenge.getScoreSecondTeam());

	}

	@Test(expected = ChallengeException.class)
	public void testOverChallenge() {

		for (int i = 0; i < 4; i++) {

			playRoll(4, 4, 5, 4);
		}

		Team expected = challenge.getSecondTeam();
		assertEquals(expected, challenge.getWinner());
	}

	private Team buildTeam(String name) {

		List<Player> players = new ArrayList<Player>();
		for (int i = 0; i < 5; i++) {

			players.add(playerFactory.newPlayer(name + (i + 1)));
		}
		Team t = teamFactory.newTeam(name);
		for (Player p : players)
			t.addPlayer(p);

		return t;
	}

	private void playRoll(int roll1, int roll2, int roll3, int roll4) {

		for (Player p : firstTeam.getPlayers()) {
			p.roll(roll1);
			p.roll(roll2);
		}
		for (Player p : secondTeam.getPlayers()) {
			p.roll(roll3);
			p.roll(roll4);
		}
	}
}
