package domain.model.player;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Player extends Observable implements Observer, Serializable {

	private static final long serialVersionUID = 4505445387411090683L;
	private static final String ERROR_NOT_YOUR_TURN = " !, It is not your turn!";
	public static final String PLAYER_NOT_EXIST = "Player doesn't exist !";

	@Id
	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Game")
	private Game currentGame;
	private boolean itsMyTurn;

	protected Player() {
	}

	Player(String name) {

		this.name = name;
		itsMyTurn = false;
	}

	public void roll(int pinsDown) {

		if (itsMyTurn)
			currentGame.roll(pinsDown);
		else
			displayError(this.getName() + ERROR_NOT_YOUR_TURN);

	}

	public void setItsMyTurn(boolean b) {
		itsMyTurn = b;
	}

	public int getScore() {

		return currentGame.getScore();
	}

	private void displayError(String message) {

		throw new GameException(message);
	}

	/**
	 * Update is called whenever the player has finished his frame
	 */
	@Override
	public void update(Observable o, Object arg) {

		setChanged();
		notifyObservers();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Game getGame() {
		return currentGame;
	}

	public void setGame(Game game) {
		currentGame = game;
		currentGame.addObserver(this);
	}
}
