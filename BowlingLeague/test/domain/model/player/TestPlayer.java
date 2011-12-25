package domain.model.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import domain.model.exception.FrameException;
import domain.model.exception.GameException;

public class TestPlayer {

	private PlayerFactoryForTest playerFactory = new PlayerFactoryForTest();
	private Player player;

	@Before
	public void setUp() {
		player = playerFactory.newPlayer("Matteo");
		player.play();
	}

	@Test
	public void testCreatePlayer() {

		String expected = "Matteo";
		assertEquals(expected, player.getName());
	}

	@Test
	public void testKnockDownPins() {
		player.roll(5);
		player.roll(2);
		assertEquals(7, player.getScore());
	}
	
	@Test
	public void testStrike() {
		player.roll(10);
		player.roll(3);
		player.roll(6);
		int expectedScore = 28;
		assertEquals(expectedScore, player.getScore());
	}
	
	@Test
	public void testSpare() {
		player.roll(4);
		player.roll(6);
		player.roll(2);
		int expectedScore = 14;
		assertEquals(expectedScore, player.getScore());
	}
	
	@Test
	public void testThreeStrikes() {
		player.roll(10);
		player.roll(10);
		player.roll(10);
		int expectedScore = 60;
		assertEquals(expectedScore, player.getScore());
	}

	@Test
	public void testStrikeLastFrame() {
		for(int i=0; i<18; i++){
			player.roll(0);
		}

		player.roll(10);
		player.roll(10);
		player.roll(10);
		
		int expectedScore = 30;
		assertEquals(expectedScore, player.getScore());
	}	
	
	@Test
	public void testSpareLastFrame() {
		
		for(int i=0; i<18; i++){
			player.roll(0);
		}

		player.roll(3);
		player.roll(7);
		player.roll(10);
		
		int expectedScore = 20;
		assertEquals(expectedScore, player.getScore());
	}
	
	@Test
	public void testPerfectGame() {
		
		for(int i=0; i<12; i++){
			player.roll(10);
		}
		
		int expectedScore = 300;
		assertEquals(expectedScore, player.getScore());
	}	
	
	@Test(expected=FrameException.class)
	public void testKnockdown11Pins() {
		player.roll(7);
		player.roll(4);	
	}	
	
	@Test(expected=FrameException.class)
	public void testKnockdown12Pins() {
		player.roll(12);
	}
	
	@Test
	public void testEndGame() {
		for (int i = 0; i < 20; i++) {
			assertFalse(player.getGame().isOver());
			player.roll(3);
		}
		assertTrue(player.getGame().isOver());
	}
	
	@Test(expected=GameException.class)
	public void testPlayGameEnded() {
		for (int i = 0; i < 21; i++) {
			player.roll(0);
		}
	}
}
