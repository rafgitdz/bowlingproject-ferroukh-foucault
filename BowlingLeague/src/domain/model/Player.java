package domain.model;

import java.util.Observable;
import java.util.Observer;

import domain.model.game.Game;
import domain.model.game.GameException;

public class Player extends Observable implements Observer {

	private static final String ERROR_NOT_YOUR_TURN = "It is not your turn!";
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
			displayError(ERROR_NOT_YOUR_TURN);
	}

	public void setItsMyTurn(boolean b) {
		
		itsMyTurn = b;
	}

	public int getScore() {
		
		return game.getScore();
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
	


}
