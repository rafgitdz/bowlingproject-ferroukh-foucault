package service.game;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import domain.model.game.Game;
import domain.model.game.GameException;
import domain.model.game.RepositoryGame;

@Stateful
public class GameService implements GameServiceRemote {

	@EJB
	private RepositoryGame eGJPA;

	@Override
	public int getScore(List<Integer> rolls) {

		Game game = new Game();
		for (int i = 0; i < rolls.size(); i++) {
			game.roll(rolls.get(i));
		}

		return game.getScore();
	}

	@Override
	public Game newGame() {

		Game game = new Game();
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
		if(game==null) throw new GameException(Game.UNKNOWN_GAME);
		return game;
	}

	@Override
	public void deleteGame(Game game) {
		eGJPA.delete(game);
	}
}
