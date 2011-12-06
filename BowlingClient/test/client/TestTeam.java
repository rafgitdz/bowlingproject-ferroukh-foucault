package client;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import service.player.PlayerServiceRemote;
import service.team.TeamServiceRemote;
import context.PlayerRemoteGeneration;
import context.TeamRemoteGeneration;
import domain.model.exception.TeamException;

public class TestTeam {

	private TeamServiceRemote teamRemote;
	private PlayerServiceRemote playerRemote;

	@Before
	public void setUp() {
		teamRemote = TeamRemoteGeneration.getInstance();
		playerRemote = PlayerRemoteGeneration.getInstance();
	}

	@AfterClass
	public static void cleanService() {
		TeamRemoteGeneration.cleanInstance();
		PlayerRemoteGeneration.cleanInstance();
	}
	
	@Test
	public void testSaveOnlyTeam() {

		String expected = "Jaguars";
		teamRemote.newTeam("Jaguars", "Premiership");
		assertEquals(expected, teamRemote.rebuildTeam("Jaguars").getName());
	}
	
	@Test
	public void testSaveTeamWithPlayers() {
		
		String nameTeam = "Bulls";
		teamRemote.newTeam(nameTeam, "NBA");
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Jack"));
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Franck"));
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Matthew"));
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Lance"));
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Fabian"));
		String expected = "Franck";
		assertEquals(expected, teamRemote.rebuildTeam(nameTeam).getPlayersNames().get(1));
	}

	@Test
	public void testLoadTeam() {

		String nameTeam = "Fabulous";
		teamRemote.newTeam(nameTeam, "Liga");
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Milan"));
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Cyril"));
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Matthieu"));
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Rafik"));
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Alessia"));

		String expected = "Matthieu";
		assertEquals(expected, teamRemote.rebuildTeam(nameTeam)
				.getPlayersNames().get(2));
	}

	@Test(expected = TeamException.class)
	public void testDeleteTeam() {

		String nameTeam = "Maggpies";
		teamRemote.newTeam(nameTeam, "BundesLiga");
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Marc"));
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Anthony"));
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Samy"));
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Alex"));
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Marine"));
		teamRemote.deleteTeam("Maggpies");
		teamRemote.loadTeam("Maggpies");
	}
}
