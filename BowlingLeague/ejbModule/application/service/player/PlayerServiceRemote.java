package application.service.player;

import javax.ejb.Remote;

import domain.model.player.Player;

@Remote
public interface PlayerServiceRemote {

	public void roll(String name, int roll);

	public int getScore(String name);

	public void getStat(String name);

	public Player newPlayer(String name);

	public Player loadPlayer(String name);

	public void deletePlayer(String name);

}
