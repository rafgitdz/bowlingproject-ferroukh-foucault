package client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Test;

import service.game.GameServiceRemote;

public class TestGameClient {

	@Test
	public void testPlayGame() {

		try {
			
			Context context = new InitialContext();
			GameServiceRemote gameRemote;
			gameRemote = (GameServiceRemote) context
					.lookup("GameService/remote");
			
			
			List<Integer> rolls = new ArrayList<Integer>();
			for (int i = 0; i < 5; i++) {
				rolls.add(3);
			}
			rolls.add(7);
			for (int i = 0; i < 5; i++) {
				rolls.add(10);
			}
			for (int i = 0; i < 2; i++) {
				rolls.add(4);
			}
			rolls.add(10);
			rolls.add(3);
			rolls.add(5);
			int expectedScore = 190;
			assertEquals(gameRemote.getScore(rolls), expectedScore);
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
