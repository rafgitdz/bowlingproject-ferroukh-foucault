package domain.model.game;

public class NormalFrame extends AbstractFrame {

	@Override
	public void roll(int pinsDown) {

		super.roll(pinsDown);
		
		if (!roll1Played) {
			roll1 = pinsDown;
			roll1Played = true;
			if (isStrike())
				roll2Played = true;
		}

		else if (!roll2Played) {
			if (roll1 + pinsDown > TOTAL_PINS) displayError(ERROR_TOO_MUCH_PINS);
				
			roll2 = pinsDown;
			roll2Played = true;
		}

	}

}
