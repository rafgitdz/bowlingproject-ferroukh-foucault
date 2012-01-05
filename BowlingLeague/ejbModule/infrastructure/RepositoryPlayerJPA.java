package infrastructure;

import javax.ejb.Stateful;

import domain.model.player.Player;
import domain.model.player.RepositoryPlayer;

@Stateful
public class RepositoryPlayerJPA extends RepositoryGenericJPA<Player, String>
		implements RepositoryPlayer {
}
