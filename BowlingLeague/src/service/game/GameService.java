package service.game;

import java.util.List;

import javax.ejb.Stateful;

import domain.model.game.Game;
import domain.model.game.GameFactory;


@Stateful
public class GameService implements GameServiceRemote {

	@Override
	public int getScore(List<Integer> rolls) {
		
		GameFactory gamefactory = new GameFactory();
		Game game = gamefactory.newGame();
		for (int i = 0; i < rolls.size(); i++) {
			game.roll(rolls.get(i));
		}
		
		return game.getScore();
	}
}