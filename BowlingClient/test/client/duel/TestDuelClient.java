package client.duel;

import static org.junit.Assert.assertEquals;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import service.duel.DuelServiceRemote;
import service.player.PlayerServiceRemote;

public class TestDuelClient {

	private DuelServiceRemote duelRemote;
	private PlayerServiceRemote playerRemote1;
	private PlayerServiceRemote playerRemote2;

	@Before
	public void setUp() {

		try {

			Context context = new InitialContext();
			duelRemote = (DuelServiceRemote) context
					.lookup("DuelService/remote");

//			Context context1 = new InitialContext();
			playerRemote1 = (PlayerServiceRemote) context
					.lookup("PlayerService/remote");

//			Context context2 = new InitialContext();
			playerRemote2 = (PlayerServiceRemote) context
					.lookup("PlayerService/remote");

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDuel() {

		duelRemote.createDuel(playerRemote1.createPlayer("Thiago"),
				playerRemote2.createPlayer("Leo"));

		for (int i = 0; i < 10; i++) {

			playerRemote1.roll(4);
			playerRemote1.roll(5);
			playerRemote2.roll(3);
			playerRemote2.roll(4);
		}

		String expected = "Leo";
		assertEquals(duelRemote.getWinner().getName(), expected);
	}
}
