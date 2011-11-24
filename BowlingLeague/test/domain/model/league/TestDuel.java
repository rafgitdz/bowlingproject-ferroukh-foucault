package domain.model.league;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import domain.model.league.Duel;
import domain.model.league.DuelException;
import domain.model.player.GameException;
import domain.model.player.Player;

public class TestDuel {

	private Duel duel;
	private Player p1, p2;

	@Before
	public void setUp() {
		p1 = new Player("Bob");
		p2 = new Player("Alice");
		duel = new Duel(p1, p2);
	}

	@Test(expected = DuelException.class)
	public void testSamePlayer() {
		duel = new Duel(p1, p1);
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
		assertEquals(expected, duel.getWinner());
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
		duel.getWinner();
	}
}