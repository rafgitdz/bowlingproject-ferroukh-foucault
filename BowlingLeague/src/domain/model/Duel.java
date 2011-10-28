package domain.model;

import java.util.Observable;
import java.util.Observer;

public class Duel implements Observer {

	private Player player1, player2;

	public Duel(Player p1, Player p2) {
		player1 = p1;
		p1.addObserver(this);
		player2 = p2;
		p2.addObserver(this);
		player1.setItsMyTurn(true);
	}

	public Player getWinner() {
		return (player1.getScore() > player2.getScore()) ? player1 : player2;
	}

	
	/**
	 * update is called whenever one of the players has finished his turn,
	 * and tells the other player that he can play
	 */
	@Override
	public void update(Observable o, Object arg) {
		player1.setItsMyTurn((Player) o == player2);
		player2.setItsMyTurn((Player) o == player1);
	}
}
