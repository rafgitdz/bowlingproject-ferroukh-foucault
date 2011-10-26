package domain.model.game;

public abstract class AbstractFrame implements Frame {
	
	protected static final int TOTAL_PINS = 10;
	protected static final String ERROR_TOO_MUCH_PINS = "Impossible to knock down more than 10 pins";
	protected int roll1, roll2, roll3;
	protected boolean roll1Played, roll2Played, roll3Played;
	
	public AbstractFrame() {
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
		return ((roll1 + roll2) == TOTAL_PINS && !isStrike() );
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

}




