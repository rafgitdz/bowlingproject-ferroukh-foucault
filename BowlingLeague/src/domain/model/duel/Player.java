package domain.model.duel;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import domain.model.game.Game;
import domain.model.game.GameException;

@Entity
public class Player extends Observable implements Observer, Serializable {

	private static final long serialVersionUID = 4505445387411090683L;
	private static final String ERROR_NOT_YOUR_TURN = " !, It is not your turn!";

	@Id
	private String name;

	@OneToOne
	@JoinColumn (name="G")
	private Game currentGame;
	@Embedded
	private boolean itsMyTurn;

	public Player(String name) {

		this.name = name;
		currentGame = new Game();
		currentGame.addObserver(this);
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

	public Game getGame() {
		return currentGame;
	}
}
