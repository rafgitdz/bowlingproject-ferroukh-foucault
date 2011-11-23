package service.game;

import java.util.List;

import javax.ejb.Remote;

import domain.model.game.Game;

@Remote
public interface GameServiceRemote {

	public int getScore(List<Integer> rolls);

	public Game newGame();

	public void saveGame(Game game);

	public Game loadGame(int id);

	public void deleteGame(Game game);
}
