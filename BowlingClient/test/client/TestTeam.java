package client;

import static org.junit.Assert.assertEquals;

import javax.ejb.EJBException;

import org.junit.Before;
import org.junit.Test;

import service.player.PlayerServiceRemote;
import service.team.TeamServiceRemote;
import context.PlayerRemoteGeneration;
import context.TeamRemoteGeneration;
import domain.model.team.Team;

public class TestTeam {

	private TeamServiceRemote teamRemote;
	private PlayerServiceRemote playerRemote;

	@Before
	public void setUp() {
		teamRemote = TeamRemoteGeneration.getInstance();
		playerRemote = PlayerRemoteGeneration.getInstance();
	}

	@Test
	public void testSaveOnlyTeam() {

		String expected = "Jaguars";
		Team team = teamRemote.newTeam("Jaguars");
		assertEquals(expected, team.getName());
	}

	@Test
	public void testSaveAllTeam() {

		teamRemote.newTeam("Cotagers");
		String expected = "Matthew";

		addPlayerTeam("Jack");
		addPlayerTeam("Franck");
		addPlayerTeam("Matthew");
		addPlayerTeam("Lance");
		addPlayerTeam("Fabian");

		assertEquals(expected, teamRemote.getPlayersNames().get(2));
	}

	@Test
	public void testLoadTeam() {

		String expected = "Leo";
		teamRemote.newTeam("Canadians");

		addPlayerTeam("Milan");
		addPlayerTeam("Cyril");
		addPlayerTeam("Leo");
		addPlayerTeam("Rafik");
		addPlayerTeam("Alessia");

		assertEquals(expected, teamRemote.loadTeam("Canadians")
				.getPlayersNames().get(2));
	}

	@Test(expected = EJBException.class)
	public void testDeleteTeam() {

		teamRemote.newTeam("Maggpies");

		addPlayerTeam("Marc");
		addPlayerTeam("Anthony");
		addPlayerTeam("Samy");
		addPlayerTeam("Alex");
		addPlayerTeam("Marine");

		teamRemote.deleteTeam("Maggpies");
		teamRemote.loadTeam("Maggpies");
	}

	private void addPlayerTeam(String name) {
		playerRemote.newPlayer(name);
		teamRemote.addPlayer(name);
	}
}
