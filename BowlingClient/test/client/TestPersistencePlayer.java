package client;

import static org.junit.Assert.*;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import service.player.PlayerServiceRemote;
import domain.model.player.Player;

public class TestPersistencePlayer {

	private PlayerServiceRemote playerRemote;
	private Player player;
	private String expected;

	@Before
	public void setUp() throws Exception {

		try {

			Context context = new InitialContext();
			playerRemote = (PlayerServiceRemote) context
					.lookup("PlayerService/remote");

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSavePlayer() {

		expected = "Mary";
		player = playerRemote.newPlayer(expected);
		assertEquals(expected, player.getName());
	}

	@Test
	public void testLoadPlayer() {

		expected = "Martin";
		player = playerRemote.newPlayer(expected);
		player = playerRemote.loadPlayer(expected);
		assertEquals(expected, player.getName());
	}

	@Test(expected = EJBException.class)
	public void testDeletePlayer() {

		expected = "Marvin";
		player = playerRemote.newPlayer(expected);
		playerRemote.deletePlayer(player);
		playerRemote.loadPlayer(expected);
	}
}