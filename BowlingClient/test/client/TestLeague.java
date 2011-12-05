package client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import service.league.LeagueServiceRemote;
import service.player.PlayerServiceRemote;
import service.team.TeamServiceRemote;
import context.LeagueRemoteGeneration;
import context.PlayerRemoteGeneration;
import context.TeamRemoteGeneration;
import domain.model.exception.LeagueException;
import domain.model.player.Player;
import domain.model.team.Team;

public class TestLeague {

	private LeagueServiceRemote leagueRemote;
	private TeamServiceRemote teamRemote;
	private PlayerServiceRemote playerRemote;
	List<Team> teams;
	List<String> teamNames;

	@Before
	public void setUp() {

		teamRemote = TeamRemoteGeneration.getInstance();
		leagueRemote = LeagueRemoteGeneration.getInstance();
		playerRemote = PlayerRemoteGeneration.getInstance();

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
		leagueRemote.newLeague(leagueName, teamNames);
		assertEquals(leagueName, leagueRemote.loadLeague(leagueName).getName());
	}

	@Test
	public void testCreateLeagueWithTeams() {

		String leagueName = "Ligue1";
		build(0, 2);
		leagueRemote.newLeague(leagueName, teamNames);
		leagueRemote.addTeam(leagueName, teams.get(0).getName());
		leagueRemote.addTeam(leagueName, teams.get(1).getName());
		leagueRemote.startLeague(leagueName, teamNames);
		String expected = "Player00";
		assertEquals(expected, leagueRemote.getTeams(teamNames).get(0)
				.getPlayersNames().get(0));
	}

	@Test(expected = LeagueException.class)
	public void testDeleteLeague() {

		String leagueName = "BundesLiga";
		build(3, 5);
		leagueRemote.newLeague(leagueName, teamNames);
		leagueRemote.addTeam(leagueName, teams.get(0).getName());
		leagueRemote.addTeam(leagueName, teams.get(1).getName());
		leagueRemote.startLeague(leagueName, teamNames);
		leagueRemote.deleteLeague(leagueName);
		String expected = leagueName;
		assertEquals(expected, leagueRemote.loadLeague(leagueName).getName());
	}

	private void build(int k, int l) {

		List<Player> playersList;
		List<String> playersNames = null;

		for (int i = k; i < l; i++) {

			playersList = new ArrayList<Player>();
			playersNames = new ArrayList<String>();

			for (int j = 0; j < 5; j++) {

				playersNames.add("Player" + i + j);
				playersList.add(playerRemote.newPlayer("Player" + i + j));
				System.out.println("Player" + i + j);
			}
			Team t = teamRemote.newTeam("Team" + i, playersNames);
			teamNames.add("Team" + i);
			teams.add(t);
		}
	}
}
