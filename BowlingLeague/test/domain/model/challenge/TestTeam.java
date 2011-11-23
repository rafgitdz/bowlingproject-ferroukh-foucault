package domain.model.challenge;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


import domain.model.player.Player;
import domain.model.team.Team;
import domain.model.team.TeamException;

public class TestTeam {

	private Team t;
	private String teamName = "The A Team";
	private Player bob;

	@Before
	public void setUp() {

		t = new Team(teamName);
		bob = new Player("Bob");
	}

	@Test
	public void testCreateTeam() {

		assertEquals(teamName, t.getName());
	}

	@Test
	public void testAddPlayers() {

		t.addPlayer(bob);
		Player alice = new Player("Alice");
		t.addPlayer(alice);
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("Bob");
		expected.add("Alice");
		assertEquals(expected, t.getPlayersNames());
	}

	@Test(expected = TeamException.class)
	public void testUniquePlayersInTeam() {

		t.addPlayer(bob);
		t.addPlayer(bob);
	}

	@Test(expected = TeamException.class)
	public void testMaxPlayersInTeam() {

		for (int i = 0; i < Team.MAX_TEAM_SIZE + 1; i++) {

			Player p = new Player("p" + i);
			t.addPlayer(p);
		}
	}
}
