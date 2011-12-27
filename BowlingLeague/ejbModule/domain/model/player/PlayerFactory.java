package domain.model.player;

import javax.ejb.Stateless;

@Stateless
public class PlayerFactory implements PlayerFactoryLocal {

	@Override
	public Player newPlayer(String name) {

		Player player = new Player(name);
		player = newGame(player);
		return player;
	}

	@Override
	public Player newGame(Player player) {
		Game game = createGame();
		player.currentGame = game;
		game.addObserver(player);
		return player;
	}

	private Game createGame() {
		Frame[] frames = new Frame[10];

		for (int i = 0; i < 9; i++)
			frames[i] = new NormalFrame();
		frames[9] = new LastFrame();

		return new Game(frames);
	}
}