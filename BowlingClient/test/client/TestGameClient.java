package client;

import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ejb.EJBException;

import org.junit.Before;
import org.junit.Test;

import service.game.GameServiceRemote;

public class TestGameClient {

	private GameServiceRemote gameRemote;
	private List<Integer> rolls;

	@Before
	public void setUp() {
		
		rolls = new ArrayList<Integer>();
		
		try {

			Context context = new InitialContext();
			gameRemote = (GameServiceRemote) context
					.lookup("GameService/remote");
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testPlayGame() {

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
	}

	@Test(expected = EJBException.class)
	public void testRoll11Pins() {
		
		rolls.add(11);
		gameRemote.getScore(rolls);

	}
	
	@Test(expected = EJBException.class)
	public void testPlayAfterGameOver() {
		
		for (int i = 0; i < 21; i++) {
			
			rolls.add(0);
			gameRemote.getScore(rolls);
		}
	}

}
