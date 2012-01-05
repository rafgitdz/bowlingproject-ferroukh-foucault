package client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import application.service.league.LeagueServiceRemote;
import application.service.player.PlayerServiceRemote;
import application.service.team.TeamServiceRemote;
import context.LeagueRemoteGeneration;
import context.PlayerRemoteGeneration;
import context.TeamRemoteGeneration;
import domain.model.exception.PlayerException;
import domain.model.exception.TeamException;
import domain.model.player.Player;
import domain.model.team.Team;

public class TestTeam {

	private TeamServiceRemote teamRemote;
	private PlayerServiceRemote playerRemote;
	private LeagueServiceRemote leagueRemote;

	private String[] playersName = { "Freddie", "Patrick", "Dennis", "Titi",
			"Sol" };
	private String teamName = "Gunners";
	private String leagueName = "Premiership";

	@Before
	public void setUp() {
		teamRemote = TeamRemoteGeneration.getInstance();
		playerRemote = PlayerRemoteGeneration.getInstance();
		leagueRemote = LeagueRemoteGeneration.getInstance();
	}

	@After
	public void tearDown() {

		for (int i = 0; i < playersName.length; i++) {
			try {
				playerRemote.deletePlayer(playersName[i]);
			} catch (PlayerException e) {

			}
		}
		leagueRemote.deleteLeague(leagueName);
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
		Team[] teams = teamRemote.getAllTeams();
		boolean containsTeam = false;
		for (Team t : teams)
			if (t.getName().equals(teamName)) {
				containsTeam = true;
				break;
			}
		assertTrue(containsTeam);
	}

	@Test
	public void testSaveTeamWithPlayers() {

		teamRemote.newTeam(teamName, leagueName);
		for (int i = 0; i < playersName.length; i++) {
			playerRemote.newPlayer(playersName[i]);
			assertFalse(playerRemote.isInATeam(playersName[i]));
			teamRemote.addPlayer(teamName, playersName[i]);
			assertTrue(playerRemote.isInATeam(playersName[i]));
		}

		for (int i = 0; i < playersName.length; i++) {
			assertEquals(playersName[i],
					teamRemote.getPlayersNames(teamName)[i]);
		}
	}

	@Test
	public void testLoadTeamEager() {

		teamRemote.newTeam(teamName, leagueName);
		for (int i = 0; i < playersName.length; i++) {
			playerRemote.newPlayer(playersName[i]);
			teamRemote.addPlayer(teamName, playersName[i]);
		}

		Player[] gunners = teamRemote.getPlayers(teamName);
		for (int i = 0; i < playersName.length; i++) {
			assertEquals(playersName[i], gunners[i].getName());
		}

	}
	
	@Test(expected= PlayerException.class)
	public void testAddUnknownPlayer() {
		teamRemote.newTeam(teamName, leagueName);
		teamRemote.addPlayer(teamName, "toto");
	}
	
	@Test(expected= TeamException.class)
	public void testAddPlayerAlreadyInTeam() {
		teamRemote.newTeam(teamName, leagueName);
		
		playerRemote.newPlayer(playersName[0]);
		teamRemote.addPlayer(teamName, playersName[0]);
		
		teamRemote.addPlayer(teamName, playersName[0]);
	}
	
	@Test(expected = TeamException.class)
	public void testCreateTeamTwice() {
		teamRemote.newTeam(teamName, leagueName);
		teamRemote.newTeam(teamName, leagueName);
		
	}
}
