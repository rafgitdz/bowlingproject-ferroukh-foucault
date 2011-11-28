package client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import service.league.LeagueServiceRemote;
import service.player.PlayerServiceRemote;
import service.team.TeamServiceRemote;
import context.LeagueRemoteGeneration;
import context.PlayerRemoteGeneration;
import context.TeamRemoteGeneration;
import domain.model.player.Player;
import domain.model.team.Team;

public class TestLeague {

	private LeagueServiceRemote leagueRemote;
	private TeamServiceRemote teamRemote;
	private PlayerServiceRemote playerRemote;
	String leagueName;
	List<Team> teams;
	List<String> teamNames;

	@Before
	public void setUp() {

		teamRemote = TeamRemoteGeneration.getInstance();
		leagueRemote = LeagueRemoteGeneration.getInstance();
		playerRemote = PlayerRemoteGeneration.getInstance();

		leagueName = "Premiership";
		teams = new ArrayList<Team>();
		teamNames = new ArrayList<String>();
		List<Player> playersList;
		List<String> playersNames = null;

		System.out.println("setUp");
		for (int i = 0; i < 2; i++) {

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

	@Test
	public void testCreateLeague() {

		leagueRemote.newLeague(leagueName, teamNames);
		// leagueRemote.addTeam(leagueRemote.getName(), teams.get(0).getName());
		// leagueRemote.addTeam(leagueRemote.getName(), teams.get(1).getName());
		// leagueRemote.startLeague(leagueName, teamNames);
		assertEquals(leagueName, leagueRemote.getName());
	}
}
