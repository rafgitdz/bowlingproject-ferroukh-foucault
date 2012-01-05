package client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
		try {
			playerRemote.deletePlayer(playerName);
		} catch (PlayerException e) {
		}
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

	@Test(expected = PlayerException.class)
	public void testDeletePlayer() {

		playerRemote.newPlayer(playerName);
		playerRemote.deletePlayer(playerName);
		playerRemote.rollTraining(playerName, 2);
	}

	@Test
	public void testRollPlayer() {

		playerRemote.newPlayer(playerName);
		for (int i = 0; i < 10; i++) {
			playerRemote.rollTraining(playerName, 4);
			assertFalse(playerRemote.isTrainingGameOver(playerName));
			playerRemote.rollTraining(playerName, 3);
		}
		assertTrue(playerRemote.isTrainingGameOver(playerName));
		int scorePlayer = 70;
		assertEquals(scorePlayer, playerRemote.getTrainingScore(playerName));
	}

	@Test
	public void testGetFramesToInt() {

		playerRemote.newPlayer(playerName);
		for (int i = 0; i < 5; i++) {
			playerRemote.rollTraining(playerName, 3);
			playerRemote.rollTraining(playerName, 4);
		}
		playerRemote.rollTraining(playerName, 10);
		for (int i = 0; i < 4; i++) {
			playerRemote.rollTraining(playerName, 6);
			playerRemote.rollTraining(playerName, 4);
		}
		playerRemote.rollTraining(playerName, 7);

		int[] rolls = playerRemote.getTrainingFrames(playerName);
		int[] expected = { 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 10, -1, 6, 4, 6, 4, 6,
				4, 6, 4, 7 };

		int i = 0;
		for (int j = 0; j < expected.length; ++i, ++j)
			assertEquals(rolls[i], expected[j]);
	}

	@Test
	public void testGetTotalScores() {

		playerRemote.newPlayer(playerName);
		for (int i = 0; i < 10; i++) {
			playerRemote.rollTraining(playerName, 3);
			playerRemote.rollTraining(playerName, 4);
		}

		int[] rolls = playerRemote.getDetailedTrainingScore(playerName);
		int[] expected = { 7, 14, 21, 28, 35, 42, 49, 56, 63, 70 };

		int i = 0, j = 0;
		for (; j < expected.length; ++i, ++j)
			assertEquals(rolls[i], expected[j]);
		
		playerRemote.newTrainingGame(playerName);
		playerRemote.rollTraining(playerName, 4);
		assertEquals(4, playerRemote.getTrainingScore(playerName));
	}
}
