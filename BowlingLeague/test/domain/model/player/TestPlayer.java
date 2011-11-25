package domain.model.player;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import domain.model.player.Game;
import domain.model.player.GameException;
import domain.model.player.Player;

public class TestPlayer {

	private PlayerFactoryForTest playerFactory = new PlayerFactoryForTest();
	private Player player;

	@Before
	public void setUp() {
		player = playerFactory.newPlayer("Matteo");
	}

	@Test
	public void testCreatePlayer() {

		String expected = "Matteo";
		assertEquals(expected, player.getName());
	}

	@Test
	public void testGamePlayer() {

		Game game = player.getGame();
		for (int i = 0; i < 20; i++) {
			game.roll(3);
		}
		int expected = 60;
		assertEquals(expected, player.getScore());
	}

	@Test(expected = GameException.class)
	public void testRoll() {
		player.roll(4);
	}
}
