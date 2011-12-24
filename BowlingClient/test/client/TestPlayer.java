package client;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import application.service.player.PlayerServiceRemote;
import context.PlayerRemoteGeneration;
import domain.model.exception.PlayerException;

public class TestPlayer {

	private PlayerServiceRemote playerRemote;
	private String expected;

	@Before
	public void setUp() throws Exception {
		playerRemote = PlayerRemoteGeneration.getInstance();
	}
	
	@AfterClass
	public static void cleanServices() {
		PlayerRemoteGeneration.cleanInstance();
	}

	@Test
	public void testNewPlayer() {

		expected = "Mary";
		assertEquals(expected, playerRemote.newPlayer(expected).getName());
	}

	@Test
	public void testLoadPlayer() {

		expected = "Martin";
		playerRemote.newPlayer(expected);
		assertEquals(expected, playerRemote.loadPlayer(expected).getName());
	}

	@Test(expected = PlayerException.class)
	public void testDeletePlayer() {

		expected = "Marvin";
		playerRemote.deletePlayer(playerRemote.newPlayer(expected).getName());
		playerRemote.loadPlayer(expected);
	}
}