package client;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import application.service.player.PlayerServiceRemote;
import application.service.team.TeamServiceRemote;
import context.PlayerRemoteGeneration;
import context.TeamRemoteGeneration;
import domain.model.exception.TeamException;
import domain.model.team.Team;

public class TestTeam {

	private TeamServiceRemote teamRemote;
	private PlayerServiceRemote playerRemote;

	
	private String[] playersName = {"Freddie", "Patrick", "Dennis", "Titi", "Sol"};
	private String teamName = "Gunners";
	private String leagueName = "Premiership";
	
	@Before
	public void setUp() {
		teamRemote = TeamRemoteGeneration.getInstance();
		playerRemote = PlayerRemoteGeneration.getInstance();
	}

	@After
	public void tearDown() {
		
		for (int i = 0; i < playersName.length; i++) {
			playerRemote.deletePlayer(playersName[i]);
		}
		teamRemote.deleteTeam(teamName);
	}
	
	@AfterClass
	public static void cleanService() {
		TeamRemoteGeneration.cleanInstance();
		PlayerRemoteGeneration.cleanInstance();
	}
	
	@Test
	public void testSaveOnlyTeam() {
		
		teamRemote.newTeam(teamName, leagueName);
		assertEquals(teamName, teamRemote.loadTeam(teamName).getName());
	}
	
	@Test
	public void testSaveTeamWithPlayers() {
		
		teamRemote.newTeam(teamName, leagueName);
		for (int i = 0; i < playersName.length; i++) {
			playerRemote.newPlayer(playersName[i]);
			teamRemote.addPlayer(teamName, playersName[i]);
		}
		
		for (int i = 0; i < playersName.length; i++) {
			assertEquals(playersName[i], teamRemote.getPlayersNames(teamName).get(i));
		}
		
		
	}

	@Test
	public void testLoadTeamEager() {

		teamRemote.newTeam(teamName, leagueName);
		for (int i = 0; i < playersName.length; i++) {
			playerRemote.newPlayer(playersName[i]);
			teamRemote.addPlayer(teamName, playersName[i]);
		}
		
		Team gunners = teamRemote.loadTeamEager(teamName);
		for (int i = 0; i < playersName.length; i++) {
			assertEquals(playersName[i], gunners.getPlayer(i));
		}
		
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
