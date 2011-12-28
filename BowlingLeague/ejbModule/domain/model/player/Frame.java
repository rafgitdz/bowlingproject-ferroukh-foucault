package domain.model.player;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface Frame extends Serializable {

	public int getId();

	public void roll(int pinsDown);

	public boolean isStrike();

	public boolean isSpare();

	public boolean isPlayed();

	public int getRoll1();

	public int getRoll2();

	public int getRoll3();
	
	public void resetFrame();

}
