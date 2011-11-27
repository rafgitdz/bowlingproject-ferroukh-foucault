package service.player;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import domain.model.exception.PlayerException;
import domain.model.player.Player;
import domain.model.player.PlayerFactoryLocal;
import domain.model.player.RepositoryPlayer;

@Stateful
public class PlayerService implements PlayerServiceRemote {

	Player player;

	@EJB
	private RepositoryPlayer ePJPA;
	
	@EJB
	private PlayerFactoryLocal playerFactory;

	@Override
	public void roll(int roll) {

		if (player != null)
			player.roll(roll);
		else
			throw new PlayerException(Player.PLAYER_NOT_EXIST);
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
	public Player newPlayer(String name) {
		player = ePJPA.save(playerFactory.newPlayer(name));
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
		ePJPA.delete(player.getName());
	}
}
