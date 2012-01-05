package infrastructure;

import javax.ejb.Stateful;

import domain.model.player.Player;
import domain.model.player.RepositoryPlayer;

@Stateful
public class RepositoryPlayerJPA extends RepositoryGenericJPA<Player, String>
		implements RepositoryPlayer {

	@Override
	public void loadTrainingGame(Player player) {
		em.find(Player.class, player.getName());
		player.getGame().getCurrentFrameNumber();
	}
}
