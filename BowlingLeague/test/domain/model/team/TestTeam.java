package domain.model.team;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import domain.model.duel.Player;

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
		String expected = "Bob, Alice";
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
