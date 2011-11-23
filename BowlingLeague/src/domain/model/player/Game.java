package domain.model.player;

import java.io.Serializable;
import java.util.Observable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.IndexColumn;

@Entity
public class Game extends Observable implements Serializable {

	private static final long serialVersionUID = 4684393781042750793L;
	private static final int MAX_FRAMES = 10;
	private static final String ERROR_GAME_ENDED = "GAME OVER FOR YOU !";
	public static final String UNKNOWN_GAME = "Unknown game !";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	@OneToMany(cascade = CascadeType.ALL, targetEntity = AbstractFrame.class)
	@IndexColumn(base = 0, name = "FrameNumber")
	private Frame[] frames;
	private int currentFrame;

	protected Game() {}
	
	Game(Frame[] frames) {

		this.frames = frames;
		this.currentFrame = 0;
	}
	
	public int getScore() {

		return getScore(currentFrame);
	}

	public int getScore(int frameNumber) {

		int result;
		if (frameNumber == 0) {
			result = 0;
		} else {
			result = getScore(frameNumber - 1);
		}

		result += getFrameScore(frameNumber);

		if (frames[frameNumber].isStrike()) {
			result += getStrikeBonus(frameNumber);
		} else if (frames[frameNumber].isSpare()) {
			result += getSpareBonus(frameNumber);
		}

		return result;
	}

	public void roll(int pins) {
		if (isOver())
			displayError(ERROR_GAME_ENDED);
		frames[currentFrame].roll(pins);
		nextFrame();
	}

	private void nextFrame() {

		if (frames[currentFrame].isPlayed() && (currentFrame < MAX_FRAMES - 1)) {
			currentFrame++;
			setChanged();
			
		} else if (isOver()) {
			setChanged();
		}
		notifyObservers();
	}

	public boolean isOver() {
		return (frames[MAX_FRAMES - 1] != null && frames[MAX_FRAMES - 1]
				.isPlayed());
	}

	private int getFrameScore(int frameNumber) {
		return frames[frameNumber].getRoll1() + frames[frameNumber].getRoll2();
	}

	private int getStrikeBonus(int frameNumber) {

		int result = 0;

		if (!isLastFrame(frameNumber)) {

			result += frames[frameNumber + 1].getRoll1()
					+ frames[frameNumber + 1].getRoll2();

			if (frames[frameNumber + 1].isStrike()
					&& !isLastFrame(frameNumber + 1)) {

				result += frames[frameNumber + 2].getRoll1();
			}

		} else
			result += frames[frameNumber].getRoll3();

		return result;

	}

	private int getSpareBonus(int frameNumber) {

		if (!isLastFrame(frameNumber))
			return frames[frameNumber + 1].getRoll1();
		else
			return getLastFrameBonus();
	}

	private boolean isLastFrame(int frameNumber) {
		return (frameNumber == MAX_FRAMES - 1);
	}

	private int getLastFrameBonus() {
		return frames[MAX_FRAMES - 1].getRoll3();
	}

	private void displayError(String error) {
		throw new GameException(error);
	}

	public int getCurrentFrameNumber() {
		return currentFrame;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
