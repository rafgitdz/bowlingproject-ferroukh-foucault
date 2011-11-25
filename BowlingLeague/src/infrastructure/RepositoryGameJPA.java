package infrastructure;

import javax.ejb.Stateful;

import domain.model.player.Game;
import domain.model.player.RepositoryGame;

@Stateful
public class RepositoryGameJPA extends RepositoryGenericJPA<Game, Integer>
		implements RepositoryGame {

}
