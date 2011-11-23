package service.player;

import javax.ejb.Remote;

import domain.model.player.Player;

@Remote
public interface PlayerServiceRemote {

	public Player createPlayer(String name);

	public void roll(int roll);

	public int getScore();

	public String getName();

	public Player getPlayer();

	public void getStat();

	public void deletePlayer(Player player);

	Player loadPlayer(String name);

	void savePlayer(Player player);

	Player newPlayer(String name);

}
