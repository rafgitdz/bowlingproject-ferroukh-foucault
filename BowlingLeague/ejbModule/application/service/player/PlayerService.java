package application.service.player;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import domain.model.exception.PlayerException;
import domain.model.player.Player;
import domain.model.player.PlayerFactoryLocal;
import domain.model.player.PlayerStatus;
import domain.model.player.RepositoryPlayer;
import domain.model.team.RepositoryTeam;
import domain.model.team.Team;
import domain.service.DuelServiceLocal;

@Stateless
@WebService(endpointInterface="application.service.player.PlayerServiceRemote", serviceName="playerService")

public class PlayerService implements PlayerServiceRemote {

	private static final String UNKNOWN_PLAYER = "Unknown player: ";

	@EJB
	private RepositoryPlayer repositoryPlayer;

	@EJB
	private RepositoryTeam repositoryTeam;

	@EJB
	private PlayerFactoryLocal playerFactory;

	@EJB
	private DuelServiceLocal duelService;


	@Override
	public Player newPlayer(String name) {
		return repositoryPlayer.save(playerFactory.newPlayer(name));
	}

	@Override
	public Player loadPlayer(String name) {

		Player player = repositoryPlayer.load(name);
		if (player == null)
			throw new PlayerException(UNKNOWN_PLAYER + name);
		return player;
	}

	@Override
	public void deletePlayer(String name) {

		Player player = repositoryPlayer.load(name);
		if (player == null)
			throw new PlayerException(UNKNOWN_PLAYER + name);

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

		player = playerFactory.rebuildPlayer(player, duelService);
		player.roll(roll);

		repositoryPlayer.update(player);
			
	}
	
	@Override
	public void rollAlonePlayer(String name, int roll) {
		
		Player p = repositoryPlayer.load(name);
		p.play();
		p.roll(roll);
		repositoryPlayer.update(p);
	}

	@Override
	public int getScore(String name) {
		Player p = repositoryPlayer.load(name);
		return p.getScore();
	}

	@Override
	public PlayerStatus getPlayerStatus(String playerName) {
		Player player = repositoryPlayer.load(playerName);
		if (player == null)
			throw new PlayerException(UNKNOWN_PLAYER + playerName);

		return player.getStatus();
	}
}
