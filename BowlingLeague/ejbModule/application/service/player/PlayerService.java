package application.service.player;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import domain.model.exception.PlayerException;
import domain.model.player.Frame;
import domain.model.player.Game;
import domain.model.player.LastFrame;
import domain.model.player.Player;
import domain.model.player.PlayerFactoryLocal;
import domain.model.player.PlayerStatus;
import domain.model.player.RepositoryPlayer;
import domain.model.team.RepositoryTeam;
import domain.model.team.Team;
import domain.model.team.TeamFactoryLocal;
import domain.service.DuelServiceLocal;

@Stateless
@WebService(endpointInterface = "application.service.player.PlayerServiceRemote", serviceName = "playerService")
public class PlayerService implements PlayerServiceRemote {

	private static final String ERROR_UNKNOWN_PLAYER = "Unknown player: ";
	private static final String ERROR_PLAYER_ALREADY_EXISTS = "The player already exists: ";
	private static final int TEN = 10;
	private static final int MAX_ROLLS_SIZE = 21;

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

		Team team = repositoryPlayer.getTeam(playerName);
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
	public void roll(String playerName, int roll) {

		Player player = loadPlayer(playerName);
		player = playerFactory.rebuildPlayer(player, duelService);
		player.roll(roll);

		repositoryPlayer.update(player);
	}

	@Override
	public void rollTraining(String playerName, int roll) {

		Player player = loadPlayer(playerName);
		player = playerFactory.rebuildPlayerForTraining(player);
		player.rollTraining(roll);
		repositoryPlayer.update(player);
	}

	@Override
	public int getScore(String playerName) {
		Player player = loadPlayer(playerName);
		return player.getScore();
	}

	@Override
	public int getTrainingScore(String name) {
		Player player = loadPlayer(name);
		player = playerFactory.rebuildPlayerForTraining(player);
		return player.getTrainingScore();
	}

	@Override
	public PlayerStatus getPlayerStatus(String playerName) {

		Player player = loadPlayer(playerName);
		return player.getStatus();
	}

	@Override
	public String getOpponentName(String name) {

		return loadPlayer(name).getOpponent().getName();
	}

	@Override
	public int[] getFrames(String playerName) {

		Player player = loadPlayer(playerName);

		return getRolls(player.getGame().getFrames());
	}

	@Override
	public int[] getDetailedScore(String playerName) {

		Player player = loadPlayer(playerName);

		int[] totalScores = new int[10];

		Game game = player.getGame();
		Frame[] frames = game.getFrames();

		for (int i = 0; i < frames.length; ++i) {

			if (frames[i].isPlayed())
				totalScores[i] = game.getScore(i);
			else
				totalScores[i] = -1;
		}
		return totalScores;
	}

	@Override
	public int[] getDetailedTrainingScore(String playerName) {
		
		Player player = loadPlayer(playerName);
		playerFactory.rebuildPlayerForTraining(player);

		int[] totalScores = new int[10];

		Game game = player.getTrainingGame();
		Frame[] frames = game.getFrames();

		for (int i = 0; i < frames.length; ++i) {

			if (frames[i].isPlayed())
				totalScores[i] = game.getScore(i);
			else
				totalScores[i] = -1;
		}
		return totalScores;
	}

	@Override
	public void newTrainingGame(String playerName) {

		Player player = loadPlayer(playerName);
		playerFactory.rebuildPlayerForTraining(player);
		playerFactory.newTrainingGame(player);
		repositoryPlayer.update(player);
	}

	public Player loadPlayer(String name) {

		Player player = repositoryPlayer.load(name);
		if (player == null)
			throw new PlayerException(ERROR_UNKNOWN_PLAYER + name);
		return player;
	}

	@Override
	public int[] getTrainingFrames(String playerName) {

		Player player = loadPlayer(playerName);
		playerFactory.rebuildPlayerForTraining(player);
		return getRolls(player.getTrainingGame().getFrames());
	}

	@Override
	public boolean isGameOver(String playerName) {
		Player player = loadPlayer(playerName);

		return player.getGame().isOver();
	}

	@Override
	public boolean isTrainingGameOver(String playerName) {

		Player player = loadPlayer(playerName);
		playerFactory.rebuildPlayerForTraining(player);
		return player.getTrainingGame().isOver();
	}

	@Override
	public boolean isInATeam(String playerName) {
		Player player = loadPlayer(playerName);
		return player.getTeam() != null;
	}

	private int[] getRolls(Frame frames[]) {

		int[] rolls = new int[MAX_ROLLS_SIZE];
		int j = 0;
		for (int i = 0; i < frames.length; ++i) {

			if (frames[i] instanceof LastFrame) {

				if (frames[i].isRoll1Played()) {
					rolls[j] = frames[i].getRoll1();
					++j;
				}
				if (frames[i].isRoll2Played()) {
					rolls[j] = frames[i].getRoll2();
					++j;
				}
				if (frames[i].isRoll3Played()) {
					rolls[j] = frames[i].getRoll3();
					++j;
				}

			} else if (frames[i].isStrike()) {

				rolls[j] = TEN;
				++j;
				rolls[j] = -1;
				++j;

			} else {

				if (frames[i].isRoll1Played()) {
					rolls[j] = frames[i].getRoll1();
					++j;
				}
				if (frames[i].isRoll2Played()) {
					rolls[j] = frames[i].getRoll2();
					++j;
				}
			}
		}
		return rolls;
	}
}
