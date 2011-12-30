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
		Player player = repositoryPlayer.load(name);
		if (player == null)
			throw new PlayerException(Player.PLAYER_NOT_EXIST);
		Team team = player.getTeam();
		if (team != null) {
			team.removePlayer(name);
			repositoryTeam.update(team);
		}
		repositoryPlayer.delete(name);
	}

	@Override
	public void roll(String name, int roll) {
		
		Player p = repositoryPlayer.load(name);
		p.roll(roll);
		repositoryPlayer.update(p);
	}
	
	@Override
	public void rollAlonePlayer(String name, int roll) {
		
		Player p = repositoryPlayer.load(name);
		p.setItsMyTurn(true);
		p.roll(roll);
		repositoryPlayer.update(p);
	}

	@Override
	public int getScore(String name) {
		Player p = repositoryPlayer.load(name);
		return p.getScore();
	}

	@Override
	public void getStat(String name) {
	}
}
