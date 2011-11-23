package domain.model.challenge;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


import domain.model.league.Challenge;
import domain.model.league.ChallengeException;
import domain.model.player.Player;
import domain.model.team.Team;

public class TestChallenge {

	private Challenge challenge;
	private Player p1, p2;

	@Before
	public void setUp() {

		Team firstTeam = new Team("Cotagers");
		Team secondTeam = new Team("Citizens");

		buildTeam(firstTeam, firstTeam.getName());
		buildTeam(secondTeam, secondTeam.getName());

		challenge = new Challenge(firstTeam, secondTeam);
		challenge.setDuels();
	}

	@Test
	public void testFirstDuelWinner() {

		p1 = challenge.getFirstTeamPlayer(0);
		p2 = challenge.getSecondTeamPlayer(0);

		play(3, 4, 2, 6);

		Player expected = p2;
		assertEquals(expected, challenge.getDuel(0).getWinner());
	}

	@Test
	public void testChallengeTeamWinner() {

		for (int i = 0; i < 5; i++) {

			p1 = challenge.getFirstTeamPlayer(i);
			p2 = challenge.getSecondTeamPlayer(i);

			play(4, 4, 5, 4);
		}

		Team expected = challenge.getSecondTeam();
		assertEquals(expected, challenge.getWinner());
	}

	@Test
	public void testCurrentScoreChallenge() {

		p1 = challenge.getFirstTeamPlayer(0);
		p2 = challenge.getSecondTeamPlayer(0);

		play(4, 4, 5, 4);

		p1 = challenge.getFirstTeamPlayer(1);
		p2 = challenge.getSecondTeamPlayer(1);

		play(5, 4, 5, 1);

		p1 = challenge.getFirstTeamPlayer(2);
		p2 = challenge.getSecondTeamPlayer(2);

		play(4, 4, 6, 3);

		int expected1 = 1;
		assertEquals(expected1, challenge.getScoreFirstTeam());
		int expected2 = 2;
		assertEquals(expected2, challenge.getScoreSecondTeam());

	}

	@Test(expected = ChallengeException.class)
	public void testOverChallenge() {

		for (int i = 0; i < 4; i++) {

			p1 = challenge.getFirstTeamPlayer(i);
			p2 = challenge.getSecondTeamPlayer(i);
			play(4, 4, 5, 4);
		}

		Team expected = challenge.getSecondTeam();
		assertEquals(expected, challenge.getWinner());
	}

	private void buildTeam(Team team, String name) {

		for (int i = 0; i < 5; i++) {

			Player p = new Player(name + (i + 1));
			team.addPlayer(p);
		}
	}

	private void play(int roll1, int roll2, int roll3, int roll4) {

		for (int i = 0; i < 10; i++) {
			p1.roll(roll1);
			p1.roll(roll2);
			p2.roll(roll3);
			p2.roll(roll4);
		}
	}
}
