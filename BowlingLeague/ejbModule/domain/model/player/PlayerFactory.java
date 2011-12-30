package domain.model.player;

import javax.ejb.Stateless;

import domain.service.DuelServiceLocal;

@Stateless
public class PlayerFactory implements PlayerFactoryLocal {
	
	@Override
	public Player newPlayer(String name) {

		Player player = new Player(name);
		player.currentGame = createGame();
		player.currentGame.addObserver(player);
		return player;
	}

	@Override
	public Player newGame(Player player) {
		player.currentGame = resetGame(player.currentGame);
		return player;
	}

	private Game createGame() {
		Frame[] frames = new Frame[10];

		for (int i = 0; i < 9; i++)
			frames[i] = new NormalFrame();
		frames[9] = new LastFrame();

		return new Game(frames);
	}

	private Game resetGame(Game game) {
		
		game.currentFrame = 0;
		for (Frame frame : game.frames)
			frame.resetFrame();

		return game;
	}

	@Override
	public Player rebuildPlayer(Player player, DuelServiceLocal duelService) {
		
		player.currentGame.addObserver(player);
		player.duelService = duelService;
		
		return player;
	}
}