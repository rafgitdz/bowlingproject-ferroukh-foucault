package domain.model.league;

import static org.junit.Assert.assertEquals;

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
	
	private Challenge challenge;
	private Player p1, p2;
	

	@Before
	public void setUp() {

		Team firstTeam = buildTeam("Cottagers");
		Team secondTeam = buildTeam("Citizens");


		challenge = new Challenge(firstTeam, secondTeam);
		challenge.setDuels();
	}

	@Test
	public void testFirstDuelWinner() {

		p1 = challenge.getFirstTeam().getPlayer(0);
		p2 = challenge.getSecondTeam().getPlayer(0);

		play(3, 4, 2, 6);

		Player expected = p2;
		assertEquals(expected, challenge.getDuel(0).getWinner());
	}

	@Test
	public void testChallengeTeamWinner() {

		for (int i = 0; i < 5; i++) {

			p1 = challenge.getFirstTeam().getPlayer(i);
			p2 = challenge.getSecondTeam().getPlayer(i);

			play(4, 4, 5, 4);
		}

		Team expected = challenge.getSecondTeam();
		assertEquals(expected, challenge.getWinner());
	}

	@Test
	public void testCurrentScoreChallenge() {

		p1 = challenge.getFirstTeam().getPlayer(0);
		p2 = challenge.getSecondTeam().getPlayer(0);

		play(4, 4, 5, 4);

		p1 = challenge.getFirstTeam().getPlayer(1);
		p2 = challenge.getSecondTeam().getPlayer(1);

		play(5, 4, 5, 1);

		p1 = challenge.getFirstTeam().getPlayer(2);
		p2 = challenge.getSecondTeam().getPlayer(2);

		play(4, 4, 6, 3);

		int expected1 = 1;
		assertEquals(expected1, challenge.getScoreFirstTeam());
		int expected2 = 2;
		assertEquals(expected2, challenge.getScoreSecondTeam());

	}

	@Test(expected = ChallengeException.class)
	public void testOverChallenge() {

		for (int i = 0; i < 4; i++) {

			p1 = challenge.getFirstTeam().getPlayer(i);
			p2 = challenge.getSecondTeam().getPlayer(i);
			play(4, 4, 5, 4);
		}

		Team expected = challenge.getSecondTeam();
		assertEquals(expected, challenge.getWinner());
	}

	private Team buildTeam(String name) {

		List<Player> players = new ArrayList<Player>();
		for (int i = 0; i < 5; i++) {

			players.add(playerFactory.newPlayer(name + (i + 1)));	
		}
		return teamFactory.newTeam(name, players);
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
