package service.game;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface GameServiceRemote {
	
	public int getScore(List <Integer> rolls);
}
