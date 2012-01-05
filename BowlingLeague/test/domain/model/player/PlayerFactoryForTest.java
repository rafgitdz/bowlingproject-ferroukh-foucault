package domain.model.player;

import domain.service.DuelServiceForTest;
import domain.service.DuelServiceLocal;

public class PlayerFactoryForTest implements PlayerFactoryLocal {
	
	@Override
	public Player newPlayer(String name) {

		Player player = new Player(name);
		newGame(player);
		player.duelService = new DuelServiceForTest();
		
		return player;
	}

	@Override
	public void newGame(Player player) {
		Game game = createGame();
		player.currentGame = game;
		game.addObserver(player);
	}
	
	private Game createGame() {
		Frame[] frames = new Frame[10];

		for (int i = 0; i < 9; i++)
			frames[i] = new NormalFrame();
		frames[9] = new LastFrame();

		return new Game(frames);
	}
	
	@Override
	public Player rebuildPlayer(Player player, DuelServiceLocal duelService) {
		
		player.currentGame.addObserver(player);

		return player;
	}

	@Override
	public Player rebuildPlayerForTraining(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void newTrainingGame(Player player) {
		// TODO Auto-generated method stub
		
	}
}
