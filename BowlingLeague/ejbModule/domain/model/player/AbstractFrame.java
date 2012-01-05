package domain.model.player;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import domain.model.exception.FrameException;

@Entity
public class AbstractFrame implements Frame {

	private static final long serialVersionUID = 8033550006877254984L;
	protected static final int TOTAL_PINS = 10;
	protected static final String ERROR_TOO_MUCH_PINS = "Impossible to knock down more than 10 pins";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	protected int roll1, roll2, roll3;
	protected boolean roll1Played, roll2Played, roll3Played;

	AbstractFrame() {
	
		roll1 = 0;
		roll2 = 0;
		roll3 = 0;
		roll1Played = false;
		roll2Played = false;
		roll3Played = false;
	}

	public void roll(int pinsDown) {
		if (pinsDown > TOTAL_PINS)
			displayError(ERROR_TOO_MUCH_PINS);
	}

	public boolean isStrike() {
		return (roll1 == TOTAL_PINS);
	}

	public boolean isSpare() {
		return ((roll1 + roll2) == TOTAL_PINS && !isStrike());
	}

	@Override
	public boolean isPlayed() {
		return roll1Played && roll2Played;
	}

	public int getRoll1() {
		return roll1;

	}

	public int getRoll2() {
		return roll2;

	}

	public int getRoll3() {
		return roll3;

	}

	protected void displayError(String error) {
		throw new FrameException(error);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public void resetFrame() {
		this.roll1 = 0;
		this.roll2 = 0;
		this.roll3 = 0;
		this.roll1Played = false;
		this.roll2Played = false;
		this.roll3Played = false;
	}

	@Override
	public boolean isRoll1Played() {
		return roll1Played;
	}

	@Override
	public boolean isRoll2Played() {
		return roll2Played;
	}

	@Override
	public boolean isRoll3Played() {
		return roll3Played;
	}
}
