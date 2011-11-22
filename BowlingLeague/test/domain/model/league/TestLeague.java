package domain.model.league;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import domain.model.challenge.Challenge;
import domain.model.challenge.Team;
import domain.model.duel.Player;

public class TestLeague {

	List<Team> teams;
	private League league;
	String name = "Barclay's";

	@Before
	public void setUp() {
		
		teams = new ArrayList<Team>();
		for (int i = 0; i < 20; i++) {
			Team t = new Team("Team" + i);
			for (int j = 0; j < 5; j++)
				t.addPlayer(new Player("Player" + i + j));
			teams.add(t);
		}
		league = new League(name, teams);
	}

	@Test
	public void testCreateLeague() {

		assertEquals(name, league.getName());
		for (int i = 0; i < teams.size(); i++) {
			assertEquals(league.getTeams().get(i).getName(), teams.get(i)
					.getName());
		}
	}

	@Test(expected = LeagueException.class)
	public void testLeague21Teams() {
		teams.add(new Team("Team21"));
		league = new League(name, teams);

	}

	@Test(expected = LeagueException.class)
	public void testLeague19Teams() {
		
		teams.remove(0);
		league = new League(name, teams);
	}

	@Test
	public void testGetSchedule() {
		
		Schedule schedule = league.getSchedule();
		for (Team t : teams) {
			assertEquals(19, schedule.getTeamSchedule(t.getName()).size());
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
	public void testPlayRound() {

		playRound();
		league.nextRound();
	}

	@Test(expected = LeagueException.class)
	public void nextRound_RoundNotOver() {
		league.nextRound();
	}

	@Test
	public void testGetTeamScore() {
		for (Team t : teams)
			assertEquals(0, league.getScore(t));
		playRound();
		for (Team t : teams) {
			if (teams.indexOf(t) < 10) // dans playRound ce sont les equipes à
										// domicile qui gagnent, donc au 1er
										// tour, les 10 premieres equipes
				assertEquals(1, league.getScore(t));
			else
				assertEquals(0, league.getScore(t));
		}
	}

	/*
	 * @Test(expected=LeagueException.class) public void testPlay20Rounds() {
	 * for (int i= 0; i < 20 ; ++i) { playRound(); league.nextRound(); } }
	 */

	private void playRound() {
		for (Challenge c : league.getCurrentRoundChallenges()) {
			for (int i = 0; i < 5; ++i) {
				Player p1 = c.getFirstTeamPlayer(i);
				Player p2 = c.getSecondTeamPlayer(i);
				for (int j = 0; j < 10; ++j) {
					p1.roll(4);
					p1.roll(4);
					p2.roll(3);
					p2.roll(4);
				}
			}
		}
	}

}
