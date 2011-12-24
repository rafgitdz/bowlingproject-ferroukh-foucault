package domain.model.team;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.model.player.Player;
import domain.model.player.PlayerFactoryForTest;

public class TestTeam {

	private PlayerFactoryForTest playerFactory = new PlayerFactoryForTest();
	private TeamFactoryForTest teamFactory = new TeamFactoryForTest();
	private RepositoryTeamForTest repositoryTeam = new RepositoryTeamForTest();

	private String teamName = "The A Team";
	private String[] playersName = { "Hannibal", "Face", "B.A. Barracus",
			"Murdock", "Lynch" };

	@Test
	public void testCreateTeam() {
		
		repositoryTeam.save(teamFactory.newTeam(teamName));

		for (int i = 0; i < playersName.length; i++) {
			Player player = playerFactory.newPlayer(playersName[i]);
			Team t = repositoryTeam.load(teamName);
			t.addPlayer(player);
			repositoryTeam.update(t);
		}

		for (int i = 0; i < playersName.length; i++) {
			Team t = repositoryTeam.load(teamName);
			assertEquals(playersName[i], t.getPlayer(i).getName());
		}
	}

}