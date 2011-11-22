package client.duel;

import static org.junit.Assert.*;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import service.player.PlayerServiceRemote;

public class TestPlayerClient {

	private PlayerServiceRemote playerRemote;

	@Before
	public void setUp() {

		try {

			Context context = new InitialContext();
			playerRemote = (PlayerServiceRemote) context
					.lookup("PlayerService/remote");

		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		playerRemote.createPlayer("Cesc");
	}

	@Test
	public void testName() {
		String expected = "Cesc";
		assertEquals(playerRemote.getName(), expected);
	}

	@Test(expected = EJBException.class)
	public void testOnlyOnePlayer() {

		playerRemote.roll(4);
		playerRemote.roll(4);
	}
}
