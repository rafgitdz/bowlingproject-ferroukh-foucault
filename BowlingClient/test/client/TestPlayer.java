package client;

import static org.junit.Assert.assertEquals;

import javax.ejb.EJBException;

import org.junit.Before;
import org.junit.Test;

import service.player.PlayerServiceRemote;
import context.PlayerRemoteGeneration;

public class TestPlayer {

	private PlayerServiceRemote playerRemote;
	private String expected;

	@Before
	public void setUp() throws Exception {
		playerRemote = PlayerRemoteGeneration.getInstance();
	}

	@Test
	public void testSavePlayer() {

		expected = "Mary";
		assertEquals(expected, playerRemote.newPlayer(expected).getName());
	}

	@Test
	public void testLoadPlayer() {

		expected = "Martin";
		playerRemote.newPlayer(expected);
		assertEquals(expected, playerRemote.loadPlayer(expected).getName());
	}

	@Test(expected = EJBException.class)
	public void testDeletePlayer() {

		expected = "Marvin";
		playerRemote.deletePlayer(playerRemote.newPlayer(expected));
		playerRemote.loadPlayer(expected);
	}
}