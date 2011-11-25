package domain.model.league;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import domain.model.player.Player;

@Entity
public class Duel extends Observable implements Observer, Serializable {

	private static final long serialVersionUID = -3615220487693083734L;
	private static final String GAME_NOT_OVER = "Game doesn't over for ";
	private static final String NO_DRAW_DUEL = "No Draw Match !";
	private static final String NOT_SAME_PLAYER_IN_DUEL = "No same player in a duel !";
	public static final String DUEL_NOT_EXIST = "Duel doesn't exist !";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@OneToOne
	@JoinColumn(name = "PLAYER_1_ID")
	private Player player1;
	@OneToOne
	@JoinColumn(name = "PLAYER_2_ID")
	private Player player2;
	int scorePlayer1;
	int scorePlayer2;

	public Duel() {
	}

	public Duel(Player p1, Player p2) {

		if (p1.getName().equals(p2.getName()))
			displayError(NOT_SAME_PLAYER_IN_DUEL);
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

		// return (player1.getScore() > player2.getScore()) ? player1 : player2;

		if (player1.getScore() > player2.getScore())
			return player1;
		else if (player1.getScore() < player2.getScore())
			return player2;
		else
			throw new DuelException(NO_DRAW_DUEL);

		// Random aleatoire = new Random();
		// int win = aleatoire.nextInt(2);
		// return win + 1 == 1 ? player1 : player2;
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

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}
}
