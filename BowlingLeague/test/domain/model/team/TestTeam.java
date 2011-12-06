package domain.model.team;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import domain.model.player.Player;
import domain.model.player.PlayerFactoryForTest;
import domain.model.team.Team;

public class TestTeam {

	
	private PlayerFactoryForTest playerFactory = new PlayerFactoryForTest();
	private TeamFactoryForTest teamFactory = new TeamFactoryForTest();
	private Team t;
	private String teamName = "The A Team";

	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testCreateTeam() {
		
		List<Player> players = new ArrayList<Player>();
		
		for (int i = 0; i < Team.TEAM_SIZE; ++i)
			players.add(playerFactory.newPlayer(""));
		
		t = teamFactory.newTeam(teamName);
		for (Player p : players)
			t.addPlayer(p);
		
		assertEquals(teamName, t.getName());
		for (Player p : players)
			assertTrue(t.getPlayer(p.getName()) != null);
	}
	
}