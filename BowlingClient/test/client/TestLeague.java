package client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import application.service.league.LeagueServiceRemote;
import application.service.team.TeamServiceRemote;
import context.LeagueRemoteGeneration;
import context.PlayerRemoteGeneration;
import context.TeamRemoteGeneration;
import domain.model.exception.LeagueException;
import domain.model.team.Team;

public class TestLeague {

	private LeagueServiceRemote leagueRemote;
	private TeamServiceRemote teamRemote;
//	private PlayerServiceRemote playerRemote;
	List<Team> teams;
	List<String> teamNames;

	@Before
	public void setUp() {

		teamRemote = TeamRemoteGeneration.getInstance();
		leagueRemote = LeagueRemoteGeneration.getInstance();
//		playerRemote = PlayerRemoteGeneration.getInstance();

		teams = new ArrayList<Team>();
		teamNames = new ArrayList<String>();
	}

	@AfterClass
	public static void cleanServices() {
		TeamRemoteGeneration.cleanInstance();
		PlayerRemoteGeneration.cleanInstance();
		LeagueRemoteGeneration.cleanInstance();
	}

	@Test
	public void testCreateLeague() {

		String leagueName = "PremierShip";
		teamRemote.newTeam("Citizens", leagueName);
		assertEquals(leagueName, leagueRemote.loadLeague(leagueName).getName());
	}

	@Test
	public void testCreateLeagueWithTeams() {

		String leagueName = "Ligue1";
		teamRemote.newTeam("RedBulls", leagueName);
		teamRemote.newTeam("Mavens", leagueName);
		String expected = "RedBulls";
		assertEquals(expected, leagueRemote.loadLeague(leagueName).getTeams().get(1)
				.getName());
	}

	@Test(expected = LeagueException.class)
	public void testDeleteLeague() {

		String leagueName = "BundesLiga";
		teamRemote.newTeam("Borussia", leagueName);
		teamRemote.newTeam("Bayern", leagueName);
		leagueRemote.deleteLeague(leagueName);
		String expected = leagueName;
		assertEquals(expected, leagueRemote.loadLeague(leagueName).getName());
	}
}
