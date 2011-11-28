package domain.model.player;

public class PlayerFactoryForTest implements PlayerFactoryLocal {
	
	@Override
	public Player newPlayer(String name) {

		Player p = new Player(name);

		p.setGame(createGame());
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
