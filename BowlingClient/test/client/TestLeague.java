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
import domain.model.exception.PlayerException;
import domain.model.exception.TeamException;

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
		try {
		leagueRemote.deleteLeague(leagueName);
		} catch (LeagueException e) {
		}
		for (int i = 0; i < teamNames.length; i++)
			try {
				teamRemote.deleteTeam(teamNames[i]);
			} catch (TeamException e) {
			}

		for (int i = 0; i < team1Players.length; i++)
			try {
				playerRemote.deletePlayer(team1Players[i]);
			} catch (PlayerException e) {
			}
		for (int i = 0; i < team2Players.length; i++)
			try {
				playerRemote.deletePlayer(team2Players[i]);
			} catch (PlayerException e) {
			}
		for (int i = 0; i < team3Players.length; i++)
			try {
				playerRemote.deletePlayer(team3Players[i]);
			} catch (PlayerException e) {
			}
		for (int i = 0; i < team4Players.length; i++)
			try {
				playerRemote.deletePlayer(team4Players[i]);
			} catch (PlayerException e) {
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

	@Test
	public void testPlayEntireLeague() {
		for (int i = 0; i < teamNames.length; i++) {
			teamRemote.newTeam(teamNames[i], leagueName);
		}
		for (int i = 0; i < team1Players.length; i++) {
			playerRemote.newPlayer(team1Players[i]);
			playerRemote.newPlayer(team2Players[i]);
			playerRemote.newPlayer(team3Players[i]);
			playerRemote.newPlayer(team4Players[i]);
			teamRemote.addPlayer(teamNames[0], team1Players[i]);
			teamRemote.addPlayer(teamNames[1], team2Players[i]);
			teamRemote.addPlayer(teamNames[2], team3Players[i]);
			teamRemote.addPlayer(teamNames[3], team4Players[i]);
		}

		leagueRemote.startLeague(leagueName);
		//playerRemote.roll(team1Players[0], 5);
	}
}
