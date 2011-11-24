package client;

import static org.junit.Assert.assertEquals;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import service.team.TeamServiceRemote;
import domain.model.player.Player;
import domain.model.team.Team;

public class TestPersistenceTeam {

	private TeamServiceRemote teamRemote;
	
	@Before
	public void setUp() {
		
		try {

			Context context = new InitialContext();
			teamRemote = (TeamServiceRemote) context
					.lookup("TeamService/remote");

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSaveOnlyTeam() {
		
		String expected = "Jaguars";
		Team team = teamRemote.newTeam("Jaguars");
		assertEquals(expected, team.getName());
	}
	
	@Test
	public void testSaveAllTeam() {
		
		String expected = "Matthew";
		Team team = teamRemote.newTeam("Cotagers");
		team.addPlayer(new Player("Jack"));
		team.addPlayer(new Player("Franck"));
		team.addPlayer(new Player("Matthew"));
		team.addPlayer(new Player("Lance"));
		team.addPlayer(new Player("Fabian"));
		teamRemote.saveTeam(team);
		assertEquals(expected, team.getPlayer(2).getName());
	}
	
	@Test
	public void testLoadTeam() {

		String expected = "Leo";
		Team team = teamRemote.newTeam("Canadians");
		team.addPlayer(new Player("Milan"));
		team.addPlayer(new Player("Cyril"));
		team.addPlayer(new Player("Leo"));
		team.addPlayer(new Player("Rafik"));
		team.addPlayer(new Player("Alessia"));
		teamRemote.saveTeam(team);
		team = teamRemote.loadTeam(team.getName());
		assertEquals(expected, team.getPlayer(2).getName());
	}

}
