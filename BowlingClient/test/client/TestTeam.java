package client;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import application.service.player.PlayerServiceRemote;
import application.service.team.TeamServiceRemote;
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
		assertEquals(expected, teamRemote.loadTeam("Jaguars").getName());
	}
	
	@Test
	public void testSaveTeamWithPlayers() {
		
		String nameTeam = "Bulls";
		teamRemote.newTeam(nameTeam, "NBA");
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Jack").getName());
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Franck").getName());
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Matthew").getName());
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Lance").getName());
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Fabian").getName());
		String expected = "Franck";
		assertEquals(expected, teamRemote.loadTeam(nameTeam).getPlayersNames().get(1));
	}

	@Test
	public void testLoadTeam() {

		String nameTeam = "Fabulous";
		teamRemote.newTeam(nameTeam, "Liga");
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Milan").getName());
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Cyril").getName());
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Matthieu").getName());
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Rafik").getName());
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Alessia").getName());

		String expected = "Matthieu";
		assertEquals(expected, teamRemote.loadTeam(nameTeam)
				.getPlayersNames().get(2));
	}

	@Test(expected = TeamException.class)
	public void testDeleteTeam() {

		String nameTeam = "Maggpies";
		teamRemote.newTeam(nameTeam, "BundesLiga");
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Marc").getName());
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Anthony").getName());
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Samy").getName());
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Alex").getName());
		teamRemote.addPlayer(nameTeam, playerRemote.newPlayer("Marine").getName());
		teamRemote.deleteTeam("Maggpies");
		teamRemote.loadTeam("Maggpies");
	}
}
