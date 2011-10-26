package domain.model;

import java.util.Observable;
import java.util.Observer;

import domain.model.game.Game;
import domain.model.game.GameException;

public class Player extends Observable implements Observer {

	private String name;
	private Game game;
	private boolean itsMyTurn;

	public Player(String name) {
		this.name = name;
		game = new Game();
		game.addObserver(this);
		itsMyTurn = false;
	}

	public void roll(int pinsDown) {
		if (itsMyTurn)
			game.roll(pinsDown);
		else
			throw new GameException(name + ", it's not your turn!");
	}

	public void setItsMyTurn(boolean b) {
		itsMyTurn = b;
	}

	public int getScore() {
		return game.getScore();
	}

	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}

}
