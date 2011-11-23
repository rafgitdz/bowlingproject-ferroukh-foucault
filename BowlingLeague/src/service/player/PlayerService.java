package service.player;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import domain.model.player.Player;
import domain.model.player.PlayerException;
import domain.model.player.RepositoryPlayer;

@Stateful
public class PlayerService implements PlayerServiceRemote {

	private static final String PLAYER_NOT_EXIST = "Player doesn't exist !";
	Player player;

	@EJB
	private RepositoryPlayer ePJPA;

	@Override
	public Player createPlayer(String name) {
		return player = new Player(name);
	}

	@Override
	public void roll(int roll) {

		if (player != null)
			player.roll(roll);
		else
			throw new PlayerException(PLAYER_NOT_EXIST);
	}

	@Override
	public int getScore() {
		return player.getScore();
	}

	@Override
	public String getName() {
		return player.getName();
	}

	@Override
	public void getStat() {
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public Player newPlayer(String name) {
		player = ePJPA.save(new Player(name));
		return player;
	}

	@Override
	public void savePlayer(Player player) {
		ePJPA.save(player);
	}

	@Override
	public Player loadPlayer(String name) {

		Player player = ePJPA.load(name);
		if (player == null)
			throw new PlayerException(Player.PLAYER_NOT_EXIST);
		return player;
	}

	@Override
	public void deletePlayer(Player player) {
		ePJPA.delete(player);
	}
}
