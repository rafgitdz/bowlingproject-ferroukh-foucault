package client;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import application.service.player.PlayerServiceRemote;
import context.PlayerRemoteGeneration;
import domain.model.exception.PlayerException;
import domain.model.player.Player;

public class TestPlayer {

	private PlayerServiceRemote playerRemote;
	private String playerName = "Marvin Martin";

	@Before
	public void setUp() throws Exception {
		playerRemote = PlayerRemoteGeneration.getInstance();
	}
	
	@After
	public void tearDown() {
		
		playerRemote.deletePlayer(playerName);
	}
	
	@AfterClass
	public static void cleanServices() {
		PlayerRemoteGeneration.cleanInstance();
	}

	@Test
	public void testNewPlayer() {
		Player marvin = playerRemote.newPlayer(playerName);
		assertEquals(playerName, marvin.getName());
	}

	@Test
	public void testLoadPlayer() {

		playerRemote.newPlayer(playerName);
		Player marvin = playerRemote.loadPlayer(playerName);
		assertEquals(playerName, marvin.getName());
	}

	@Test(expected = PlayerException.class)
	public void testDeletePlayer() {
		
		playerRemote.newPlayer(playerName);
		playerRemote.deletePlayer(playerName);
		playerRemote.loadPlayer(playerName);
	}
}