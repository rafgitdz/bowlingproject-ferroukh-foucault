package client.challenge;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import service.team.TeamServiceRemote;

public class TestTeamClient {

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
		
		ArrayList<String> listPlayers = new ArrayList<String>(5);
		listPlayers.add("Mike");
		listPlayers.add("Fabrice");
		listPlayers.add("Noella");
		listPlayers.add("Magdalene");
		listPlayers.add("Josh");
		teamRemote.createTeam("Jaguars", listPlayers);
	}

	@Test
	public void testName() {
		String expected = "Josh";
		assertEquals(expected, teamRemote.getPlayersNames().get(4));
	}

}
