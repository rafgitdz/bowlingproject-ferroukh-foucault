package domain.model.team;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import domain.model.exception.TeamException;
import domain.model.player.Player;
import domain.model.player.PlayerFactoryForTest;

public class TestTeam {

	private PlayerFactoryForTest playerFactory = new PlayerFactoryForTest();
	private TeamFactoryForTest teamFactory = new TeamFactoryForTest();
	private RepositoryTeamForTest repositoryTeam = new RepositoryTeamForTest();

	private String teamName = "The A Team";
	private String[] playersName = { "Hannibal", "Face", "B.A. Barracus",
			"Murdock", "Lynch" };

	@Before
	public void setUp() {
		repositoryTeam.save(teamFactory.newTeam(teamName));

		for (int i = 0; i < playersName.length; i++) {
			Player player = playerFactory.newPlayer(playersName[i]);
			Team t = repositoryTeam.load(teamName);
			t.addPlayer(player);
			repositoryTeam.update(t);
		}

	}
	
	@Test
	public void testCreateTeam() {
		
		for (int i = 0; i < playersName.length; i++) {
			Team t = repositoryTeam.load(teamName);
			assertEquals(playersName[i], t.getPlayer(i).getName());
		}
	}

	@Test(expected = TeamException.class)
	public void testMaxPlayers() {
		
		Team t = repositoryTeam.load(teamName);
		t.addPlayer(playerFactory.newPlayer("6th player"));
	}
	
	@Test
	public void testRemovePlayer() {
		
		Team t = repositoryTeam.load(teamName);
		int expectedSize = t.getPlayers().size() - 1;
		t.removePlayer(playersName[0]);
		assertEquals(expectedSize, t.getPlayers().size());
	}
	
	@Test(expected = TeamException.class)
	public void testRemoveUnknownPlayer() {
		
		Team t = repositoryTeam.load(teamName);
		t.removePlayer("random name");
	}

}