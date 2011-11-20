package service.game;

import java.util.List;

import javax.ejb.Stateful;

import domain.model.game.Game;


@Stateful
public class GameService implements GameServiceRemote {

	@Override
	public int getScore(List<Integer> rolls) {
		
		Game game = new Game();
		for (int i = 0; i < rolls.size(); i++) {
			game.roll(rolls.get(i));
		}
		
		return game.getScore();
	}
}