package service.player;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import domain.model.exception.PlayerException;
import domain.model.player.Player;
import domain.model.player.PlayerFactoryLocal;
import domain.model.player.RepositoryPlayer;

@Stateless
public class PlayerService implements PlayerServiceRemote {

	@EJB
	private RepositoryPlayer repositoryPlayer;

	@EJB
	private PlayerFactoryLocal playerFactory;

	@Override
	public Player newPlayer(String name) {
		return repositoryPlayer.save(playerFactory.newPlayer(name));
	}

	@Override
	public void savePlayer(Player player) {
		repositoryPlayer.save(player);
	}

	@Override
	public Player loadPlayer(String name) {

		Player player = repositoryPlayer.load(name);
		if (player == null)
			throw new PlayerException(Player.PLAYER_NOT_EXIST);
		return player;
	}

	@Override
	public void deletePlayer(String name) {
		repositoryPlayer.delete(name);
	}

	@Override
	public void roll(String name, int roll) {
		repositoryPlayer.load(name).roll(roll);
	}

	@Override
	public int getScore(String name) {
		return repositoryPlayer.load(name).getScore();
	}

	@Override
	public void getStat(String name) {
	}
}
