package domain.model.player;

import domain.service.DuelServiceForTest;
import domain.service.DuelServiceLocal;

public class PlayerFactoryForTest implements PlayerFactoryLocal {
	
	@Override
	public Player newPlayer(String name) {
		DuelServiceLocal duelService = new DuelServiceForTest();
		
		Player p = new Player(name);

		p.setGame(createGame());
		
		p.duelService = duelService;
		return p;
	}
	
	@Override
	public Player newGame(Player player) {
		player.setGame(createGame());
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
