package service.game;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

<<<<<<< HEAD
import domain.model.game.Game;
import domain.model.game.GameException;
import domain.model.game.RepositoryGame;
=======
import domain.model.player.Game;
import domain.model.player.GameFactory;
import domain.model.player.RepositoryGame;
>>>>>>> 7dc33c52d1693596c0790dd09d4ae428bc9f0ee9

@Stateful
public class GameService implements GameServiceRemote {

	@EJB
	private RepositoryGame eGJPA;

	@Override
	public int getScore(List<Integer> rolls) {

		GameFactory gamefactory = new GameFactory();
		Game game = gamefactory.newGame();
		for (int i = 0; i < rolls.size(); i++) {
			game.roll(rolls.get(i));
		}

		return game.getScore();
	}

	@Override
	public Game newGame() {

		GameFactory gamefactory = new GameFactory();
		Game game = gamefactory.newGame();
		for (int i = 0; i < 18; i++) {
			game.roll(4);
		}

		game = eGJPA.save(game);
		return game;
	}

	@Override
	public void saveGame(Game game) {
		eGJPA.save(game);
	}

	@Override
	public Game loadGame(int id) {

		Game game = eGJPA.load(id);
<<<<<<< HEAD
		if(game==null) throw new GameException(Game.UNKNOWN_GAME);
=======
		if (game == null) {
			GameFactory gamefactory = new GameFactory();
			game = gamefactory.newGame();
		}
>>>>>>> 7dc33c52d1693596c0790dd09d4ae428bc9f0ee9
		return game;
	}

	@Override
	public void deleteGame(Game game) {
		eGJPA.delete(game);
	}
}
