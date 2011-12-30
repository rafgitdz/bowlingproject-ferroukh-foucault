package client;

import static org.junit.Assert.assertEquals;

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
			assertEquals(playersName[i], teamRemote.getPlayersNames(teamName)
					.get(i));
		}
	}

	@Test
	public void testLoadTeamEager() {

		teamRemote.newTeam(teamName, leagueName);
		for (int i = 0; i < playersName.length; i++) {
			playerRemote.newPlayer(playersName[i]);
			teamRemote.addPlayer(teamName, playersName[i]);
		}

		Team gunners = teamRemote.loadTeam(teamName);
		for (int i = 0; i < playersName.length; i++) {
			assertEquals(playersName[i], gunners.getPlayer(i).getName());
		}

	}
}
