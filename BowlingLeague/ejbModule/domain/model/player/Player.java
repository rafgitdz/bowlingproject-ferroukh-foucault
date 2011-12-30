package domain.model.player;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import javax.ejb.EJB;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import domain.model.exception.GameException;
import domain.model.team.Team;
import domain.service.DuelServiceLocal;

@Entity
public class Player implements Observer, Serializable {

	private static final long serialVersionUID = 4505445387411090683L;
	private static final String ERROR_NOT_YOUR_TURN = ", it's not your turn!";

	
	@EJB
	@Transient
	DuelServiceLocal duelService;
	
	@Id
	private String name;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "Game")
	Game currentGame;
	
	PlayerStatus status;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	Player opponent;

	@ManyToOne
	@JoinColumn(name = "Team_Id")
	Team team;
	
	Player() {
	}

	Player(String name) {

		this.name = name;
		status = PlayerStatus.Waiting;
	}

	public void roll(int pinsDown) {

		if (status == PlayerStatus.Playing)
			currentGame.roll(pinsDown);
		else
			displayError(this.getName() + ERROR_NOT_YOUR_TURN);
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

		duelService.finishTurn(this);
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
	
	public void play() {
		status = PlayerStatus.Playing;
	}
	
	public void waitForOpponent() {
		status = PlayerStatus.Waiting;
	}

	public Player getOpponent() {
		return opponent;
	}

	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	public Team getTeam() {
		return this.team;
	}

	public PlayerStatus getStatus() {
		return status;
	}
}
