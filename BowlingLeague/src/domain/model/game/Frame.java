package domain.model.game;

import java.io.Serializable;

public interface Frame extends Serializable {

	public int getId();

	public void roll(int pinsDown);

	public boolean isStrike();

	public boolean isSpare();

	public boolean isPlayed();

	public int getRoll1();

	public int getRoll2();

	public int getRoll3();

}