package domain.model.game;

import java.util.Observable;

public class Game extends Observable {

	private static final int MAX_FRAMES = 10;
	private static final String ERROR_GAME_ENDED = "GAME OVER !";

	private Frame[] frames;
	private int currentFrame;

	public Game() {
		
		this.frames = new Frame[MAX_FRAMES];
		this.currentFrame = 0;
		
		for (int i = 0; i < MAX_FRAMES-1; i++) {
			
			frames[i] = new NormalFrame();
		}
		frames[MAX_FRAMES-1] = new LastFrame();
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
		if(isOver()) displayError(ERROR_GAME_ENDED);
		frames[currentFrame].roll(pins);
		nextFrame();
	}

	private void nextFrame() {

		if (frames[currentFrame].isPlayed() && currentFrame < MAX_FRAMES - 1) {
			currentFrame++;
			setChanged();
		}
		else if (isOver()) {
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

			if (frames[frameNumber + 1].isStrike() && !isLastFrame(frameNumber+1)) {
				
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


}
