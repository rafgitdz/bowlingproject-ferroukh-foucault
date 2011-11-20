package domain.model.game;

public class LastFrame extends AbstractFrame {

	private static final long serialVersionUID = -5353520182708691879L;

	@Override
	public boolean isPlayed() {

		if (!isStrike() && !isSpare())
			return super.isPlayed();
		else
			return roll3Played && super.isPlayed();
	}

	@Override
	public void roll(int pinsDown) {

		super.roll(pinsDown);

		if (!roll1Played) {
			roll1 = pinsDown;
			roll1Played = true;
		} else if (!roll2Played) {
			roll2 = pinsDown;
			roll2Played = true;
		} else if (!roll3Played && (isStrike() || isSpare())) {
			roll3 = pinsDown;
			roll3Played = true;
		}
	}

}
