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
import domain.model.exception.LeagueException;

public class TestLeague {

	private LeagueServiceRemote leagueRemote;
	private TeamServiceRemote teamRemote;
	private PlayerServiceRemote playerRemote;

	String[] team1Players = { "t1_p1", "t1_p2", "t1_p3", "t1_p4", "t1_p5" };
	String[] team2Players = { "t2_p1", "t2_p2", "t2_p3", "t2_p4", "t2_p5" };
	String[] team3Players = { "t3_p1", "t3_p2", "t3_p3", "t3_p4", "t3_p5" };
	String[] team4Players = { "t4_p1", "t4_p2", "t4_p3", "t4_p4", "t4_p5" };

	String[] teamNames = { "t1", "t2", "t3", "t4" };
	String leagueName = "Ligue 1";

	@Before
	public void setUp() {
		teamRemote = TeamRemoteGeneration.getInstance();
		leagueRemote = LeagueRemoteGeneration.getInstance();
		playerRemote = PlayerRemoteGeneration.getInstance();

	}

	@After
	public void tearDown() {
		for (int i = 0; i < team1Players.length; i++)
			playerRemote.deletePlayer(team1Players[i]);
		for (int i = 0; i < team2Players.length; i++)
			playerRemote.deletePlayer(team2Players[i]);
		for (int i = 0; i < team3Players.length; i++)
			playerRemote.deletePlayer(team3Players[i]);
		for (int i = 0; i < team4Players.length; i++)
			playerRemote.deletePlayer(team4Players[i]);

		for (int i = 0; i < teamNames.length; i++)
			teamRemote.deleteTeam(teamNames[i]);
		try {
			leagueRemote.deleteLeague(leagueName);
		} catch (LeagueException e) {
		}

	}

	@AfterClass
	public static void cleanServices() {
		TeamRemoteGeneration.cleanInstance();
		PlayerRemoteGeneration.cleanInstance();
		LeagueRemoteGeneration.cleanInstance();
	}

	@Test
	public void testCreateLeagueWithTeams() {

		for (int i = 0; i < teamNames.length; i++) {
			teamRemote.newTeam(teamNames[i], leagueName);
		}
		for (int i = 0; i < teamNames.length; i++)
			assertEquals(leagueRemote.getTeams(leagueName).get(i).getName(),
					teamNames[i]);

	}

	@Test(expected = LeagueException.class)
	public void testDeleteLeague() {

		teamRemote.newTeam(teamNames[0], leagueName);
		leagueRemote.deleteLeague(leagueName);
		leagueRemote.getTeams(leagueName);
	}
}
