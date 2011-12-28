package application.service.player;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import domain.model.exception.PlayerException;
import domain.model.player.Player;
import domain.model.player.PlayerFactoryLocal;
import domain.model.player.RepositoryPlayer;
import domain.model.team.RepositoryTeam;
import domain.model.team.Team;

@Stateless
public class PlayerService implements PlayerServiceRemote {

	private static final String UNKNOWN_PLAYER = "Unknown player: ";

	@EJB
	private RepositoryPlayer repositoryPlayer;

	@EJB
	private RepositoryTeam repositoryTeam;
	
	@EJB
	private PlayerFactoryLocal playerFactory;

	@Override
	public Player newPlayer(String name) {
		return repositoryPlayer.save(playerFactory.newPlayer(name));
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
		
		Player player = repositoryPlayer.load(name);
		if (player == null)
			throw new PlayerException(Player.PLAYER_NOT_EXIST);
		
		Team team = player.getTeam();
		if (team != null) {
			team.removePlayer(name);
			repositoryTeam.update(team);
		}
		
		Player opponent = player.getOpponent();
		if (opponent != null) {
			opponent.setOpponent(null);
			repositoryPlayer.update(opponent);
		}
		
		repositoryPlayer.delete(name);
	}

	@Override
	public void roll(String name, int roll) {
		
		Player player = repositoryPlayer.load(name);
		if (player == null)
			throw new PlayerException(UNKNOWN_PLAYER + name);
		player.roll(roll);
		
		repositoryPlayer.update(player);
	}

	@Override
	public int getScore(String name) {
		return repositoryPlayer.load(name).getScore();
	}

	@Override
	public void getStat(String name) {
	}
}
