package client.infrastructure;

import org.junit.Before;
import org.junit.Test;

import domain.model.league.Duel;
import domain.model.player.Player;

public class TestPersistenceDuel {

	Player player1;
	Player player2;
	Duel duel;

	@Before
	public void setUp() {

		player1 = new Player("Jack");
		player2 = new Player("Mike");
		duel = new Duel(player1, player2);
	}

	@Test
	public void testPersistDuel() {
		
	}

}
