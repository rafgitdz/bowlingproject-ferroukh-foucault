package infrastructure;

import javax.ejb.Stateful;

import domain.model.player.Game;
import domain.model.player.Player;
import domain.model.player.RepositoryPlayer;

@Stateful
public class RepositoryPlayerJPA extends RepositoryGenericJPA<Player, String>
		implements RepositoryPlayer {

	@Override
	public Game loadTrainingGame(String playerName) {
		Player player = em.find(Player.class, playerName);
		player.getTrainingGame().getCurrentFrameNumber();
		return player.getTrainingGame();
	}
}
