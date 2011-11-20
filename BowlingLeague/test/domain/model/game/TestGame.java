package domain.model.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import domain.model.game.FrameException;
import domain.model.game.Game;
import domain.model.game.GameException;


public class TestGame {

	private Game game;
	
	@Before
	public void setUp() {
		game = new Game();
	}

	@Test
	public void testKnockDownPins() {
		game.roll(5);
		game.roll(2);
		assertEquals(7, game.getScore());
	}
	
	@Test
	public void testStrike() {
		game.roll(10);
		game.roll(3);
		game.roll(6);
		int expectedScore = 28;
		assertEquals(expectedScore, game.getScore());
	}
	
	@Test
	public void testSpare() {
		game.roll(4);
		game.roll(6);
		game.roll(2);
		int expectedScore = 14;
		assertEquals(expectedScore, game.getScore());
	}
	
	@Test
	public void testThreeStrikes() {
		game.roll(10);
		game.roll(10);
		game.roll(10);
		int expectedScore = 60;
		assertEquals(expectedScore, game.getScore());
	}

	@Test
	public void testStrikeLastFrame() {
		for(int i=0; i<18; i++){
			game.roll(0);
		}

		game.roll(10);
		game.roll(10);
		game.roll(10);
		
		int expectedScore = 30;
		assertEquals(expectedScore, game.getScore());
	}	
	
	@Test
	public void testSpareLastFrame() {
		
		for(int i=0; i<18; i++){
			game.roll(0);
		}

		game.roll(3);
		game.roll(7);
		game.roll(10);
		
		int expectedScore = 20;
		assertEquals(expectedScore, game.getScore());
	}
	
	@Test
	public void testPerfectGame() {
		
		for(int i=0; i<12; i++){
			game.roll(10);
		}
		
		int expectedScore = 300;
		assertEquals(expectedScore, game.getScore());
	}	
	
	@Test(expected=FrameException.class)
	public void testKnockdown11Pins() {
			game.roll(7);
			game.roll(4);	
	}	
	
	@Test(expected=FrameException.class)
	public void testKnockdown12Pins() {
		game.roll(12);
	}
	
	@Test
	public void testEndGame() {
		for (int i = 0; i < 20; i++) {
			assertFalse(game.isOver());
			game.roll(3);
		}
		assertTrue(game.isOver());
	}
	
	@Test(expected=GameException.class)
	public void testPlayGameEnded() {
		for (int i = 0; i < 21; i++) {
			game.roll(0);
		}
	}

}
