package client.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import service.game.GameServiceRemote;
import domain.model.game.Game;

public class TestPersistenceGame {

	private GameServiceRemote gameRemote;
	private Game game;

	@Before
	public void setUp() {

		try {

			Context context = new InitialContext();
			gameRemote = (GameServiceRemote) context
					.lookup("GameService/remote");

		} catch (NamingException e) {
			e.printStackTrace();
		}

		game = gameRemote.newGame();
	}

	@Test
	public void testPersistGame() {
		assertFalse(game.getId() == 0);
	}

	@Test
	public void testLoadGame() {
		
		Game g2 = gameRemote.loadGame(game.getId());
		assertEquals(game.getId(), g2.getId());
		assertEquals(game.getScore(), g2.getScore());
		assertEquals(game.getCurrentFrameNumber(), g2.getCurrentFrameNumber());
	}

	@Test
	public void testLoadAndSaveGame() {
		
		game = gameRemote.loadGame(game.getId());
		game.roll(10);
		game.roll(4);
		game.roll(3);
		gameRemote.saveGame(game);
		assertEquals(game.getScore(), gameRemote.loadGame(game.getId())
				.getScore());
	}

	@Test(expected = EJBException.class)
	public void testLoadUnkwnownGame() {
		game = gameRemote.loadGame(1233);
	}
}
