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

	@Before
	public void setUp() {

		teamRemote = TeamRemoteGeneration.getInstance();
		leagueRemote = LeagueRemoteGeneration.getInstance();
		playerRemote = PlayerRemoteGeneration.getInstance();

		leagueName = "Premiership";
		teams = new ArrayList<Team>();
		List<Player> playersList;
		List<String> playersNames = null;

		for (int i = 0; i < 2; i++) {

			playersList = new ArrayList<Player>();
			playersNames = new ArrayList<String>();

			for (int j = 0; j < 5; j++) {

				playersNames.add("Player" + i + j);
				playersList.add(playerRemote.newPlayer("Player" + i + j));
				System.out.println("Player" + i + j);
			}
			Team t = teamRemote.newTeam("Team" + i, playersNames);
			teams.add(t);
		}
	}

	@Test
	public void testCreateLeague() {
		leagueRemote.newLeague(leagueName, teams);
		assertEquals(leagueName, leagueRemote.getName());
	}
}
