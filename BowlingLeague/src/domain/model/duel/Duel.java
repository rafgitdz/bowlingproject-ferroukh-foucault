package domain.model.duel;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;


public class Duel extends Observable implements Observer, Serializable {

	private static final long serialVersionUID = -3615220487693083734L;
	private static final String GAME_NOT_OVER = "Game doesn't over for ";


	private int id;

	private Player player1;

	private Player player2;
	
	int scorePlayer1;
	int scorePlayer2;

	public Duel(Player p1, Player p2) {

		player1 = p1;
		p1.addObserver(this);
		player2 = p2;
		p2.addObserver(this);
		player1.setItsMyTurn(true);
		scorePlayer1 = 0;
		scorePlayer2 = 0;
	}

	public Player getWinner() {

		if (!player1.getGame().isOver())
			displayError(GAME_NOT_OVER + player1.getName());
		if (!player2.getGame().isOver())
			displayError(GAME_NOT_OVER + player2.getName());

		return (player1.getScore() > player2.getScore()) ? player1 : player2;
	}

	private void displayError(String error) {
		throw new DuelException(error);
	}

	/**
	 * update is called whenever one of the players has finished his turn, and
	 * tells the other player that he can play
	 */
	@Override
	public void update(Observable o, Object arg) {

		scorePlayer1 = player1.getScore();
		scorePlayer2 = player2.getScore();
		player1.setItsMyTurn((Player) o == player2);
		player2.setItsMyTurn((Player) o == player1);
		
		if (player1.getGame().isOver() && player2.getGame().isOver()) {
			setChanged();
			notifyObservers();
		}
	}

	public int getId() {
		return id;
	}
}
