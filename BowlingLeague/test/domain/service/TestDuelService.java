package domain.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import domain.model.exception.DuelException;
import domain.model.exception.GameException;
import domain.model.player.Player;
import domain.model.player.PlayerFactoryForTest;

public class TestDuelService {

	private PlayerFactoryForTest playerFactory = new PlayerFactoryForTest();
	private DuelServiceForTest duelService = new DuelServiceForTest();
	
	
	private Player p1, p2;

	@Before
	public void setUp() {
		p1 = playerFactory.newPlayer("Bob");
		p2 = playerFactory.newPlayer("Alice");
		duelService.startDuel(p1, p2);
	}

	@Test(expected = DuelException.class)
	public void testSamePlayer() {
		duelService.startDuel(p1, p1);
	}

	@Test
	public void testPlayDuel() {

		for (int i = 0; i < 10; i++) {
			p1.roll(4);
			p1.roll(5);
			p2.roll(3);
			p2.roll(4);
		}
		Player expected = p1;
		assertEquals(expected, duelService.getWinner(p1,p2));
	}

	@Test(expected = GameException.class)
	public void testAlternateTurns() {
		p1.roll(10);
		p1.roll(4);
		p1.roll(7);
	}

	@Test(expected = DuelException.class)
	public void testDrawDuel() {

		for (int i = 0; i < 10; i++) {
			p1.roll(4);
			p1.roll(4);
			p2.roll(4);
			p2.roll(4);
		}
		duelService.getWinner(p1,p2);
	}
}
