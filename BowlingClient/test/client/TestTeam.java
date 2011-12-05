package client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import service.player.PlayerServiceRemote;
import service.team.TeamServiceRemote;
import context.PlayerRemoteGeneration;
import context.TeamRemoteGeneration;
import domain.model.team.Team;
import domain.model.team.TeamException;

public class TestTeam {

	private TeamServiceRemote teamRemote;
	private PlayerServiceRemote playerRemote;
	private List<String> players;

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
		players = new ArrayList<String>();
		buildPlayer("Jack");
		buildPlayer("Franck");
		buildPlayer("Matthew");
		buildPlayer("Lance");
		buildPlayer("Fabian");
		Team team = teamRemote.newTeam("Jaguars", players);
		assertEquals(expected, team.getName());
	}

	@Test
	public void testSaveAllTeam() {

		String expected = "Estelle";
		players = new ArrayList<String>();
		buildPlayer("Chris");
		buildPlayer("David");
		buildPlayer("Estelle");
		buildPlayer("Magic");
		buildPlayer("Batman");

		teamRemote.newTeam("Cotagers", players);
		assertEquals(expected, teamRemote.getPlayersNames("Cotagers").get(2));
	}

	@Test
	public void testLoadTeam() {

		String expected = "Leo";
		players = new ArrayList<String>();
		buildPlayer("Milan");
		buildPlayer("Cyril");
		buildPlayer("Leo");
		buildPlayer("Rafik");
		buildPlayer("Alessia");

		teamRemote.newTeam("Fabulous", players);
		assertEquals(expected, teamRemote.loadTeamEager("Fabulous")
				.getPlayersNames().get(2));
	}

	@Test(expected = TeamException.class)
	public void testDeleteTeam() {

		players = new ArrayList<String>();
		buildPlayer("Marc");
		buildPlayer("Anthony");
		buildPlayer("Samy");
		buildPlayer("Alex");
		buildPlayer("Marine");

		teamRemote.newTeam("Maggpies", players);
		teamRemote.deleteTeam("Maggpies");
		teamRemote.loadTeam("Maggpies");
	}
	
	private void buildPlayer(String name) {
		playerRemote.newPlayer(name);
		players.add(name);
	}
}
