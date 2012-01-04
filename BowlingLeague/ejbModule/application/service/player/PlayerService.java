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
import domain.model.team.TeamFactoryLocal;
import domain.service.DuelServiceLocal;

@Stateless
@WebService(endpointInterface="application.service.player.PlayerServiceRemote", serviceName="playerService")

public class PlayerService implements PlayerServiceRemote {

	private static final String ERROR_UNKNOWN_PLAYER = "Unknown player: ";
	private static final String ERROR_PLAYER_ALREADY_EXISTS = "The player already exists: ";

	@EJB
	private RepositoryPlayer repositoryPlayer;

	@EJB
	private RepositoryTeam repositoryTeam;

	@EJB
	private PlayerFactoryLocal playerFactory;
	
	@EJB
	private TeamFactoryLocal teamFactory;

	@EJB
	private DuelServiceLocal duelService;


	@Override
	public Player newPlayer(String name) {
		
		if (repositoryPlayer.load(name) != null) {
			throw new PlayerException(ERROR_PLAYER_ALREADY_EXISTS + name);
		}
		
		return repositoryPlayer.save(playerFactory.newPlayer(name));
	}

	@Override
	public void deletePlayer(String playerName) {

		Player player = loadPlayer(playerName);

		Team team = player.getTeam();
		if (team != null) {
			team = teamFactory.rebuildTeam(team);
			team.removePlayer(playerName);
			repositoryTeam.update(team);
		}

		Player opponent = player.getOpponent();
		if (opponent != null) {
			opponent.setOpponent(null);
			repositoryPlayer.update(opponent);
		}
		
		repositoryPlayer.delete(playerName);
	}

	@Override
	public void roll(String name, int roll) {

		Player player = loadPlayer(name);
		player = playerFactory.rebuildPlayer(player, duelService);
		player.roll(roll);

		repositoryPlayer.update(player);
			
	}
	
	@Override
	public void rollAlonePlayer(String name, int roll) {
		
		Player p = loadPlayer(name);
		p.play();
		p.roll(roll);
		repositoryPlayer.update(p);
	}

	@Override
	public int getScore(String name) {
		Player player = loadPlayer(name);

		return player.getScore();
	}

	@Override
	public PlayerStatus getPlayerStatus(String playerName) {
		Player player = loadPlayer(playerName);

		return player.getStatus();
	}
	
	private Player loadPlayer(String name) {

		Player player = repositoryPlayer.load(name);
		if (player == null)
			throw new PlayerException(ERROR_UNKNOWN_PLAYER + name);
		return player;
	}
}
