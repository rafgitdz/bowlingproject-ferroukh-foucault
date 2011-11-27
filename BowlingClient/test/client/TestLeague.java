package client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import service.league.LeagueServiceRemote;
import service.team.TeamServiceRemote;
import context.LeagueRemoteGeneration;
import context.TeamRemoteGeneration;
import domain.model.team.Team;

public class TestLeague {

	private TeamServiceRemote teamRemote;
	private LeagueServiceRemote leagueRemote;
	String leagueName;

	@Before
	public void setUp() {
		teamRemote = TeamRemoteGeneration.getInstance();
		leagueRemote = LeagueRemoteGeneration.getInstance();
		leagueName = "Premiership";
	}

	@Test
	public void testCreateLeague() {

		List<Team> teams = new ArrayList<Team>();
		teams.add(teamRemote.newTeam("Cotagers"));
		teams.add(teamRemote.newTeam("Managers"));
		leagueRemote.createLeague(leagueName, teams);
		assertEquals(leagueName, leagueRemote.getName());
	}
}
