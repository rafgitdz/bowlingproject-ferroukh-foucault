package service.player;

import javax.ejb.Remote;

import domain.model.player.Player;

@Remote
public interface PlayerServiceRemote {

	public void roll(int roll);

	public int getScore();

	public String getName();

	public void getStat();

	public Player newPlayer(String name);

	public void savePlayer(Player player);

	Player loadPlayer(String name);

	public void deletePlayer(Player player);

}
