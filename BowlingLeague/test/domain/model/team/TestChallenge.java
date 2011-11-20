package domain.model.team;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import domain.model.duel.Player;

public class TestChallenge {

	private Challenge challenge;
	private Team firstTeam;
	private Team secondTeam;
	private Player p1, p2;
	
	@Before
	public void setUp() {

		
		firstTeam = new Team("Cotagers");
		secondTeam = new Team("Citizens");
		
		buildTeam(firstTeam);
		buildTeam(secondTeam);
		challenge = new Challenge(firstTeam, secondTeam);
		challenge.setDuels();
	}

	@Test
	public void testFirstDuelWinner() {

		p1 = challenge.getFirstTeamPlayer(0);
		p2 = challenge.getSecondTeamPlayer(0);

		for (int j = 0; j < 10; j++) {

			p1.roll(3);p1.roll(4);
			p2.roll(2);p2.roll(5);
		}

		Player expected = p2;
		assertEquals(expected, challenge.getDuel(0).getWinner());
	}

	@Test
	public void testChallengeTeamWinner() {

		for (int i = 0; i < 5; i++) {

			p1 = challenge.getFirstTeamPlayer(i);
			p2 = challenge.getSecondTeamPlayer(i);

			for (int j = 0; j < 10; j++) {

				p1.roll(4);
				p1.roll(4);
				p2.roll(5);
				p2.roll(4);
			}
			
			
		}
		
		Team expected = secondTeam;
		assertEquals(expected, challenge.getWinner());
	}
	
	@Test (expected = ChallengeException.class)
	public void testOverChallenge() {
		
		for (int i = 0; i < 4; i++) {

			p1 = challenge.getFirstTeamPlayer(i);
			p2 = challenge.getSecondTeamPlayer(i);

			for (int j = 0; j < 10; j++) {

				p1.roll(4);
				p1.roll(4);
				p2.roll(5);
				p2.roll(4);
			}
			
		}
		
		Team expected = secondTeam;
		assertEquals(expected, challenge.getWinner());
	}
	
	@Test
	public void testCurrentScoreChallenge() {
		
		p1 = challenge.getFirstTeamPlayer(0);
		p2 = challenge.getSecondTeamPlayer(0);

		for (int j = 0; j < 10; j++) {

			p1.roll(4);
			p1.roll(4);
			p2.roll(5);
			p2.roll(4);
		}
		

		
		p1 = challenge.getFirstTeamPlayer(1);
		p2 = challenge.getSecondTeamPlayer(1);

		for (int j = 0; j < 10; j++) {

			p1.roll(5);
			p1.roll(4);
			p2.roll(5);
			p2.roll(1);
		}
		

		
		p1 = challenge.getFirstTeamPlayer(2);
		p2 = challenge.getSecondTeamPlayer(2);

		for (int j = 0; j < 10; j++) {

			p1.roll(4);
			p1.roll(4);
			p2.roll(6);
			p2.roll(3);
		}

		
		int expected1 = 1;
		assertEquals(expected1, challenge.getScoreFirstTeam());
		int expected2 = 2;
		assertEquals(expected2, challenge.getScoreSecondTeam());
		
	}

	private void buildTeam(Team team) {

		for (int i = 0; i < 5; i++) {

			Player p = new Player(team.getName() + (i+1));
			team.addPlayer(p);
		}
	}
}